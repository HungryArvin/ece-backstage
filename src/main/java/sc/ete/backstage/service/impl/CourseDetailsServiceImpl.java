package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.CourseDetails;
import sc.ete.backstage.mapper.CourseDetailsMapper;
import sc.ete.backstage.service.CourseDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程详情表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class CourseDetailsServiceImpl extends ServiceImpl<CourseDetailsMapper, CourseDetails> implements CourseDetailsService {

}
