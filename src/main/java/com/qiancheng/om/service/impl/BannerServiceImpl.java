package com.qiancheng.om.service.impl;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import com.qiancheng.om.common.utils.ImageUtils;
import com.qiancheng.om.common.utils.Response;
import com.qiancheng.om.dao.BannerMapper;
import com.qiancheng.om.model.Banner;
import com.qiancheng.om.service.BannerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("BannerService")
public class BannerServiceImpl implements BannerService {
    private static final String IMAGE_PATH = "upload/image/banner/";
    private static final Logger LOGGER = Logger.getLogger(BannerServiceImpl.class);

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public Response insert(String link, String uid, Part part) throws
            Throwable {
        //判断传入的参数是否为空？空返回3
        if (part == null || uid == null || link == null) {
            return new Response(3);
        }
        Banner banner = new Banner();
        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        banner.setImageUrl(fileName);
        banner.setInsertUser(uid);
        banner.setUpdateUser(uid);
        banner.setLink(link);
        int rows = bannerMapper.insert(banner);
        if (rows > 0) {
            return new Response(0).add("info", fileName);
        } else {
            return new Response(1);
        }
    }

    @Override
    public Response deleteById(List<Integer> idArr, String uid) throws Throwable {
        Response response = null;
        if (!idArr.isEmpty() && uid != null) {
            Integer[] bannerIdArr = new Integer[idArr.size()];
            for (int i = 0; i < idArr.size(); i++) {
                bannerIdArr[i] = idArr.get(i);
            }
            int rows = bannerMapper.deleteById(bannerIdArr, uid);
            if (rows > 0) {
                response = new Response(0);
            } else {
                response = new Response(1);
            }
        } else {
            response = new Response(1);
        }
        return response;
    }

    @Override
    public Response listAll() throws Throwable {
        List<Map<String, Object>> bannerList = bannerMapper.listAll();
        for (Map<String, Object> banner : bannerList) {
            banner.put("insertTime", ((Timestamp) banner.get("insertTime")).getTime());
        }
        return new Response(0).add("info", bannerList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response saveWeight(List<Integer> idList, String uid) throws Throwable {
        if (idList == null) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.MISS_PARAM);
        }

        List<Banner> bannerList = new ArrayList<>(idList.size());

        // 遍历传入的 ID，计算各 ID 对应的权重值
        int size = idList.size();
        for (int i = 0, weight = size - 1; i < size; i++, weight--) {
            Integer id = idList.get(i);
            if (id == null) {
                throw new IllegalAccessException(IllegalAccessTypeEnum.ILLEGAL_PARAM);
            }

            Banner banner = new Banner();
            banner.setId(id);
            banner.setWeight(weight);
            banner.setUpdateUser(uid);
            bannerList.add(banner);
        }

        // 更新权重
        for (Banner banner : bannerList) {
            bannerMapper.updateById(banner);
        }
        return new Response(0);
    }

    @Override
    public Response updateImage(Part part, String uid, Integer id) throws
            Throwable {
        // 判断传入的参数是否为空？若为空 返回 3
        if (part == null || uid == null) {
            return new Response(3);
        }
        String fileName = ImageUtils.saveImage(part, IMAGE_PATH);
        //把文件名传入数据库
        Banner banner = new Banner();
        banner.setId(id);
        banner.setImageUrl(fileName);
        banner.setUpdateUser(uid);
        int rows = bannerMapper.updateById(banner);
        if (rows == 0) {
            return new Response(1);
        }
        return new Response(0).add("info", fileName);
    }

    @Override
    public Response updateById(Integer id, String uid, String link) throws
            Throwable {
        if (id == null || uid == null || link == null) {
            return new Response(3);
        }
        Banner banner = new Banner();
        Boolean bannerIsDelete;
        banner.setId(id);
        banner.setUpdateUser(uid);
        banner.setLink(link);
        int rows = bannerMapper.updateById(banner);
        return new Response(0);
    }

}
