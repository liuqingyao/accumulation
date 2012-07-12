package sohu.mdvil.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-30
 * Time: 下午12:23
 * To change this template use File | Settings | File Templates.
 */
public class PageUtil {
    private static int firstPageCorrect(int page){
         if(page<1){
            return 1;
         }
        return page;
    }
    private static int lastPageCorrect(int page,int itemCount ,int size){
         int pageCount = pageCount(itemCount,size);
         if(page>pageCount){
            return pageCount;
         }
        return page;
    }
    public static int pageCorrect(int page,int itemCount,int size){
        page = firstPageCorrect(page);
        page = lastPageCorrect(page,itemCount,size);
        return page;
    }
    public static int pageCount(int itemCount,int size){
         return (itemCount+size-1)/size;
    }

    /**
     * 适用于 index 开始于0
     * */
    public static int startIndex(int page,int itemCount,int size){
        int startIndex = pageCorrect(page,itemCount,size)*size-size;
        if(startIndex<0){
            startIndex = 0;
        }
        return startIndex;
    }

    /**
     * 适用于 index 开始于0
     * */
    public static int endIndex(int page,int itemCount,int size){
        int endIndex = startIndex(page,itemCount,size)+size;
        if(endIndex>itemCount){
           endIndex = itemCount;
        }
        return endIndex;
    }



//
//    public static void main(String[] args){
//        System.out.println(pageCount(5,3));
//        System.out.println(pageCount(10,3));
//        System.out.println(pageCount(3,5));
//    }

}
