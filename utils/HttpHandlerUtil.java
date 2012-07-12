package sohu.mdvil.utils;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-16
 * Time: 上午11:51
 * To change this template use File | Settings | File Templates.
 */
public class HttpHandlerUtil {
    public static Logger logger = Logger.getLogger(HttpHandlerUtil.class);
    public static final String SEP = "/";
    public static final String HANDLER_PATH = "sohu.mdvil.controller.";
    public static final String DEFAULT_METHOD = "execute";
    public static String[] getHandlerInfo(String uri){
        if(uri == null){
            return null;
        }
        String[] result = new String[2];
        try{

                String[] tmp = uri.split(SEP);
                int len = tmp.length;
                if(len>=3){
                    result[0] = HANDLER_PATH + upperTheFirstChar(uri.split(SEP)[1]);
                    result[1] = uri.split(SEP)[2];
                } else{
                    result[0] = HANDLER_PATH + upperTheFirstChar(uri.split(SEP)[1]);
                    result[1] = DEFAULT_METHOD;
                }

        }catch (Throwable e){
            logger.error(e.getMessage(),e);
            return null;
        }
        return result;
    }
    private static String upperTheFirstChar(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    public static String getParamString(Map map){
        StringBuffer sb = new StringBuffer();
        Set set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String key = it.next();
            String value = (String)map.get(key);
            if(value!=null){
                try {
                    sb.append(key).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }

       return sb.toString();
    }
}
