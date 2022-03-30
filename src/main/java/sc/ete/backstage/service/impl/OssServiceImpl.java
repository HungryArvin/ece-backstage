package sc.ete.backstage.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sc.ete.backstage.service.OssService;
import sc.ete.backstage.utils.OssPropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    //上传文件
    @Override
    public String uploadFile(MultipartFile file,boolean watermark)  {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = OssPropertiesUtil.END_POINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = OssPropertiesUtil.KEY_ID;
        String accessKeySecret = OssPropertiesUtil.KEY_SECRET;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = null;
        try {
            //获取输入文件的流
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
// 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        //获取文件名称+后缀
        String filename = UUID.randomUUID().toString().replaceAll("-","").substring(25)+file.getOriginalFilename();
        //工具类jar包，简单获取当前日期
        String date = new DateTime().toString("yyyy/MM/dd");
        String uploadFileName="image/"+date+"/"+filename;
        ossClient.putObject(OssPropertiesUtil.BUCKET_NAME, uploadFileName, inputStream);
        //上传之后---》需要返回该文件在oss当中的路径
        String url="http://"+OssPropertiesUtil.BUCKET_NAME+"."+OssPropertiesUtil.END_POINT+"/"+uploadFileName;
        // 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }
}
