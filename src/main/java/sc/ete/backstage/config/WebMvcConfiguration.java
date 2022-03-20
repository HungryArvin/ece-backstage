package sc.ete.backstage.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * description: mvc相关配置
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //設置允許跨域路徑
        registry.addMapping("/**")
                //允許跨域请求的域名
                .allowedOrigins("*")
                //是否允许cookie
                .allowCredentials(true)
                //运行跨域的方法
                .allowedMethods("*")
                //设置允许的headers
                .allowedHeaders("*")
                .maxAge(3600);
    }
}

