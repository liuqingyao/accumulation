package sohu.mdvil.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-6-8
 * Time: 下午6:09
 * To change this template use File | Settings | File Templates.
 */
public class DataStreamUtil {
    private static InputStream bytesToInputStream(byte[] bytes ){
        InputStream in = new ByteArrayInputStream(bytes);
        return in;
    }
    private static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            byteStream.write(ch);
        }
        byte[] data = byteStream.toByteArray();
        byteStream.close();
        return data;
    }
}
