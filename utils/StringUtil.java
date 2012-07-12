package sohu.mdvil.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-7-12
 * Time: 上午9:40
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    /**
     * 格式化String为Integer，如果失败，取默认值
     * */
    public static int formatInt(String num,int defaultValue){
         try{
             return Integer.parseInt(num);
         }catch (Throwable e){
             return defaultValue;
         }
    }

}
