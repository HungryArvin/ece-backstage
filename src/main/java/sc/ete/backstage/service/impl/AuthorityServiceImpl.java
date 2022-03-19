package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.Authority;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sc.ete.backstage.mapper.AuthorityMapper;
import sc.ete.backstage.service.AuthorityService;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

}
