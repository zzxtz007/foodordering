package com.qiancheng.om.common.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * 应用上下文工具
 *
 * @author Brendan Lee
 */
public class AppContextUtils {
    /**
     * Servlet 上下文对象
     */
    private static final ServletContext SERVLET_CONTEXT = ContextLoader
            .getCurrentWebApplicationContext().getServletContext();

    /**
     * 应用根路径
     */
    private static String rootPath;

    /**
     * 类路径
     */
    private static String classpath;

    static {
        // 获取并格式化 rootPath
        rootPath = formatDirPath(SERVLET_CONTEXT.getRealPath("/"));

        // 获取并格式化 classpath
        try {
            URL rootUrl = AppContextUtils.class.getClassLoader().getResource("");
            if (rootUrl != null) {
                classpath = formatDirPath(URLDecoder.decode(rootUrl.getPath(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前会话的 session 对象
     *
     * @return 当前会话的 session 对象
     */
    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
    }

    /**
     * 获取 Servlet Context 对象
     *
     * @return Servlet Context 对象
     */
    public static ServletContext getServletContext() {
        return SERVLET_CONTEXT;
    }

    /**
     * 根据指定的相对路径，获取其相对于应用根目录的绝对路径
     * <p>
     * 注意区分 Classpath，Classpath 是类文件的存储路径，在 WebAPP 中通常为应用根路径下的 WEB-INF/classes/
     *
     * @param relativePath 相对于应用根目录的路径
     * @return 资源绝对路径
     */
    public static String getPath(String relativePath) {
        relativePath = formatPath(relativePath);
        return rootPath + relativePath;
    }

    /**
     * 根据指定的相对路径，获取其相对于 Classpath 的绝对路径
     * <p>
     * 注意区分应用根路径，应用根路径是整个应用程序的根路径，在 WebAPP 中 Classpath 通常为其下的 WEB-INF/classes/
     *
     * @param relativePath 相对于 Classpath 的路径
     * @return 资源的绝对路径
     */
    public static String getClasspath(String relativePath) {
        relativePath = formatPath(relativePath);
        return classpath + relativePath;
    }

    /**
     * 格式化路径
     * <p>
     * 使用斜线（Forward Slash）作为路径分隔符，去除路径开头的斜线
     *
     * @param path 路径
     * @return 格式化后的路径
     */
    private static String formatPath(String path) {
        // 替换反斜线为斜线
        path = path.replace('\\', '/');

        // 去除开头的斜线
        path = !path.isEmpty() && path.charAt(0) == '/' ? path.substring(1) : path;

        return path;
    }

    /**
     * 格式化文件夹路径
     * <p>
     * 使用斜线（Forward Slash）作为路径分隔符，添加结尾的斜线
     *
     * @param dirPath 文件夹路径
     * @return 格式化后的路径
     */
    private static String formatDirPath(String dirPath) {
        // 替换反斜线为斜线
        dirPath = dirPath.replace('\\', '/');

        // 添加结尾的斜线
        dirPath += !dirPath.isEmpty() && dirPath.charAt(dirPath.length() - 1) == '/' ? "" : '/';

        return dirPath;
    }
}
