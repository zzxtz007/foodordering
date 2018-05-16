package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.FeedbackMapper;
import com.qiancheng.om.model.Feedback;
import com.qiancheng.om.service.FeedBackService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * 反馈业务的实现
 *
 * @author 秦鑫宇
 */
//   0：添加成功   1：添加失败  2: 参数有空值
@Service("FeedBackService")
public class FeedBackServiceImpl implements FeedBackService {
    private static final Logger LOGGER = Logger.getLogger(FeedBackServiceImpl.class);
    @Autowired
    private FeedbackMapper feedBackMapper;

    @Override
    public Response insert(String content, String insertUser) throws Throwable {
        LOGGER.debug("开始insert方法，参数为" + content + "," + insertUser);
        Feedback f = new Feedback();
        f.setContent(content);
        f.setInsertUser(insertUser);
        f.setUpdateUser(insertUser);
        if (content == null) {
            LOGGER.debug("方法结束，状态码为3");
            return new Response(3);
        } else if (insertUser == null) {
            LOGGER.debug("方法结束，状态码为3");
            return new Response(3);
        } else if (feedBackMapper.insert(f) == 1) {
            LOGGER.debug("方法结束，状态码为0");
            return new Response(0);

        } else {
            LOGGER.debug("方法结束，状态码为1");
            return new Response(1);
        }
    }

    @Override
    public Response list(Integer pageNum, Integer pageSize) throws Throwable {
        if (pageNum == null || pageSize == null) {
            return new Response(3);
        }

        // 计算起始索引
        int startIndex = (pageNum - 1) * pageSize;

        List<Map<String, Object>> feedbackList = feedBackMapper.list(startIndex, pageSize);
        int totalCount = feedBackMapper.count();

        // 格式化时间
        for (Map<String, Object> feedback : feedbackList) {
            feedback.put("insertTime", ((Timestamp) feedback.get("insertTime")).getTime());
        }

        return new Response(0).add("info", feedbackList).add("totalCount", totalCount);
    }

    @Override
    public Response deleteById(int[] idArr, String updateUser) throws Throwable {
        LOGGER.debug("开始deleteById方法，参数为" + idArr + "," + updateUser);
        if (feedBackMapper.deleteById(idArr, updateUser) == idArr.length) {
            LOGGER.debug("方法结束，状态码为0");
            return new Response(0);
        } else {
            LOGGER.debug("方法结束，状态码为1");
            return new Response(1);
        }

    }


}
