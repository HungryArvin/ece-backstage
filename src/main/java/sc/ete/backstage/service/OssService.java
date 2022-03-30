package sc.ete.backstage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：Arvin
 * @Description:    Oss 存储操作 service接口
 * @name：OssService
 * @date ：2021/3/18 14:05
 */
public interface OssService {
    //上传文件
    String uploadFile(MultipartFile file,boolean watermark);
}
