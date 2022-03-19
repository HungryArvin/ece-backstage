package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.StudentCourse;
import sc.ete.backstage.mapper.StudentCourseMapper;
import sc.ete.backstage.service.StudentCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生-课程中间表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

}
