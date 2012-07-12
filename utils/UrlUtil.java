package sohu.mdvil.utils;

import org.apache.log4j.Logger;
import sohu.mdvil.cache.Cache;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-15
 * Time: 下午5:57
 * 网络连接
 */
public class UrlUtil {
    public static Logger logger = Logger.getLogger(UrlUtil.class);

    private static Cache cache = Cache.getInstance();

    /*****
     *  @param url
     *  @param timeOut
     *  @return String
     *  加cache策略 :仿造Ajax的cache策略，考虑奥运数据来源的业务特性，可以使用
     * **/
    public static String getContentWithCache(String url,int timeOut){
       return getContentWithCache(url,"UTF-8",timeOut);
    }
    public static String getContent(String urlStr){
        return getContent(urlStr,"UTF-8");
    }
    public static String getContentWithCache(String url,String charSet ,int timeOut){
        String cachedContent = cache.get(url);
        if(cachedContent == null || cachedContent.trim().length()<=0){
            cachedContent = getContent(url,charSet);
            cache.set(url,cachedContent,timeOut);
        }
        return cachedContent;
    }

    public static String getContent(String urlStr,String charset){
        if(urlStr==null){
          return null;
        }

        URL url = null;
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        StringBuffer sb = new StringBuffer();

        try{
            url = new URL(urlStr);
            con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            isr = new InputStreamReader(con.getInputStream(),charset);

            if(isr!=null){
                int len = -1;
                char[] buff = new char[500];

                    while((len=isr.read(buff))!=-1){
                        sb.append(buff,0,len);
                    }
            }
        }catch(Throwable e){
            logger.error(e.getMessage(),e);
        }finally {
            try {
                if(isr!=null){
                    isr.close();
                }
            } catch (IOException e) {

            }
            con.disconnect();
        }
        return sb.toString();
    }

    
    public static int getContentLength(String urlStr){
        if(urlStr==null){
            return 0;
        }
        URL url = null;
        HttpURLConnection con = null;
        int len = 0;
        try{
            url = new URL(urlStr);
            con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            len = con.getContentLength();

        }catch(Throwable e){
            logger.error(e.getMessage(),e);
        }finally {
            con.disconnect();
        }
        return len;
    }

    public static void main(String[] args){

        for(int i=0;i<100;i++){
//            System.out.println(getImageLength("http://1824.img.pp.sohu.com.cn/images/blog/2012/6/11/10/21/u54136728_1389ba95320g215.jpg"));
        }

    }
    
}
