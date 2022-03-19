package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.CourseTeacher;
import sc.ete.backstage.mapper.CourseTeacherMapper;
import sc.ete.backstage.service.CourseTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程-教师中间表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {

}
