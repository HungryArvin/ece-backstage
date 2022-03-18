package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.ClassInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sc.ete.backstage.mapper.ClassInfoMapper;
import sc.ete.backstage.service.ClassInfoService;

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

}
