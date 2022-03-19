package sc.ete.backstage.controller;


import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;
import sc.ete.backstage.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import sc.ete.backstage.service.UserService;
import sc.ete.backstage.utils.MD5;
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

    @PostMapping("/add")
    public R userLogin(@RequestBody User user){
        if (StrUtil.isNotEmpty(user.getUsername()) && StrUtil.isNotEmpty(user.getPassword())) {
            //密码md5加密
            user.setPassword(MD5.encrypt(user.getPassword()));
            final boolean save = userService.save(user);
        }
        return R.right().data("user",user);
    }

}

