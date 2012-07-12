package sohu.mdvil.utils;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-3-22
 * Time: 下午5:36
 * 功能添加中
 *
 */
public class JsonUtil {

    private static Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * param:obj
     * */
    public static String objectToJson(Object obj){
        return new JSONObject(obj).toString();    ///org-json
    }
    /**
     * param:collection
     * */
    public static String collectionToJson(Collection collection){
        return new JSONArray(collection).toString();  ///org-json
    }
    /**
     * param:array
     * */
    public static String arrayToJson(Object array){
        try {
            return new JSONArray(array).toString();  ///org-json
        } catch (JSONException e) {
            logger.error(e.getMessage(),e);
        }
        return "[]";
    }
    /**
     * param:map
     * */
    public static String mapToJson(Map map){
        return new JSONObject(map).toString();
    }

}
