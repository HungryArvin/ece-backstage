package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.Department;
import sc.ete.backstage.mapper.DepartmentMapper;
import sc.ete.backstage.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
