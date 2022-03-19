package sc.ete.backstage.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sc.ete.backstage.entity.*;
import sc.ete.backstage.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ：Arvin
 * @Description: 数据库校验逻辑
 * @name：LocalUserDetailsService
 * @date ：2022/3/19 22:49
 */
@Service
public class LocalUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        final QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        final User userInfo = userService.getOne(userQueryWrapper);
        if (userInfo == null) {
            throw new RuntimeException("用户名密码错误");
        }
        //查询角色
        final List<Role> roles = getRolesByUserId(userInfo.getUserId());

        //查询权限
        final ArrayList<String> auths = new ArrayList<>();
        if (roles != null) {
            roles.stream().map(role -> getAuthByRoleId(role.getRoleId())).filter(Objects::nonNull).forEach(auth -> {
                for (Authority authority : auth) {
                    auths.add(authority.getAuthorityUrl());
                }
            });
        }
        //UserDetails封装
        return new SecurityUser(userInfo,auths);
    }

    //根据用户id 获取角色信息
    private List<Role> getRolesByUserId(Integer userId) {
        final QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",userId);
        final List<UserRole> userRoles = userRoleService.list(userRoleQueryWrapper);
        final List<Object> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return null;
        }
        final QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
              roleQueryWrapper.in("role_id", roleIds);
        return roleService.list(roleQueryWrapper);
    }

    //根据role id 获取权限地址
    private List<Authority> getAuthByRoleId(Integer id) {
        final QueryWrapper<RoleAuthority> roleAuthorityQueryWrapper = new QueryWrapper<>();
        roleAuthorityQueryWrapper.eq("role_id",id);
        final List<RoleAuthority> roleAuthorities = roleAuthorityService.list(roleAuthorityQueryWrapper);
        final List<Integer> authIds = roleAuthorities.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());

        if (authIds.isEmpty()) {
            return null;
        }
        final QueryWrapper<Authority> authorityQueryWrapper = new QueryWrapper<>();
        authorityQueryWrapper.in("authority_id",authIds);
        return authorityService.list(authorityQueryWrapper);
    }

}
