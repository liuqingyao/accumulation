package sohu.mdvil.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-4-1
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponseUtil {

    /** 返回客户端JSON数据，尽量直接使用WebUtils的returnJson ***/
    @Deprecated
    public static void putJsonInResponse(String json,HttpServletResponse response) throws Throwable{
        WebUtils.returnJson(json,response);
    }
    public static String jsonResponse(String successOrError,String arrayOrObjectFlag){
          return "{\"status\":\""+successOrError+"\",\"msg\":\"\",\"data\":"+arrayOrObjectFlag+"}";
    }
    public static String errorJsonResponse(){
        return jsonResponse("0","{}");
    }
    public static String errorJsonResponse(String arrayOrObjectFlag){
        return jsonResponse("0",arrayOrObjectFlag);
    }

    public static String successJson(String data){
        return  jsonResponse("1",data);
    }

}
