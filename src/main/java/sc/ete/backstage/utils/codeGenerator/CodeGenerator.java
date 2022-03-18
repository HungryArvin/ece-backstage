package sc.ete.backstage.utils.codeGenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

/**
 * @author ：Arvin
 * @Description: mybatisPlus 逆向工程--》代码生成器
 * @name：CodeGenerator
 */
public class CodeGenerator {
    public  static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("G:\\projects\\ete-backstage" + "/src/main/java/sc/ete/backstage");
        gc.setAuthor("arvin");
        gc.setOpen(false); //是否打开资源管理器
        gc.setServiceName("%sService");//去掉 命名时自动在名称前面加I
        gc.setIdType(IdType.AUTO);//主键生成策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类当中的日期类型
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://8.142.101.41:3306/ece_base?useSSL=false&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("ece_base");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        System.out.println("输入模块名称：");
        pc.setModuleName("backstage");
//        pc.setParent("sc.ete");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true); //lombok生成
        strategy.setRestControllerStyle(true); //restFul 风格
        // 公共父类
        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
       // strategy.setSuperEntityColumns("id");
        //输入表名
        System.out.println("请输入表名--》多个按逗号分割");
        strategy.setInclude(scanner.next().split(","));
        strategy.setControllerMappingHyphenStyle(true); //驼峰命名
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        //  mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
