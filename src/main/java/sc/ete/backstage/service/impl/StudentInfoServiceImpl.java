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
import sc.ete.backstage.utils.MD5;

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
        //密码需要md5加密
        user.setPassword(MD5.encrypt(studentInfo.getStudentNum()));
        //查询或创建user
        final QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        User userInfo = userService.getOne(userQueryWrapper);
        if (userInfo == null) {
            userService.save(user);
            userInfo = user;
        }
        studentInfo.setUserId(userInfo.getUserId());

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
        //保存前查询 避免重复
        final QueryWrapper<StudentInfo> studentInfoQueryWrapper = new QueryWrapper<>();
        studentInfoQueryWrapper.eq("student_num",studentInfo.getStudentNum());
        final Integer count = studentInfoMapper.selectCount(studentInfoQueryWrapper);
        if(count < 1) {
            studentInfoMapper.insert(studentInfo);
        }
    }
}
