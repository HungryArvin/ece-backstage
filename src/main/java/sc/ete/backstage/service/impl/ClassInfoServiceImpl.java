package sc.ete.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import sc.ete.backstage.entity.ClassInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sc.ete.backstage.entity.VO.ClassInfoListVO;
import sc.ete.backstage.mapper.ClassInfoMapper;
import sc.ete.backstage.service.ClassInfoService;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;

    //存在则返回id 不存在则创建并返回id
    @Override
    public Integer judgeAndReturn(ClassInfo classInfo) {
        final QueryWrapper<ClassInfo> classInfoQueryWrapper = new QueryWrapper<>();
        classInfoQueryWrapper.eq("class_name",classInfo.getClassName())
                .eq("department",classInfo.getDepartment())
                .eq("level",classInfo.getLevel());
        final ClassInfo classInfo1 = classInfoMapper.selectOne(classInfoQueryWrapper);
        if (classInfo1 == null) {
            classInfoMapper.insert(classInfo);
            return classInfo.getId();
        }
        return classInfo1.getId();
    }

    //返回班级名称+入学年份列表
    @Override
    public ClassInfoListVO getClassList(){
        final QueryWrapper<ClassInfo> classInfoQueryWrapper = new QueryWrapper<>();
        final List<ClassInfo> classInfos = classInfoMapper.selectList(classInfoQueryWrapper);
        //处理并生成指定列表
        final HashSet<String> classNames = new HashSet<>();
        final HashSet<String> levels = new HashSet<>();
        classInfos.forEach(classInfo -> {
            classNames.add(classInfo.getClassName());
            levels.add(classInfo.getLevel());
        });
        final ClassInfoListVO classInfoListVO = new ClassInfoListVO();
        classInfoListVO.setClassName(classNames);
        classInfoListVO.setLevel(levels);
        return classInfoListVO;
    }

}
