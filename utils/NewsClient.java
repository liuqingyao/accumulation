package sohu.mdvil.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import sohu.mdvil.cache.Cache;
import sohu.mdvil.core.model.News;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-7-9
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class NewsClient {
    public static void main( String[] args )
    {
//        SAXReader reader = new SAXReader();
//        try {
//            Document document = reader.read(new URL("http://192.168.106.102:8085/service/newsListCache.action?id=212846206&beginDate=20100325000101"));
//            List list = document.selectNodes("//LIST/ITEM");
//            for(Object o :list){
//                Element element = (Element)o;
//                Map<String,String> news= new HashMap<String,String>();
//                for (int i=0,size=element.nodeCount();i<size;i++){
//                    Node node = element.node(i);
//                    if(node instanceof Element){
//                        Element ele = (Element)element.node(i);
//                        if( ele.nodeCount()>0)
//                            news.put(ele.getName().toLowerCase(), ele.node(0).getText());
//                        else
//                            news.put(ele.getName().toLowerCase(), "");
//                    }
//                }
//                System.out.println(news);
//            }
//        } catch (DocumentException e) {
//            e.printStackTrace();  
//        } catch (MalformedURLException e) {
//            e.printStackTrace();  
//        }

    }
    //TODO 缓冲快讯

    private Map<String ,String> parseXmlToMap(String parentId,String beginDate){
        SAXReader reader = new SAXReader();
        parentId = "347691465";
        String url = "http://open.cms5.sohu.com/service/newsListCache.action?id="+parentId+"&beginDate="+beginDate;
        try {
            Document document = reader.read(new URL(url));
            Element element = (Element)document.selectSingleNode("//ITEM");

            Map<String,String> news= new HashMap<String,String>();
            for (int i=0,size=element.nodeCount();i<size;i++){
                Node node = element.node(i);
                if(node instanceof Element){
                    Element ele = (Element)element.node(i);
                    if( ele.nodeCount()>0)
                        news.put(ele.getName().toLowerCase(), ele.node(0).getText());
                    else
                        news.put(ele.getName().toLowerCase(), "");
                }
            }
            return  news;

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new HashMap<String, String>();
    }

    //获取快讯列表内容
    public List<News> getNewsList(String parentId){

//        String beginDate = Cache.getInstance().get("news_latest_time");

        String beginDate = sdf.format(new Date(new Date().getTime()-24*60*60*1000));
        if(beginDate==null){
            beginDate = "20100325000101";
        }

        Map map = parseXmlToMap(parentId,beginDate);

        Set set = map.keySet();
        Iterator it = set.iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            String value = (String)map.get(key);

        }


        return null;
    }

    //获取单个快讯内容
    public News getNewsById(String id){
        return null;
    }


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

//    public static void main(String[] args){
//
//    }

}
