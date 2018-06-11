package com.qiancheng.om.common.aspect;

import com.google.gson.Gson;
import com.qiancheng.om.common.annotation.NeedRole;
import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.utils.AES;
import com.qiancheng.om.common.utils.AppContextUtils;
import com.qiancheng.om.common.utils.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Session 切面
 *
 * @author XLY
 * @author Brendan Lee
 */
@Aspect
@Component
public class SessionAspect {
    private static final Logger LOGGER = Logger.getLogger(SessionAspect.class);
    private static final Gson GSON = new Gson();
    private static final String UID_FIELD_NAME = "uid";
    private static final Pattern SESSION_KEY_PATTERN = Pattern.compile("\"timestamp\":");
    private static final int OPENID_START_INDEX = 11;
    private static final int OPENID_END_INDEX = 39;

    /**
     * session 拥有的参数名列表
     */
    private static final String[] SESSION_ATTR_ARR = {"uid", "role"};

    /**
     * 水印过期时间
     * <p>
     * 4 分钟内有效
     */
    private static final int WATERMARK_EXPIRE_TIME = 4 * 60;

    /**
     * 检查用户是否已登录
     * <p>
     * 已登录时进入方法，未登录时直接返回
     *
     * @param joinPoint 连接点
     * @return 未登录时返回状态码 2，已登录时返回连接点方法的返回值
     * @throws Throwable 发生异常时抛出
     */
    @Around("@annotation(com.qiancheng.om.common.annotation.NeedLogin)")
    private Object aspect001checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取 session
        HttpSession session = AppContextUtils.getSession();

        // 检查 session 中是否含有 UID，不含说明未登录，直接返回
        if (session.getAttribute(UID_FIELD_NAME) == null) {
            return GSON.toJson(new Response(2));
        }

        // 已登录时正常执行
        return joinPoint.proceed();
    }

    /**
     * 检查用户的角色是否合法
     * <p>
     * 登录用户的角色合法时正常执行方法，非法时直接返回
     *
     * @param joinPoint 连接点
     * @return 角色合法时返回连接点方法的返回值，非法时返回状态码 3
     * @throws Throwable 发生异常时抛出
     */
    @Around("@annotation(com.qiancheng.om.common.annotation.NeedRole)")
    public Object aspect002checkRole(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前登录用户的角色
        HttpSession session = AppContextUtils.getSession();
        UserRoleEnum role = (UserRoleEnum) session.getAttribute("role");

        // 获取方法需求的角色列表
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        UserRoleEnum[] neededRoleArr = signature.getMethod().getAnnotation(NeedRole.class).value();

        // 遍历所需的角色，如果登录用户的角色符合要求，则正常执行方法
        for (UserRoleEnum neededRole : neededRoleArr) {
            if (neededRole.equals(role)) {
                return joinPoint.proceed();
            }
        }

        // 角色非法，返回状态码 3
        return GSON.toJson(new Response(3));
    }

    /**
     * 检查请求的 Open ID 与已登录用户的 Open ID 是否一致
     * <p>
     * 一致时准许执行，不一致则为非法请求，直接返回
     *
     * @param joinPoint 连接点
     * @return 会话合法时返回连接点方法的返回值，非法时直接返回
     * @throws Throwable 发生异常时抛出
     */
    @Around("@annotation(com.qiancheng.om.common.annotation.CheckOpenId)")
    public Object aspect003decryptConsumerInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取参数变量名列表
        LocalVariableTableParameterNameDiscoverer pnd = new
                LocalVariableTableParameterNameDiscoverer();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] paramNames = pnd.getParameterNames(method);

        // 获取用户传输的敏感信息,与 session 内的 uid 进行比较,并且判断时间
        Object[] args = joinPoint.getArgs();
        // 加密算法的初始向量
        String iv = null;
        // 包括敏感数据在内的完整用户信息的加密数据
        String encryptedData = null;
        // 会话密钥
        String sessionKey;
        // session 内 uid
        String uid;
        // 用户唯一标识
        String openId;
        // 水印内的时间戳
        String time;

        // 获取 session
        HttpSession session = AppContextUtils.getSession();

        if (session.getAttribute(UID_FIELD_NAME) == null) {
            return GSON.toJson(new Response(2));
        }
        sessionKey = (String) session.getAttribute("sessionKey");
        uid = (String) session.getAttribute(UID_FIELD_NAME);

        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            // 获取私密数据
            if ("encryptedData".equals(paramName) && args[i] instanceof String) {
                encryptedData = (String) args[i];
            }
            // 获取加密算法的初始向量
            if ("iv".equals(paramName) && args[i] instanceof String) {
                iv = (String) args[i];
            }
        }

        // 没有 sessionKey 则返回 状态码 3
        if (sessionKey == null) {
            return GSON.toJson(new Response(3));
        }
        try {
	        byte[] resultByte = AES.decrypt(Base64.decodeBase64(encryptedData),
                    Base64.decodeBase64(sessionKey),
                    Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                String userInfo = new String(resultByte, "UTF-8");
                openId = userInfo.substring(OPENID_START_INDEX, OPENID_END_INDEX);
                if (openId.equals(uid)) {
                    time = SESSION_KEY_PATTERN.split(userInfo)[1].split(",")[0];
                    long now = System.currentTimeMillis() / 1000;
                    if (now < Long.valueOf(time) || (now - WATERMARK_EXPIRE_TIME) > Long.valueOf
                            (time)) {
                        return GSON.toJson(new Response(3));
                    }
                } else {
                    return GSON.toJson(new Response(3));
                }
            } else {
                return GSON.toJson(new Response(3));
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("错误信息" + e);
            return GSON.toJson(new Response(1));
        }
        return joinPoint.proceed(args);
    }

    /**
     * 从 session 中解析用户信息、并赋给相应的参数
     * <p>
     * 参数的匹配通过参数变量名进行
     *
     * @param joinPoint 连接点
     * @return 连接点方法的返回值
     * @throws Throwable 发生异常时抛出
     */
    @Around("@annotation(com.qiancheng.om.common.annotation.GetUserInfo)")
    public Object aspect004parseSession(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取连接点参数变量名列表
        LocalVariableTableParameterNameDiscoverer pnd = new
                LocalVariableTableParameterNameDiscoverer();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] paramNames = pnd.getParameterNames(method);

        // 获取 session
        HttpSession session = AppContextUtils.getSession();

        // 遍历变量名列表，为变量名匹配的参数赋值
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];

            for (String attr : SESSION_ATTR_ARR) {
                if (attr.equals(paramName)) {
                    args[i] = session.getAttribute(attr);
                }
            }
        }

        // 使用赋值后的参数执行连接点方法
        return joinPoint.proceed(args);
    }
}
