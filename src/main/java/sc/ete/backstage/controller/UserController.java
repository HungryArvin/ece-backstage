package sc.ete.backstage.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import sc.ete.backstage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import sc.ete.backstage.entity.VO.ResponseUserVO;
import sc.ete.backstage.service.*;
import sc.ete.backstage.utils.JwtUtil;
import sc.ete.backstage.utils.MD5;
import sc.ete.backstage.utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public R userAdd(@RequestBody User user){
        if (StrUtil.isNotEmpty(user.getUsername()) && StrUtil.isNotEmpty(user.getPassword())) {
            //用户名不存在
            final QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username",user.getUsername());
            final int count = userService.count(userQueryWrapper);
            if (count > 0) {
                return R.error().message("用户名已存在");
            }
            //密码md5加密
            user.setPassword(MD5.encrypt(user.getPassword()));
            final boolean save = userService.save(user);
            //赋予超管权限
            final UserRole userRole = new UserRole();
            userRole.setRoleId(1);
            userRole.setUserId(user.getUserId());
            userRoleService.save(userRole);
        }
        return R.right().data("user",user);
    }

    @GetMapping("/info")
    public R  getUserInfo(HttpServletRequest request){
        //获取用户id
        final String userID = JwtUtil.getMemberIdByJwtToken(request);
        if (StrUtil.isBlank(userID)) {
            return R.error().message("登陆有误");
        }
        final User user = userService.getById(userID);
        if (user == null) {
            return R.error();
        }
        final ResponseUserVO responseUserVO = new ResponseUserVO();
        //判断是否是学生
        final QueryWrapper<StudentInfo> studentInfoQueryWrapper = new QueryWrapper<>();
        studentInfoQueryWrapper.eq("user_id",userID);
        final StudentInfo studentInfo = studentInfoService.getOne(studentInfoQueryWrapper);
        final QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        teacherInfoQueryWrapper.eq("teacher_num",user.getUsername());
        final TeacherInfo teacherInfo = teacherInfoService.getOne(teacherInfoQueryWrapper);
        if (studentInfo != null) {
            responseUserVO.setName(studentInfo.getStudentName());
            responseUserVO.setAvatar("http://arvin-bucket.oss-cn-beijing.aliyuncs.com/image/2022/04/02/7d8395eavator.jpg");
            responseUserVO.setIntroduction(studentInfo.getDepartment());
        } else if(teacherInfo != null){
            responseUserVO.setName(teacherInfo.getTeacherName());
            responseUserVO.setAvatar(teacherInfo.getTeacherCover());
            responseUserVO.setIntroduction(teacherInfo.getTeacherIntroduction());
            responseUserVO.setValue(teacherInfo.getSatisfactionPoint());
        } else{
            responseUserVO.setName("Admin");
            responseUserVO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            responseUserVO.setIntroduction("超管后台");
        }
        //查询用户角色名称
        final QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",userID);
        final List<UserRole> userRoles = userRoleService.list(userRoleQueryWrapper);
        final List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        if (roleIds.isEmpty()) {
            return R.error();
        }
        final List<Role> roles = roleService.listByIds(roleIds);
        final List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        responseUserVO.setRoles(roleNames);
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

