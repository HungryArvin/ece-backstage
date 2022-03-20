package sc.ete.backstage.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Arvin
 * @Description:        自定义异常信息类
 * @name：MyException
 * @date ：2021/3/16 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{
    private Integer code;
    private String msg;
}
