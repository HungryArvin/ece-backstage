package sc.ete.backstage.utils;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：Arvin
 * @Description:        读取配置文件当中的Oss配置信息,继承的初始化接口，可以在将类放进容器当中后执行afterPropertiesSet方法
 * 可以利用这个方法进行常量绑定
 * @name：OssPropertiesUtil
 * @date ：2021/3/18 13:41
 */
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")    //读取配置文件
@Data
@ToString
public class OssPropertiesUtil implements InitializingBean {
    //绑定值
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;

    //定义常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    //常量绑
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=this.endpoint;
        KEY_ID=this.keyId;
        KEY_SECRET=this.keySecret;
        BUCKET_NAME=this.bucketName;
    }
}
