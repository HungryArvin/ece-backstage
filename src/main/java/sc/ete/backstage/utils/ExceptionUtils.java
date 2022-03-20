package sc.ete.backstage.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ：Arvin
 * @Description:        异常信息处理工具
 * @name：ExceptionUtils
 * @date ：2021/3/16 11:45
 */
public class ExceptionUtils {
    public static String getMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
