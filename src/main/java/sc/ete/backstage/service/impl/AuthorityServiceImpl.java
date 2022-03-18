package com.baomidou.backstage.service.impl;

import com.baomidou.backstage.entity.Authority;
import com.baomidou.backstage.mapper.AuthorityMapper;
import com.baomidou.backstage.service.AuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
