package sohu.mdvil.utils;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sohu.mdvil.cache.Cache;
import sohu.mdvil.core.model.Speech;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-7
 * Time: 上午8:57
 * To change this template use File | Settings | File Templates.
 */
public class SpeechClient {
    static  Logger logger =  Logger.getLogger(SpeechClient.class);
    static Cache cache = Cache.getInstance();
    private static String prefix = "3099";
    private static final Map map = Collections.synchronizedMap(new HashMap());
//    private static String submitUrl =  "http://comment4.news.sohu.com/post/mobilecomment/?_input_encode=UTF-8&_output_encode=UTF-8";
    private static String submitUrl =  "http://api.pinglun.sohu.com/post/mobilecomment/?_input_encode=UTF-8&_output_encode=UTF-8";
    private static String countUrl = "http://api.pinglun.sohu.com/dynamic/cmt_wap_topic_count.json?ids=";
//    private static String initUrl = "http://comment4.news.sohu.com/static/cmt_waplive_all_{topicId}.json?from=8";
//    private static String incrementUrl = "http://comment4.news.sohu.com/static/cmt_waplive_rt_{topicId}.json";
    private static String order = "all";
    private static String pageNo = "1";
    private static String pageSize = "30";
    private static String initUrl = "http://api.pinglun.sohu.com/dynamic/cmt_wap_"+order+"_{topicId}.json?pageNo="+pageNo+"&pageSize="+pageSize;
    private static String incrementUrl = "http://api.pinglun.sohu.com/dynamic/cmt_wap_"+order+"_{topicId}.json?pageNo="+pageNo+"&pageSize="+pageSize;
    private static String originPullUrl = "http://api.pinglun.sohu.com/dynamic/cmt_wap_"+order+"_{topicId}.json?pageNo={pageNo}&pageSize={pageSize}";;

    public static String codeToTopicId(String code){
        if(map.containsKey(code)){
            return prefix+map.get(code);
        } else {
//           map.put(code,getNum());
            map.put(code,100000);
           return prefix+map.get(code);
        }
    }

    private static int num = 100000;
    private static int getNum(){
          return num++;
    }

    public static String addSpeech(Speech speech, String topicId) {

        URL url = null;
        HttpURLConnection con = null;
        InputStreamReader isr = null;
        try{
            url = new URL(submitUrl);
            con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(),"UTF-8");

            String content =  speech.getContent();
            String author =  speech.getName();

            logger.info("content:"+content);
            logger.info("author:"+author);

            content = content.replace("%E2%80%86","%20");
            author = author.replace("%E2%80%86","%20");

            content = content.replace("%0A","\\n");
            author = author.replace("%0A","\\n");

            content = content.replace("&","%26");
            author = author.replace("&","%26");

            logger.info("replaced################3");
            logger.info("content:"+content);
            logger.info("author:"+author);

            writer.write("topicId="+topicId+"&content="+ content +"&author="+author);
            writer.flush();
            writer.close();

            isr = new InputStreamReader(con.getInputStream(),"UTF-8");

            if(isr!=null){
                //获取String数据，并转换成JSON数据
                StringBuffer sb = new StringBuffer();
                int len = -1;
                char[] buff = new char[500];

                while((len=isr.read(buff))!=-1){
                    sb.append(buff,0,len);
                }
                isr.close();
                con.disconnect();

                return  sb.toString();
            }

        }catch(Throwable e){
            logger.error(e.getMessage(),e);
        } finally {
            if(con!=null){
                con.disconnect();
            }
            if(isr!=null){
                try {
                    isr.close();
                } catch (IOException e) {
                }
            }
        }
        return "error";
    }

    public static List<Speech> pullSpeechByOriginMethod(String scheduleCode,String pageNo,String pageSize){
        String url = "";
        String topicId = codeToTopicId(scheduleCode);
        url =  originPullUrl.replace("{pageSize}",pageSize);
        url = url.replace("{pageNo}",pageNo);
        url  = url.replace("{topicId}",topicId);

        String json = UrlUtil.getContent(url,"GBK");

//        TreeMap treeMap = new TreeMap();

        List<Speech> speechList = new ArrayList<Speech>() ;
        try {

            logger.info(json);
            JSONObject jsonObject = new JSONObject(json);
            if(jsonObject.has("commentList")){
                JSONArray array = jsonObject.getJSONArray("commentList");

                int len = array.length();
                for(int i =0;i<len;i++){
                    JSONObject obj = array.getJSONObject(i);

                    Speech speech = new Speech();
                    speech.setName(obj.getString("author"));
                    long id = obj.getLong("commentId") ;
                    speech.setId(id);
                    speech.setContent(unusualCharShift(obj.getString("content")));
                    speech.setTime(obj.getLong("createTime"));

//                    treeMap.put(id,speech);
                    speechList.add(speech);
                }

            }

        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
        }

        return speechList;
    }

    public static int getSpeechCount(String scheduleCode){
        String id = codeToTopicId(scheduleCode);
        String result = UrlUtil.getContent(countUrl+id);

        if(result == null || !result.startsWith("{")){
           return 0;
        }
        int count = 0;
        try {
            JSONObject obj = new JSONObject(result);
            if(obj.has(id)){
               JSONObject object = obj.getJSONObject(id);
                return object.getInt("count");
            }
        } catch (JSONException e) {
            logger.error(e.getMessage(),e);
        }
        return count;

    }
    public static List<Speech>  getSpeechFromCache(String scheduleCode){
       String json = getCachedSpeechJson(scheduleCode);
       TreeMap<Long,Speech> map =  formatJsonToList(json);
       return mapToList(map,300);
    }
    private static String getCachedSpeechJson(String scheduleCode){
            return  cache.get(codeToTopicId(scheduleCode)+"_speech_300");
    }
    private static void setCachedSpeechJson(String scheduleCode,String json){
        cache.set(codeToTopicId(scheduleCode)+"_speech_300",json,Cache.TIME_OUT);
    }

    private static List<Speech> mapToList(TreeMap<Long,Speech> map,int size){
        List<Speech> list = new ArrayList<Speech>();
        Set set = map.descendingKeySet();
        Iterator<Long> it = set.iterator();
        while(it.hasNext()){
            long  key = it.next();
            Speech speech = map.get(key);
            if(size>0){
                list.add(speech);
                size --;
            }

        }
        return list;
    }

    public static void updateSpeechCache(String scheduleCode){
       List<Speech> cachedSpeech = getSpeechFromCache(scheduleCode);
        boolean init = true;
        if(cachedSpeech!=null && cachedSpeech.size()>0){
            init = false;
        }
        TreeMap<Long,Speech> map = speechFromComment(codeToTopicId(scheduleCode),init);
        for(Speech sp:cachedSpeech){
              map.put(sp.getId(),sp);
        }

       List<Speech> list = mapToList(map,300);

        setCachedSpeechJson(scheduleCode,new JSONArray(list).toString());
    }

    private static TreeMap<Long ,Speech> formatJsonToList(String json){
        TreeMap treeMap = new TreeMap();
          if(json == null || !json.startsWith("[")){
              return treeMap;
          }
        try {
            JSONArray array = new JSONArray(json);
            int len = array.length();
            for(int i =0;i<len;i++){
               JSONObject obj = array.getJSONObject(i);
                Speech speech = new Speech();
                speech.setName(obj.getString("name"));
                long id = obj.getLong("id") ;
                speech.setId(id);
                speech.setContent(unusualCharShift(obj.getString("content")));   //TODO 后续清除html注入代码
                speech.setTime(obj.getLong("time"));

                treeMap.put(id,speech);
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
        }

        return treeMap;
    }
    private static TreeMap<Long ,Speech> speechFromComment(String topicId ,boolean init){
        String url = "";
        if(init){
            url = initUrl.replace("{topicId}",topicId);
        } else {
            url  = incrementUrl.replace("{topicId}",topicId);
        }

        String json = UrlUtil.getContent(url,"GBK");

        TreeMap treeMap = new TreeMap();
        if(json == null){
            return treeMap;
        }
        try {
            logger.info(json);
            JSONObject jsonObject = new JSONObject(json);
            if(jsonObject.has("commentList")){
                JSONArray array = jsonObject.getJSONArray("commentList");

                int len = array.length();
                for(int i =0;i<len;i++){
                    JSONObject obj = array.getJSONObject(i);

                    Speech speech = new Speech();
                    speech.setName(obj.getString("author"));
                    long id = obj.getLong("commentId") ;
                    speech.setId(id);
                    speech.setContent(obj.getString("content"));  //TODO 转换关键字符，减少性能损失
                    speech.setTime(obj.getLong("createTime"));

                    treeMap.put(id,speech);
                }
            }

        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
        }

        return treeMap;
    }

    private static  String unusualCharShift(String str){
        if(str==null){
                     return "";
        }

        str = str.replaceAll("&amp;","&");
        str = str.replaceAll("&lt;","<");
        str = str.replaceAll("&gt;",">");
        str = str.replaceAll("&quot;","\"");
        str = str.replaceAll("&#039;","\'");
        str = str.replaceAll("&#040;","(");
        str = str.replaceAll("&#041;",")");
        str = str.replaceAll("&#064;","@");
        str = str.replaceAll("<br />;","\\\n");
        str = str.replaceAll("<br/>;","\\\n");
        str = str.replaceAll("\\\\n","\n");

        return str;
    }

}
