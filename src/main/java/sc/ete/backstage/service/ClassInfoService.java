package sc.ete.backstage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.VO.ClassInfoListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
public interface ClassInfoService extends IService<ClassInfo> {

    //存在则返回id 不存在则创建并返回id
    Integer judgeAndReturn(ClassInfo classInfo);

    //返回班级名称+入学年份列表
    ClassInfoListVO getClassList();
}
