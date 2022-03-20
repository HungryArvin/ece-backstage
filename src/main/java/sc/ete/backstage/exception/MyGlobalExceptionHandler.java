package sc.ete.backstage.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sc.ete.backstage.utils.ExceptionUtils;
import sc.ete.backstage.utils.R;

/**
 * @author ：Arvin
 * @Description:        自定义的全局异常处理器----》优先指定异常处理，没有特定异常，使用全局异常
 * @name：MyGlobalExceptionHandler
 * @date ：2021/3/16 10:56
 */
@ControllerAdvice
@Slf4j
public class MyGlobalExceptionHandler {

    //异常处理方法
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R returnExceptionInfo(Exception e){
        log.info("异常信息日志:{}",e.getMessage());//控制台输出
        log.error(ExceptionUtils.getMessage(e));; //错误信息输出到日志文件
        return R.error().message("服务器内部发生了点错误......");
    }


    //特殊异常处理
    @ExceptionHandler(MyException.class) //自定义异常处理
    @ResponseBody
    public R specialExceptionInfo(MyException e){
        log.info("异常信息日志:{}",e.getMessage());
        log.error(ExceptionUtils.getMessage(e));; //错误信息输出到日志文件
        return R.error().code(e.getCode()).message(e.getMsg());
    }


}
