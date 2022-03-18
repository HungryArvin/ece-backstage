package com.baomidou.backstage.service.impl;

import com.baomidou.backstage.entity.UserRole;
import com.baomidou.backstage.mapper.UserRoleMapper;
import com.baomidou.backstage.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色中间表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
