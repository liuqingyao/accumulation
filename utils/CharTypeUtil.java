package sohu.mdvil.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-7-11
 * Time: 下午9:29
 * To change this template use File | Settings | File Templates.
 */
public class CharTypeUtil {
    
    private static final int NUM_INDEX = 0;
    private static final int EN_INDEX = 1;
    private static final int CHN_INDEX = 2;
    private static final int SPE_INDEX = 3;

    /**
     * @param  str 传入判断类型的字符
     *
     * @return int[] 返回四位长度的int数组，第一位标识是否含有数字，第二位标识是否含有英文或者_，第三位标识是否含有中文，第四位标识是否含有特殊字符
     *  如果含有，对应此位值为1，否则此位值为0
     *
     * */
    public static final int[] type(String str) {
        int[] r = {0,0,0,0};
        for (int i = 0,len = str.length(); i < len; i++) {
            char ch = str.charAt(i);
            short sc = (short) ch;
            if ((sc >= 48) && (sc <= 57)) { //这个字符是数字
                r[NUM_INDEX] = 1;
            } else if (((sc >= 91) && (sc <= 122)) || ((sc >= 65) && (sc <= 90)) || (sc == 95)) {//这个字符是英文或者是 "_ "
                r[EN_INDEX] = 1;
            } else if (isChinese(ch)) { //这个字符是中文
                r[CHN_INDEX] = 1;
            } else {  //这个字符是特殊字符
                r[SPE_INDEX] = 1;
            }
        }
        return r;
    }

    public static boolean containsCHN(String str){
        int[] r = type(str);
        if(r[CHN_INDEX] == 1){
            return true;
        }
        return false;
    }

    public static boolean onlyNum(String str){
        int[] r = type(str);
        if(r[NUM_INDEX]==1 && r[EN_INDEX]==0 && r[CHN_INDEX]==0 && r[SPE_INDEX] == 0){
           return true;
        }
        return false;
    }

    /**
     * 可判断中文标点符号
     * */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

}
