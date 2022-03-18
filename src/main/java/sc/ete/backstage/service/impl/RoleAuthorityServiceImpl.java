package com.baomidou.backstage.service.impl;

import com.baomidou.backstage.entity.RoleAuthority;
import com.baomidou.backstage.mapper.RoleAuthorityMapper;
import com.baomidou.backstage.service.RoleAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-权限中间表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {

}
