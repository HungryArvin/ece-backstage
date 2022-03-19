package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.TeacherInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sc.ete.backstage.mapper.TeacherInfoMapper;
import sc.ete.backstage.service.TeacherInfoService;

/**
 * <p>
 * 教师信息表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo> implements TeacherInfoService {

}
