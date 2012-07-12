package sohu.mdvil.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-4-25
 * Time: 上午8:57
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtil {
   private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    public static Map<String,String> getSimpleMap(String file){
        Map map = new HashMap();

        Properties props = new Properties();
        try {
            String filePath =  PropertiesUtil.class.getClassLoader().getResource(file).getPath();
            InputStream ins = new FileInputStream(filePath);  //可更新配置文件

            if (ins != null) {

                props.load(ins);
                Set set = props.keySet();
                Iterator<String> it = set.iterator();
                while(it.hasNext()){
                    String key = it.next();
                    map.put(key,props.get(key));
                }
                ins.close();

                return map;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public static Map<String,String>  getRootMap(String file,String rootName){
        Map map = new HashMap();

        Properties props = new Properties();
        try {
            InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            if (ins != null) {

                props.load(ins);
                String rootValue = props.getProperty(rootName);
                String[] childrenKeys = rootValue==null?new String[0]:rootValue.split(",");
                for(String key:childrenKeys){
                    String value = props.getProperty(key);
                    map.put(key,value);
                }
                ins.close();
                return map;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
