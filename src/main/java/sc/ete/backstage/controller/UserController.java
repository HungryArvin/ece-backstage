package sc.ete.backstage.controller;


import org.springframework.web.bind.annotation.*;
import sc.ete.backstage.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import sc.ete.backstage.service.UserService;
import sc.ete.backstage.utils.R;

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

    @PostMapping("/login")
    public R userLogin(@RequestBody User user){
        final QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        userQueryWrapper.eq("password",user.getPassword());
        final User userInfo = userService.getOne(userQueryWrapper);
        return R.right().data("user",user);
    }
}

