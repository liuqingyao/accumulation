package sohu.mdvil.utils;

import sohu.mdvil.InfoConstant;
import sohu.mdvil.core.model.Discipline;
import sohu.mdvil.core.model.Option;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-28
 * Time: 下午4:43
 * To change this template use File | Settings | File Templates.
 */
public class TagUtil {
    public static List<Option> getDateOptions(String selected){
        SimpleDateFormat sdf0 = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日");

        String[] tip = new String[]{"(5天前)","(4天前)","(3天前)","(2天前)","(昨天)","(今天)","(明天)","(后天)","(3天后)","(4天后)"};
        List<Option> list = new java.util.ArrayList<Option>();
        if(selected.equalsIgnoreCase("all")){
            list.add(new Option("显示所有时间","all",true));
        }else{
            list.add(new Option("显示所有时间","all",false));
        }

        for(int i=-5;i<5;i++){
           String date = sdf0.format(new Date(new Date().getTime()+i*24*60*60*1000L));
           String text = sdf1.format(new Date(new Date().getTime()+i*24*60*60*1000L));
           boolean s_f = false;
           if(date.equalsIgnoreCase(selected)){
               s_f = true;
           }
           Option option = new Option(text+tip[5+i],date,s_f);
            list.add(option);
        }

        return list;
    }
    public static List<Option> getDisciplineOptions(String selected){
        List<Option> list = new java.util.ArrayList<Option>();

        List<Discipline> disciplineList = InfoConstant.list;

        if(selected == null || selected.equalsIgnoreCase("all")){
            list.add(new Option("显示所有项目","all",true));
        }else{
            list.add(new Option("显示所有项目","all",false));
        }

        for(Discipline discipline:disciplineList){
             String text = discipline.getPrefix()+" "+discipline.getName();
            String value = discipline.getCode();
            boolean s_f = false;
            if(value.equalsIgnoreCase(selected)){
                s_f = true;
            }
            list.add(new Option(text,value,s_f));
        }

        return  list;
    }
}

