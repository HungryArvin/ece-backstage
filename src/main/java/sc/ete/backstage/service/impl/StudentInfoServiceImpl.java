package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.StudentInfo;
import sc.ete.backstage.mapper.StudentInfoMapper;
import sc.ete.backstage.service.StudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
