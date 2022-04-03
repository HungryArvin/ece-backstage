package sc.ete.backstage.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;
import sc.ete.backstage.entity.Role;
import sc.ete.backstage.entity.StudentInfo;
import sc.ete.backstage.entity.TeacherInfo;
import sc.ete.backstage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import sc.ete.backstage.entity.VO.ResponseUserVO;
import sc.ete.backstage.service.StudentInfoService;
import sc.ete.backstage.service.TeacherInfoService;
import sc.ete.backstage.service.UserService;
import sc.ete.backstage.utils.JwtUtil;
import sc.ete.backstage.utils.MD5;
import sc.ete.backstage.utils.R;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private TeacherInfoService teacherInfoService;

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

        final List<String> roles = new ArrayList();
        final Role role = new Role();
        role.setRoleName("admin");
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

