package com.qiancheng.om.common.aspect;

import com.google.gson.Gson;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.Response;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller 相关的切面
 *
 * @author Brendan Lee
 */
@Aspect
@Component
public class ControllerAspect {
    private static final Logger LOGGER = Logger.getLogger(ControllerAspect.class);
    private static final Gson GSON = new Gson();

    /**
     * 从连接点中获取方法参数名和传入参数值的 Map
     *
     * @param jp 连接点
     * @return 参数名-传入参数值 Map
     */
    private static Map<String, Object> getArgMap(JoinPoint jp) {
        // 获取参数名列表
        LocalVariableTableParameterNameDiscoverer pnd = new
                LocalVariableTableParameterNameDiscoverer();
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        String[] argNames = pnd.getParameterNames(method);

        // 获取参数值列表
        Object[] argValues = jp.getArgs();

        // 构建参数 Map
        Map<String, Object> argMap = new HashMap<>(argNames.length, 1);
        for (int i = 0; i < argNames.length; i++) {
            argMap.put(argNames[i], argValues[i]);
        }

        return argMap;
    }

    /**
     * 获取方法执行的信息
     *
     * @param jp 连接点
     * @return 方法执行的信息
     */
    private static String getProceedInfo(JoinPoint jp) {
        String signature = jp.getSignature().toString();
        Map<String, Object> argMap = getArgMap(jp);

        return "\nMethod Signature: " + signature + "\nArguments: " + argMap;
    }

    /**
     * Controller 通用切面
     *
     * @param pjp 连接点
     * @return 未发生异常时返回连接点返回值，否则返回异常状态码
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object genericAspect(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (IllegalAccessException e) {
            String message = getProceedInfo(pjp);
            LOGGER.debug("拦截到非法访问" + message, e);
            return GSON.toJson(new Response(3));
        } catch (Throwable t) {
            String message = getProceedInfo(pjp);
            LOGGER.error(message, t);
            return GSON.toJson(new Response(1));
        }
    }
}
