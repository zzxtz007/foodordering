package com.qiancheng.om.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;

/**
 * 文件上传工具
 *
 * @author Brendan Lee
 */
public class UploadUtils {
    /**
     * 获取上传文件的文件名
     * <p>
     * 从 HEADER 的 Content-Disposition 中解析
     *
     * @param part 包含上传文件的 Request Part 对象
     * @return 上传文件的文件名
     * <p>
     * 未获取到文件名时，返回空字符串
     */
    public static String getFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        String[] arr = contentDisposition.split(";");
        for (String str : arr) {
            str = str.trim();
            if (str.startsWith("filename=")) {
                return str.substring(str.indexOf('"') + 1, str.length() - 1);
            }
        }

        return "";
    }

    /**
     * 获取上传文件的文件名
     *
     * @param file 上传的文件
     * @return 上传文件的文件名
     */
    public static String getFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    /**
     * 获取扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     * <p>
     * 文件没有扩展名时，返回空字符串
     */
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.') + 1;
        return index > -1 ? fileName.substring(index) : "";
    }

    /**
     * 获取上传文件的扩展名
     *
     * @param part 包含上传文件的 Request Part 对象
     * @return 上传文件的扩展名
     * <p>
     * 文件没有扩展名时，返回空字符串
     * @see #getFileName(Part)
     */
    public static String getExtension(Part part) {
        return getExtension(getFileName(part));
    }

    /**
     * 获取上传文件的扩展名
     *
     * @param file 上传的文件
     * @return 上传文件的扩展名
     * <p>
     * 文件没有扩展名时，返回空字符串
     * @see #getFileName(Part)
     */
    public static String getExtension(MultipartFile file) {
        return getExtension(getFileName(file));
    }

    /**
     * 根据给定的扩展名数组，检查扩展名是否符合期望
     *
     * @param extension        包含上传文件的 Request Part 对象
     * @param expectExtensions 期望的扩展名数组
     * @return 符合期望时返回 true，否则返回 false
     */
    public static boolean checkExtension(String extension, String[] expectExtensions) {
        for (String expectExtension : expectExtensions) {
            if (extension.equalsIgnoreCase(expectExtension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 根据给定的扩展名数组，检查上传文件的扩展名是否符合期望
     *
     * @param part             包含上传文件的 Request Part 对象
     * @param expectExtensions 期望的扩展名数组
     * @return 符合期望时返回 true，否则返回 false
     */
    public static boolean checkExtension(Part part, String[] expectExtensions) {
        return checkExtension(getExtension(part), expectExtensions);
    }

    /**
     * 根据给定的扩展名数组，检查上传文件的扩展名是否符合期望
     *
     * @param file             上传的文件
     * @param expectExtensions 期望的扩展名数组
     * @return 符合期望时返回 true，否则返回 false
     */
    public static boolean checkExtension(MultipartFile file, String[] expectExtensions) {
        return checkExtension(getExtension(file), expectExtensions);
    }

    /**
     * 根据给定的大小，检查上传文件的大小是否符合期望
     *
     * @param part 包含上传文件的 Request Part 对象
     * @param size 期望的文件大小，单位为 byte
     * @return 文件的大小 <= 期望大小时返回 true，否则返回 false
     */
    public static boolean checkSize(Part part, long size) {
        return part.getSize() <= size;
    }

    /**
     * 根据给定的大小，检查上传文件的大小是否符合期望
     *
     * @param file 上传的文件
     * @param size 期望的文件大小，单位为 byte
     * @return 文件的大小 <= 期望大小时返回 true，否则返回 false
     */
    public static boolean checkSize(MultipartFile file, long size) {
        return file.getSize() <= size;
    }
}
