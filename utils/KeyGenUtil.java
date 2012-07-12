package sohu.mdvil.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-5-24
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class KeyGenUtil {
    public static String getKey(String module,String params){
       return module+"_"+params;
    }
}
