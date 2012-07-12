package sohu.mdvil.utils;

import sun.misc.BASE64Encoder;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-15
 * Time: 下午6:58
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtil {
    private static String argPrefix = "dy/1/0/";
    private static String thumbnailHost =  "http://s9.rr.itc.cn/";
    public static Map<String,String> getImageInfo(String urlStr){
        Map<String, String> map = new HashMap<String, String>();
        try {
            BufferedImage buff = ImageIO.read(new URL(urlStr));
            map.put("w", buff.getWidth() * 1L+"");
            map.put("h", buff.getHeight() * 1L+"");
            map.put("href", urlStr);
            buff.flush();
        }catch (Throwable e) {
            map = null;
        }finally {

        }
        return map;
    }
    public static int picSize(String url){
          return UrlUtil.getContentLength(url);
    }

    public static String  thumbnail(String originUrl){
        String url = "";
        try{
            url = UrlUtil.getContent(thumbnailHost+argPrefix+base64(originUrl));
        }   catch (Throwable e){
        }
          return thumbnailHost+"a"+url;
    }
    
    private static String base64(String value){
        if (value == null) return null;
        return Base64Util.encode(value.getBytes());
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        long start = new Date().getTime();
//        for(int i=0;i<1000;i++){
//            Map map = getImageInfo(thumbnail("http://1824.img.pp.sohu.com.cn/images/blog/2012/6/11/10/21/u54136728_1389ba95320g215.jpg"));
//            System.out.println(map.get("w"));
//            System.out.println(map.get("h"));
//            System.out.println(map.get("href"));
//            System.out.println(picSize("http://1824.img.pp.sohu.com.cn/images/blog/2012/6/11/10/21/u54136728_1389ba95320g215.jpg"));
//        }
//        long end = new Date().getTime();

//        System.out.println((end-start)/1000);

        System.out.println(thumbnail("http://1824.img.pp.sohu.com.cn/images/blog/2012/6/11/10/21/u54136728_1389ba95320g215.jpg"));
    }
}
