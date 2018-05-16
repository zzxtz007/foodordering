package com.qiancheng.om.task.job;

import com.qiancheng.om.service.StallService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;

/**
 * 批量关店定时任务
 */
public class BatchCloseStall {
    @Resource
    private StallService stallService;
    private static final Logger LOGGER = Logger.getLogger(BatchCloseStall.class);

    public void execute() {
        try {
            int count = stallService.batchCloseStall();
            LOGGER.info("批量关店成功，共影响 " + count + " 家商户");
        } catch (Throwable throwable) {
            LOGGER.error("批量关店失败", throwable);
        }
    }
}
