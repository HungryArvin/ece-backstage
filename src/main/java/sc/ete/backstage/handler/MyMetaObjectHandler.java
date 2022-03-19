package sc.ete.backstage.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author ：Arvin
 * @Description:    mybatisPlus自动填充注解的填充内容实现逻辑
 * @name：MyMetaObjectHandler
 * @date ：2021/3/15 11:44
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    /**
     * create by: Arvin
     * description: 添加操作时候的自动填充逻辑
     * @params: [metaObject]
     * @return void
     */
    public void insertFill(MetaObject metaObject) {

//        this.setFieldValByName("gmtCreate",new Date(),metaObject); //创建时间
        this.setFieldValByName("updateTime",new Date(),metaObject); //更新时
//        Integer isDelete=0;
//        this.setFieldValByName("isDeleted",isDelete,metaObject); //默认删除值0
        //乐观锁版本默认值
      //  this.setFieldValByName("version",1,metaObject);

    }

    @Override
    /**
     * create by: Arvin
     * description: 修改操作时候的自动填充逻辑
     * @params: [metaObject]
     * @return void
     */
    public void updateFill(MetaObject metaObject) {
      //  this.setFieldValByName("register",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
