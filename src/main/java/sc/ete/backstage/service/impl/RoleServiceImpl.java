package com.baomidou.backstage.service.impl;

import com.baomidou.backstage.entity.Role;
import com.baomidou.backstage.mapper.RoleMapper;
import com.baomidou.backstage.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
