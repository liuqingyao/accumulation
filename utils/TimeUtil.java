package sohu.mdvil.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-25
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtil {
    public static String getNextDay(String date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date1 = sdf.parse(date);
            Date resultDate = new Date(date1.getTime()+1000*60*60*24);

            return sdf.format(resultDate);
        } catch (ParseException e) {

        }
        return  date;
    }
    public static String getPreDay(String date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date1 = sdf.parse(date);
            Date resultDate = new Date(date1.getTime()-1000*60*60*24);

            return sdf.format(resultDate);
        } catch (ParseException e) {

        }
        return  date;
    }
}
