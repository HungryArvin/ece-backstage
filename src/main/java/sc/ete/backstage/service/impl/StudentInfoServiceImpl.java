package sc.ete.backstage.service.impl;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.StudentInfo;
import sc.ete.backstage.entity.User;
import sc.ete.backstage.entity.VO.StudentInfoVO;
import sc.ete.backstage.mapper.StudentInfoMapper;
import sc.ete.backstage.service.ClassInfoService;
import sc.ete.backstage.service.StudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sc.ete.backstage.service.UserService;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {
    @Autowired
    private StudentInfoMapper studentInfoMapper;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private UserService userService;

    @Override
    public void addStudentInfo(StudentInfoVO studentInfoVO) {
        final StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(studentInfoVO,studentInfo);

        //创建user
        final User user = new User();
        user.setUsername(studentInfo.getStudentNum());
        user.setPassword(studentInfo.getStudentNum());
        userService.save(user);
        studentInfo.setUserId(user.getUserId());

        //查询或创建classInfo
        final ClassInfo classInfo = new ClassInfo();
        final QueryWrapper<ClassInfo> classInfoQueryWrapper = new QueryWrapper<>();
        classInfoQueryWrapper.eq("department",studentInfo.getDepartment())
                .eq("class_name",studentInfoVO.getClassName())
                .eq("level",studentInfoVO.getLevel());
        ClassInfo classInformation = classInfoService.getOne(classInfoQueryWrapper);
        if (classInformation == null) {
            classInfo.setClassName(studentInfoVO.getClassName());
            classInfo.setDepartment(studentInfoVO.getDepartment());
            classInfo.setLevel(studentInfoVO.getLevel());
            classInfoService.save(classInfo);
            classInformation = classInfo;
        }
        studentInfo.setClassId(classInformation.getId()+"");

        //保存学生信息
        studentInfoMapper.insert(studentInfo);
    }
}
