package com.qiancheng.om.common.utils;

import com.qiancheng.om.common.enumeration.IllegalAccessTypeEnum;
import com.qiancheng.om.common.exception.IllegalAccessException;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

/**
 * 图片存储工具
 *
 * @author Ice_Dog
 * @author Brendan Lee
 */
public class ImageUtils {
    private static final Logger LOGGER = Logger.getLogger(ImageUtils.class);

    /**
     * 接受的文件大小上限
     */
    private static int maxFileSize;

    /**
     * 接受的文件扩展名数组
     */
    private static String[] acceptExtension;

    static {
        Properties prop = new Properties();
        try {
            String propFilePath = AppContextUtils.getClasspath("order_meal.properties");
            prop.load(new FileInputStream(propFilePath));
            maxFileSize = Integer.parseInt(prop.getProperty("upload.image.maxSize"));
            acceptExtension = prop.getProperty("upload.image.acceptExtensions").split(",");
            for (int i = 0; i < acceptExtension.length; i++) {
                acceptExtension[i] = acceptExtension[i].trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图片保存到服务器
     * <p>
     * 内部已经对文件扩展名和大小进行了验证，调用前无需再进行验证
     *
     * @param part 包含图片二进制数据的 {@link Part} 对象
     * @param path 图片的相对存储路径，相对于应用根目录
     * @return 图片文件名
     * @throws IOException            文件写入发生异常时抛出
     * @throws IllegalAccessException 非法访问时抛出
     */
    public static String saveImage(Part part, String path) throws IOException,
            IllegalAccessException {
        // 扩展名非法时抛出异常
        if (!UploadUtils.checkExtension(part, acceptExtension)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.INCORRECT_FILE_EXTENSION);
        }

        // 文件过大时抛出异常
        if (!UploadUtils.checkSize(part, maxFileSize)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.FILE_OVERSIZE);
        }

        // 处理并获取上传路径
        UploadInfo uploadInfo = handleUploadPath(path, UploadUtils.getExtension(part));
        String filePath = uploadInfo.path + uploadInfo.fileName;

        //把文件写入本地
        part.write(filePath);

        LOGGER.info("写入图片 " + filePath);
        return uploadInfo.fileName;
    }

    /**
     * 将图片保存到服务器
     * <p>
     * 内部已经对文件扩展名和大小进行了验证，调用前无需再进行验证
     *
     * @param file 上传的文件
     * @param path 图片的相对存储路径，相对于应用根目录
     * @return 图片文件名
     * @throws IOException            文件写入发生异常时抛出
     * @throws IllegalAccessException 非法访问时抛出
     */
    public static String saveImage(MultipartFile file, String path) throws IOException,
            IllegalAccessException {
        // 扩展名非法时抛出异常
        if (!UploadUtils.checkExtension(file, acceptExtension)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.INCORRECT_FILE_EXTENSION);
        }

        // 文件过大时抛出异常
        if (!UploadUtils.checkSize(file, maxFileSize)) {
            throw new IllegalAccessException(IllegalAccessTypeEnum.FILE_OVERSIZE);
        }

        // 处理并获取上传路径
        UploadInfo uploadInfo = handleUploadPath(path, UploadUtils.getExtension(file));
        String filePath = uploadInfo.path + uploadInfo.fileName;

        //把文件写入本地
        file.transferTo(new File(filePath));

        LOGGER.info("写入图片 " + filePath);
        return uploadInfo.fileName;
    }

    /**
     * 上传信息
     */
    static class UploadInfo {
        String path;
        String fileName;

        /**
         * 构造上传信息对象
         *
         * @param path     上传路径
         * @param fileName 文件名
         */
        UploadInfo(String path, String fileName) {
            this.path = path;
            this.fileName = fileName;
        }
    }

    /**
     * 处理并获取上传目标路径及文件名
     *
     * @param path    图片的相对存储路径路径，相对于应用根目录
     * @param extName 文件扩展名
     * @return 文件最终存储的绝对路径，包含以 UUID 命名的文件名
     */
    private static UploadInfo handleUploadPath(String path, String extName) {
        // 获取绝对存储路径，确保路径以斜线结尾
        if (!path.isEmpty() && path.charAt(path.length() - 1) != '/') {
            path += '/';
        }
        String uploadPath = AppContextUtils.getPath(path);

        // 创建上传文件夹
        //noinspection ResultOfMethodCallIgnored
        new File(uploadPath).mkdirs();

        // 使用UUID作为文件名，重名时重新随机命名
        String fileName;
        do {
            fileName = UUID.randomUUID() + "." + extName;
        } while (new File(uploadPath + fileName).exists());

        return new UploadInfo(uploadPath, fileName);
    }
}
