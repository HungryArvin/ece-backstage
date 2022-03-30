package sc.ete.backstage.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sc.ete.backstage.service.OssService;
import sc.ete.backstage.utils.R;

@Api(tags = "阿里云Oss存储接口")
@RestController
@RequestMapping(path = "/backstage/oss")
public class OssController {
    @Autowired
    OssService ossService;
    @PostMapping("/upload")
    /**
     * create by: Arvin
     * description: 文件上传到阿里云Oss存储
     * @params: [multipartFile]
     * @return com.arvin.utils.R
     */
    @ApiOperation(value = "文件上传到云oss,不保存到数据库，直接返回url")
    public R uploadFile(@RequestParam(name = "file",required = true) MultipartFile file){
        if (file!=null){
            String url = ossService.uploadFile(file,false);
            return R.right().data("url",url);
        }
        return R.error().message("文件上传失败，传入空文件流");
    }
}
