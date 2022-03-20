package sc.ete.backstage.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Arvin
 * @Description: 统一json格式的 规范类
 * @name：R
 */
@Data
public class R {
        @ApiModelProperty(value = "是否成功")
        private Boolean success;

        @ApiModelProperty(value = "返回码")
        private Integer code;

        @ApiModelProperty(value = "返回消息")
        private String message;

        @ApiModelProperty(value = "返回数据")
        private Map<String, Object> data = new HashMap<String, Object>();
        //构造函数私有化
        private R(){}

        //方法统一采用链式编程
        //成功调用的方法
        public static R right(){
            R r = new R();
            r.setSuccess(true);
            r.setCode(ResultCode.SUCCESS.CODE);
            r.setMessage("调用成功");
            return r;
        }
    //创建成功无返回结果
    public static R created(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.CREATED.CODE);
        r.setMessage("创建成功");
        return r;
    }
        //失败方法
        public static R error(){
            R r = new R();
            r.setSuccess(false);
            r.setCode(ResultCode.ERROR.CODE);
            r.setMessage("请求失败");
            return r;
        }
        public R success(Boolean success){
            this.setSuccess(success);
            return this;
        }
        public R message(String message){
            this.setMessage(message);
            return this;
        }
        public R code(Integer code){
            this.setCode(code);
            return this;
        }
        public R data(String key, Object value){
            this.data.put(key, value);
            return this;
        }
        public R data(Map<String, Object> map){
            this.setData(map);

            return this;
        }
    }

