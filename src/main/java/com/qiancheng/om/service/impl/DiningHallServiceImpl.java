package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.ImageUtils;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.DiningHallMapper;
import com.qiancheng.om.model.DiningHall;
import com.qiancheng.om.service.DiningHallService;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 食堂 service
 *
 * @author 李登帅
 */
@Service("DiningHallService")
public class DiningHallServiceImpl implements DiningHallService {
    private static final String IMAGE_PATH = "upload/image/dining-hall/";
    private static final Long ONEDAY = 24 * 60 * 60 * 1000L;
    private static final Logger LOGGER = Logger.getLogger(DiningHallServiceImpl.class);
    @Resource
    private DiningHallMapper diningHallMapper;
    @Resource
    private FastDateFormat timeFormat;

    @Override
    public Response insert(String name, Date startTime, Date endTime, String introduction,
            String uid, MultipartFile file) throws Throwable {
        if (name == null || startTime == null || endTime == null || uid == null) {
            LOGGER.error("必填选项没有全部填写");
            return new Response(3);
        }

        Time start = new Time(startTime.getTime());
        Time end = new Time(endTime.getTime());
        if (end.getTime() < start.getTime() || (end.getTime() - start.getTime()) >= ONEDAY) {
            return new Response(3);
        }

        String imageUrl = "";
        if (file != null) {
            imageUrl = ImageUtils.saveImage(file, IMAGE_PATH);
        }

        DiningHall diningHall = new DiningHall();
        diningHall.setName(name);
        diningHall.setImageUrl(imageUrl);
        diningHall.setStartTime(start);
        diningHall.setEndTime(end);
        diningHall.setInsertUser(uid);
        diningHall.setUpdateUser(uid);
        diningHall.setIntroduction(introduction);
        int rows = diningHallMapper.insert(diningHall);
        if (rows > 0) {
            return new Response(0);
        } else {
            LOGGER.error("方法调用结束，非正常执行.");
            return new Response(1);
        }
    }

    @Override
    public Response deleteById(List<Integer> diningHallIds, String uid) throws
            Throwable {
        if (uid == null || diningHallIds.isEmpty()) {
            LOGGER.error("必填选项没有全部填写");
            return new Response(3);
        }

        Integer[] idArr = new Integer[diningHallIds.size()];
        for (int i = 0; i < diningHallIds.size(); i++) {
            idArr[i] = diningHallIds.get(i);
        }
        int rows = this.diningHallMapper.deleteById(idArr, uid);
        return new Response(0);
    }

    @Override
    public Response updateById(Integer id, String name, Date startTime, Date endTime, String
            introduction, String uid) throws Throwable {
        if (id == null || uid == null) {
            LOGGER.error("必填选项没有全部填写");
            return new Response(3);
        }

        Time start = new Time(startTime.getTime());
        Time end = new Time(endTime.getTime());
        if (end.getTime() < start.getTime() || (end.getTime() - start.getTime()) >= ONEDAY) {
            return new Response(3);
        }
        DiningHall diningHall = new DiningHall();
        diningHall.setId(id);
        diningHall.setName(name);
        diningHall.setStartTime(start);
        diningHall.setEndTime(end);
        diningHall.setUpdateUser(uid);
        diningHall.setIntroduction(introduction);
        int rows = this.diningHallMapper.updateById(diningHall);
        return new Response(0);
    }

    @Override
    public Response list() throws Throwable {
        DiningHall condition = new DiningHall();
        List<Map<String, Object>> list = diningHallMapper.list(condition);
        int totalCount = diningHallMapper.count(condition);

        // 格式化时间
        for (Map<String, Object> diningHall : list) {
            diningHall.put("insertTime", ((Timestamp) diningHall.get("insertTime")).getTime());
            diningHall.put("updateTime", ((Timestamp) diningHall.get("updateTime")).getTime());
            diningHall.put("startTime", timeFormat.format(diningHall.get("startTime")));
            diningHall.put("endTime", timeFormat.format(diningHall.get("endTime")));
        }

        return new Response(0).add("info", list).add("totalCount", totalCount);
    }

    @Override
    public Response getById(Integer id) throws Throwable {
        if (id == null) {
            return new Response(3);
        }
        DiningHall diningHall = new DiningHall();
        diningHall.setId(id);
        Map<String, Object> map = diningHallMapper.selectById(diningHall);
        return new Response(0).add("diningHall", map);
    }

    @Override
    public Response updateWeight(List<Integer> idList, String uid) throws Throwable {
        if (idList == null || uid == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        // 遍历 ID List，计算各 ID 对应的权重值
        int size = idList.size();
        List<DiningHall> diningHallList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int weight = size - 1 - i;
            Integer id = idList.get(i);
            if (id == null) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }

            DiningHall diningHall = new DiningHall();
            diningHall.setId(id);
            diningHall.setWeight(weight);
            diningHall.setUpdateUser(uid);
            diningHallList.add(diningHall);
        }

        // 更新数据库
        for (DiningHall diningHall : diningHallList) {
            diningHallMapper.updateById(diningHall);
        }

        return new Response(0);
    }

    @Override
    public Response updateImageUrl(Integer id, Part part, String uid) throws Throwable {
        if (part == null || id == null || uid == null) {
            LOGGER.error("part context id uid 必填");
            return new Response(3);
        }

        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        DiningHall diningHall = new DiningHall();
        diningHall.setId(id);
        diningHall.setImageUrl(fileName);
        diningHall.setUpdateUser(uid);
        int ret = diningHallMapper.updateById(diningHall);
        if (ret == 0) {
            return new Response(1);
        }
        return new Response(0).add("info", fileName);
    }
}
