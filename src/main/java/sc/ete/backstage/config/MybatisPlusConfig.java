package sc.ete.backstage.config;



import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sc.ete.backstage.handler.MyMetaObjectHandler;


/**
 * @author ：Arvin
 * @Description:        MybatisPlus 配置类
 * @name：MybatisPlusConfig
 * @date ：2021/3/15 20:35
 */
@Configuration(proxyBeanMethods = false)
@MapperScan(basePackages = {"sc.ete.backstage.mapper"})
@Slf4j
public class MybatisPlusConfig {
    @Bean
    /**
     * create by: Arvin
     * description: 分页插件 and 乐观锁的插件
     * @params: []
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     */
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("MybatisPlus 插件加载");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.H2);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //  interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
    @Bean
   /* *
     * create by: Arvin
     * description:  自定义的entity指定内容自动填充插件
     * @params: []
     * @return com.arvin.school.handler.MyMetaObjectHandler*/

    public MyMetaObjectHandler myMetaObjectHandler(){
        return new MyMetaObjectHandler();
    }
  /*  @Bean
    public IdentifierGenerator idGenerator() {

        return new CustomIdGenerator();
    }*/

}
