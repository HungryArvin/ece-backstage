package sc.ete.backstage.controller;


import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import sc.ete.backstage.entity.Role;
import sc.ete.backstage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import sc.ete.backstage.entity.VO.ResponseUserVO;
import sc.ete.backstage.service.UserService;
import sc.ete.backstage.utils.MD5;
import sc.ete.backstage.utils.R;

import java.util.*;
import java.util.ArrayList;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/backstage/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public R userAdd(@RequestBody User user){
        if (StrUtil.isNotEmpty(user.getUsername()) && StrUtil.isNotEmpty(user.getPassword())) {
            //密码md5加密
            user.setPassword(MD5.encrypt(user.getPassword()));
            final boolean save = userService.save(user);
        }
        return R.right().data("user",user);
    }

    @GetMapping("/info")
    public R  getUserInfo(){
        final ResponseUserVO responseUserVO = new ResponseUserVO();
        responseUserVO.setName("admin");
        responseUserVO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        responseUserVO.setIntroduction("demo");
        final List<String> roles = new ArrayList();
//        final Role role = new Role();
//        role.setRoleName("admin");
        roles.add("admin");
        responseUserVO.setRoles(roles);
        return R.right().data("user",responseUserVO);
    }

    @PostMapping("/login")
    public R  userLoginMock(){
        return R.right().data("user","user").data("token","admin-token");
    }

    @PostMapping("/logout")
    public R  userLogout(){
        return R.right();
    }

}

