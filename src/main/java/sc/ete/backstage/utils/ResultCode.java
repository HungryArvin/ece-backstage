package sc.ete.backstage.utils;
/**
 * create by: Arvin
 * description: 20000 是成功，20001是访问失败
 * @params:
 * @return
 */
public enum ResultCode {
    SUCCESS(200,"request success"),
    NOT_FOUND(404,"not found request url"),
    ERROR(201,"bad request");
    public Integer CODE;
    public String MSG;
    private ResultCode (Integer code,String msg) {
        this.CODE = code;
        this.MSG = msg;
    }
}
