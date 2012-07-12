package sohu.mdvil.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-15
 * Time: 下午4:58

 工具化 :
 要求：良好的注释和语义

 */
public class WebUtils {
    /**
     * 设置客户端无缓存Header.
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        //Http 1.0 header
        response.setDateHeader("Expires", 0);
        //Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }
    /**
     * 检查浏览器客户端是否支持gzip编码.
     */
    public static boolean checkAcceptGZip(HttpServletRequest request) {
        //Http1.1 header
        String acceptEncoding = request.getHeader("Accept-Encoding");

        if (acceptEncoding!=null && acceptEncoding.toLowerCase().contains("gzip")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回客户端JSON数据
     */
    public static void returnJson(String json,HttpServletResponse response){
        try{
            response.setContentType("application/json;charset=UTF-8");
            Writer writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        }catch (Throwable e){
             logger.error(e.getMessage(),e);
        }
    }

    /**
     * 返回客户端JSON数据
     */
    public static void returnXml(String json,HttpServletResponse response){
        try{
            response.setContentType("application/xml;charset=GBK");
            Writer writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        }catch (Throwable e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 设置客户端缓存过期时间 Header.
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        //Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
        //Http 1.1 header
        response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
    }

    private static Logger logger = Logger.getLogger(WebUtils.class);
}
