package com.baomidou.backstage.service.impl;

import com.baomidou.backstage.entity.User;
import com.baomidou.backstage.mapper.UserMapper;
import com.baomidou.backstage.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
