package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.UserRoleEnum;
import com.qiancheng.om.common.utils.PasswordUtils;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.ConsumerMapper;
import com.qiancheng.om.dao.OperatorMapper;
import com.qiancheng.om.dao.StallMapper;
import com.qiancheng.om.model.Consumer;
import com.qiancheng.om.model.Stall;
import com.qiancheng.om.service.SessionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 会话 Service
 *
 * @author Brendan Lee
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {
    private static final Logger LOGGER = Logger.getLogger(SessionServiceImpl.class);
    private static final Pattern COMMA_PATTERN = Pattern.compile("\",\"");
    private static final Pattern SESSION_KEY_PATTERN = Pattern.compile("\"session_key\":\"");
    private static final Pattern OPEN_ID_PATTERN = Pattern.compile("\"openid\":\"");
    private static final int SESSION_TIMEOUT = 4 * 60 * 60;
    @Resource
    private ConsumerMapper consumerMapper;
    @Resource
    private StallMapper stallMapper;
    @Resource
    private OperatorMapper operatorMapper;

    @Override
    public Response consumerLogin(String code, HttpSession session) throws Throwable {
        StringBuffer stringBuffer;
        try {
            // TODO：数据从 properties 内取出
            URL url = new URL("https://api.weixin.qq"
                    + ".com/sns/jscode2session?appid=wx7cb22a290dc569d2&secret"
                    + "=0ed434d22f2727ca583a28a82242deda&js_code=" + code +
                    "&grant_type=authorization_code");
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream(), "utf-8"));
            String line;
            stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append('\n');
            }

            bufferedReader.close();
        } catch (IOException e) {
            LOGGER.error("输入输出流异常" + e);
            return new Response(1);
        }

        String[] infoArr = COMMA_PATTERN.split(stringBuffer.toString());

        if (infoArr[0].contains("session_key")) {
            String[] info = {(SESSION_KEY_PATTERN.split(infoArr[0])[1]),
                    OPEN_ID_PATTERN.split(infoArr[1])[1].substring(0, 28)};

            session.setAttribute("sessionKey", info[0]);
            session.setAttribute("uid", info[1]);
            session.setAttribute("role", UserRoleEnum.CONSUMER);

            Consumer consumer = new Consumer();
            consumer.setId(info[1]);

            List<Map<String, Object>> consumerArr = consumerMapper.list(consumer);
            if (consumerArr.isEmpty()) {
                consumerMapper.insert(consumer);
                Map<String, Object> map = new HashMap<>(2, 1);
                map.put("isMember", false);
                map.put("phone", null);
                return new Response(0).add("info", map);
            }
            LOGGER.info("顾客登录, UID=" + session.getAttribute("uid"));
            Map<String, Object> consumerMap = consumerArr.get(0);
            consumerMap.remove("id");
            return new Response(0).add("info", consumerMap);
        } else {
            return new Response(2);
        }
    }

    @Override
    public Response stallLogin(String username, String password, HttpSession session) throws
            Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (username == null || password == null || session == null) {
            return new Response(3);
        }
        //获取username对应的数据
        Stall stall = new Stall();
        stall.setUsername(username);
        List<Map<String, Object>> stalls = stallMapper.list(stall);
        Map<String, Object> s;
        //查询到的数据为空 返回 4
        if (!stalls.isEmpty()) {
            s = stalls.get(0);
        } else {
            LOGGER.error("获取到的 map 为空");
            return new Response(4);
        }
        String realPassword = (String) s.get("pwdHash");
        String salt = (String) s.get("salt");
        String passwordHash = PasswordUtils.hash(password, salt);
        if (passwordHash.equals(realPassword)) {
            session.setMaxInactiveInterval(SESSION_TIMEOUT);
            session.setAttribute("uid", s.get("id").toString());
            session.setAttribute("role", UserRoleEnum.STALL);
            session.setAttribute("username", s.get("username").toString());
            LOGGER.info("商户登录, UID=" + session.getAttribute("uid"));
            Map<String,Object> info = new HashMap<>();
            info.put("id",s.get("id"));
            info.put("isOpen",s.get("isOpen"));
            return new Response(0).add("info",info);
        } else {
            LOGGER.error("账号密码错误");
            return new Response(4);
        }
    }

    @Override
    public Response operatorLogin(String username, String password, HttpSession session) throws
            Throwable {
        //判断传入的参数是否为空？若为空 返回 3
        if (username == null || password == null) {
            return new Response(3);
        }

        // 已登录时直接返回
        if (session.getAttribute("uid") != null) {
            return new Response(5);
        }

        //获取username对应的数据
        Map<String, Object> map = operatorMapper.get(username);
        if (map != null) {
            // 获取盐值
            String salt = (String) map.get("salt");
            //获取加盐密码的散列值
            String pwdHash = (String) map.get("pwdHash");
            String pwd = PasswordUtils.hash(password, salt);
            if (pwd.equalsIgnoreCase(pwdHash)) {
                UserRoleEnum role;

                switch ((Integer) map.get("role")) {
                    case 1:
                        role = UserRoleEnum.OPERATOR_ADMIN;
                        break;
                    case 2:
                        role = UserRoleEnum.OPERATOR;
                        break;
                    default:
                        return new Response(4);
                }
                session.setAttribute("role", role);
                session.setAttribute("uid", map.get("id").toString());
                session.setAttribute("username", map.get("username").toString());

                LOGGER.info("运营用户登录, UID=" + session.getAttribute("uid"));
                return new Response(0).add("role", role.getValue());
            } else {
                return new Response(4);
            }
        } else {
            return new Response(4);
        }
    }

    @Override
    public Response logout(HttpSession session, String uid, UserRoleEnum role) throws Throwable {
        if (session == null) {
            return new Response(3);
        }

        LOGGER.info("用户登出, UID=" + uid + ", Role=" + role);
        session.invalidate();
        return new Response(0);
    }
}
