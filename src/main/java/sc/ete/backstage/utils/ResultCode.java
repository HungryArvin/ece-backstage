package sc.ete.backstage.utils;
/**
 * create by: Arvin
 * @params:
 * @return
 */
public enum ResultCode {
    SUCCESS(200,"request success"),
    NOT_FOUND(404,"not found request url"),
    ERROR(500,"request error"),
    CREATED(201,"created success"),
    NO_CONTENT(204,"success and no response"),
    BAD_REQUEST(400,"bad request");


    public Integer CODE;
    public String MSG;
    private ResultCode (Integer code,String msg) {
        this.CODE = code;
        this.MSG = msg;
    }
}
