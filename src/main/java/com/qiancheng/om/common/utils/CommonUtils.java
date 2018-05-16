package com.qiancheng.om.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import sun.reflect.Reflection;

/**
 * 
 * <p>工具类</p >
 * @author 宋云龙
 *
 */

public class CommonUtils {
	
	//定义静态变量log
	private static Logger log=null;
	
	/**
     *  
     * <p>获取logger</p >
     * 
     * @Since 2017年12月05日 上午10:21:24
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @return Logger
     *
     */
	
	public static Logger getLog(){
		if(log==null){
			log=Logger.getLogger(Reflection.getCallerClass().getName());
		}
		return log;
	}
	
	/**
     *  
     * <p>MD5加密</p >
     * 
     * @Since 2017年12月05日 上午10:23:24
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @param String psw
     * @return String
     *
     */
	
	public static String StringToMd5(String psw) {
        {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(psw.getBytes("UTF-8"));
                byte[] encryption = md5.digest();
                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < encryption.length; i++) {
                    if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                    } else {
                        strBuf.append(Integer.toHexString(0xff & encryption[i]));
                    }
                }
                return strBuf.toString();
            } catch (NoSuchAlgorithmException e) {
                return "";
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }
	/**
     *  
     * <p>String转Date</p >
     * 
     * @Since 2017年12月08日 下午14:10:24
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @param String str
     * @return Date
     *
     */
	public static Date StringToDate(String str,int patton){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (patton == 0) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (patton == 1) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} 
		Date date=null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
     *  
     * <p>Date转String</p >
     * 
     * @Since 2017年12月08日 下午14:17:24
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @param Date date
     * @return String
     *
     */
	public static String DateToString(Date date,int patton){
		SimpleDateFormat sdf = null;
		if (patton == 0) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (patton == 1) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} 
		String str=null;
		str= sdf.format(date);
		return str;
	}
	/**
     *  
     * <p>获取系统当前时间</p >
     * 
     * @Since 2017年12月08日 下午14:21:24
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @return String
     *
     */
	public static String getTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}
	/**
     *  
     * <p>汉字格式转换</p >
     * 
     * @Since 2017年12月09日 上午10:07:24
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @return String
     *
     */
	public static String charsetConvert(String str){
		try {
			str=new String(str.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
     *  
     * <p>多图上传</p >
     * 
     * @Since 2017年12月13日 下午15:47:51
     * @author < a href=1097478463@qq.com >宋云龙</ a>
     * @return String
     *
     */
	public static void saveFile(HttpServletRequest request,
            MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
                // )
                String filePath = request.getSession().getServletContext()
                        .getRealPath("/")
                        + "upload/" + file.getOriginalFilename();
                //list.add(file.getOriginalFilename());
                File saveDir = new File(filePath);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();
                // 转存文件
                file.transferTo(saveDir);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	/**
     *  
     * <p>发送验证码</p >
     * 
     * @Since 2017年12月05日 上午09:25:28
     * @author < a href=1097478463@qq.com>宋云龙</ a>
     * @param String telephone
     * @param HttpServletRequest request
     * @return string
     *
     */
	public static String sendCode(String telephone,HttpServletRequest request) throws Exception {
		//生成6位验证码
		Random random = new Random();
		int checkcode = random.nextInt(899999)+100000;
		String url = "http://sapi.253.com/msg/QueryBalance";// 应用地址
		String account = "ddyueche";// 账号
		String pswd = "20171018qC!";// 密码
		String msg = "您好，感谢您的支持，您的验证码为"+checkcode;// 短信内容
		HttpSession session=request.getSession();
		//session.setMaxInactiveInterval(60);//设置session失效时间为60s
		session.setAttribute("checkcode", checkcode);//把验证码保存到session中
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码	
		//构造HttpClient的实例
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		//创建get实例
		GetMethod method = new GetMethod();
		try {
			//添加参数
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),//短信管理账号
					new NameValuePair("pswd", pswd), //短信管理密码
					new NameValuePair("mobile", telephone),//手机号
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("extno", extno) 
				});
			//执行请求
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				//返回请求结果
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String nn = URLDecoder.decode(baos.toString(), "UTF-8");
				return nn;
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			//释放连接
			method.releaseConnection();
			}
		}
	
	/**
     *  
     * <p>获取上传文件路径名</p >
     * 
     * @Since 2017年12月15日 上午10:37:28
     * @author < a href=1097478463@qq.com>宋云龙</ a>
     * @param String telephone
     * @param HttpServletRequest request
     * @return string
     *
     */
	public static String getPath(MultipartFile[] files,HttpServletRequest request){
		String filePathArray="";
		if (files != null && files.length > 0) {
	        for (int i = 0; i < files.length; i++) {
	        	filePathArray=filePathArray+request.getSession().getServletContext()
	                    .getRealPath("/")
	                    + "upload/" + files[i].getOriginalFilename();
	        }
	    }
		return filePathArray;
	}
	
	
	 /** 
	  * 
	  * @param content 
	  *            请求的参数 格式为：name=xxx&pwd=xxx 
	  * @param encoding 
	  *            服务器端请求编码。如GBK,UTF-8等 
	  * @return 
	  * @throws UnsupportedEncodingException 
	  */  
	 public static String getAddresses(String content, String encodingString)throws UnsupportedEncodingException {  
	  // 这里调用淘宝的地理位置接口  
	  String urlStr = "http://ip.taobao.com/service/getIpInfo.php";  
	  String returnStr = getResult(urlStr, content, encodingString);  
	  if (returnStr != null) {  
		   // 处理返回的省市区信息  
		   System.out.println(returnStr);  
		   String[] temp = returnStr.split(",");  
		   if(temp.length<3){  
			   return "0";//无效IP，局域网测试  
		   }  
		   String results = "";
	       String country = ""; //国家 
	       String area = "";    //地区
	       String region = "";  //省份
	       String city = "";    //市区
	       String county = "";  //县 
	       String isp = "";     //运营商 
          for (int i = 0; i < temp.length; i++) {  
               switch (i) {  
               	case 1:  
               		country = decodeUnicode((temp[i].split(":"))[2].replaceAll("\"", ""));// 国家  
               		break;  
                   case 3:  
                       area = decodeUnicode((temp[i].split(":"))[1].replaceAll("\"", ""));// 地区   
                       break;  
                   case 5:  
                       region = decodeUnicode((temp[i].split(":"))[1].replaceAll("\"", ""));// 省份   
                       break;   
                   case 7:  
                       city = decodeUnicode((temp[i].split(":"))[1].replaceAll("\"", ""));// 市区  
                       break;   
                   case 9:  
                       county = decodeUnicode((temp[i].split(":"))[1].replaceAll("\"", ""));// 地区   
                       break;  
                   case 11:  
                       isp = decodeUnicode((temp[i].split(":"))[1].replaceAll("\"", "")); //运营商   
                       break;  
               }  
           }  
           results = country+":"+area+":"+region+":"+city+":"+county+":"+isp;
           return results;  
	  }  
	  return null;  
	 }  
	
	 /** 
	  * @param urlStr 
	  *            请求的地址 
	  * @param content 
	  *            请求的参数 格式为：name=xxx&pwd=xxx 
	  * @param encoding 
	  *            服务器端请求编码。如GBK,UTF-8等 
	  * @return 
	  */  
	 private static String getResult(String urlStr, String content, String encoding) {  
	  URL url = null;  
	  HttpURLConnection connection = null;  
	  try {  
	   url = new URL(urlStr);  
	   connection = (HttpURLConnection) url.openConnection();// 新建连接实例  
	   connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒  
	   connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒  
	   connection.setDoOutput(true);// 是否打开输出流 true|false  
	   connection.setDoInput(true);// 是否打开输入流true|false  
	   connection.setRequestMethod("POST");// 提交方法POST|GET  
	   connection.setUseCaches(false);// 是否缓存true|false  
	   connection.connect();// 打开连接端口  
	   DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据  
	   out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx  
	   out.flush();// 刷新  
	   out.close();// 关闭输出流  
	   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据  
	   // ,以BufferedReader流来读取  
	   StringBuffer buffer = new StringBuffer();  
	   String line = "";  
	   while ((line = reader.readLine()) != null) {  
	    buffer.append(line);  
	   }  
	   reader.close();  
	   return buffer.toString();  
	  } catch (IOException e) {  
	   e.printStackTrace();  
	  } finally {  
	   if (connection != null) {  
	    connection.disconnect();// 关闭连接  
	   }  
	  }  
	  return null;  
	 } 
	 
	 /** 
	  * unicode 转换成 中文 
	  * 
	  * @author fanhui 2007-3-15 
	  * @param theString 
	  * @return 
	  */  
	 public static String decodeUnicode(String theString) {  
	  char aChar;  
	  int len = theString.length();  
	  StringBuffer outBuffer = new StringBuffer(len);  
	  for (int x = 0; x < len;) {  
	   aChar = theString.charAt(x++);  
	   if (aChar == '\\') {  
	    aChar = theString.charAt(x++);  
	    if (aChar == 'u') {  
	     int value = 0;  
	     for (int i = 0; i < 4; i++) {  
	      aChar = theString.charAt(x++);  
	      switch (aChar) {  
	      case '0':  
	      case '1':  
	      case '2':  
	      case '3':  
	      case '4':  
	      case '5':  
	      case '6':  
	      case '7':  
	      case '8':  
	      case '9':  
	       value = (value << 4) + aChar - '0';  
	       break;  
	      case 'a':  
	      case 'b':  
	      case 'c':  
	      case 'd':  
	      case 'e':  
	      case 'f':  
	       value = (value << 4) + 10 + aChar - 'a';  
	       break;  
	      case 'A':  
	      case 'B':  
	      case 'C':  
	      case 'D':  
	      case 'E':  
	      case 'F':  
	       value = (value << 4) + 10 + aChar - 'A';  
	       break;  
	      default:  
	       throw new IllegalArgumentException(  
	         "Malformed      encoding.");  
	      }  
	     }  
	     outBuffer.append((char) value);  
	    } else {  
	     if (aChar == 't') {  
	      aChar = '\t';  
	     } else if (aChar == 'r') {  
	      aChar = '\r';  
	     } else if (aChar == 'n') {  
	      aChar = '\n';  
	     } else if (aChar == 'f') {  
	      aChar = '\f';  
	     }  
	     outBuffer.append(aChar);  
	    }  
	   } else {  
	    outBuffer.append(aChar);  
	   }  
	  }  
	  return outBuffer.toString();  
	 } 
	 
	 /**
     *  
     * <p>生成UUID</p >
     * 
     * @Since 2017年12月26日 上午10:01:24
     * @author < a href=1097478463@qq.com>宋云龙</ a>
     * @return string
     *
     */
	 public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
     }
}
