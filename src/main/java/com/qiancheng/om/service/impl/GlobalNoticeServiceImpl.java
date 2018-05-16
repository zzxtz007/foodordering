package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.GlobalNoticeMapper;
import com.qiancheng.om.model.GlobalNotice;
import com.qiancheng.om.service.GlobalNoticeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 全局消息的业务实现类
 *
 * @author 王道铭
 */
@Service("GlobalNoticeService")
public class GlobalNoticeServiceImpl implements GlobalNoticeService {
    private static final Logger LOGGER = Logger.getLogger(GlobalNoticeServiceImpl.class);
    @Resource
    private GlobalNoticeMapper globalNoticeMapper;

    @Override
    public Response list() throws Throwable {
        List<Map<String, Object>> list = globalNoticeMapper.list();
        if (list != null) {
            return new Response(0).add("globalNoticeList", list);
        } else {
            return new Response(1);
        }
    }

    @Override
    public Response insert(String content, String uid) throws Throwable {
        if (content == null || uid == null) {
            return new Response(3);
        }
        if (content.toCharArray().length > 100) {
            LOGGER.debug("全局通知字符超过100");
            return new Response(3);
        }
        GlobalNotice globalNotice = new GlobalNotice();
        globalNotice.setContent(content);
        globalNotice.setUpdateUser(uid);
        globalNotice.setInsertUser(uid);
        int row = globalNoticeMapper.insert(globalNotice);
        return new Response(0);
    }
}
