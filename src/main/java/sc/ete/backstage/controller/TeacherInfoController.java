package sc.ete.backstage.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sc.ete.backstage.entity.*;
import sc.ete.backstage.entity.VO.TeacherQueryVO;
import sc.ete.backstage.entity.VO.TeacherRequestVO;
import sc.ete.backstage.entity.VO.TeacherResponseVO;
import sc.ete.backstage.entity.VO.TeacherUpdateVO;
import sc.ete.backstage.service.*;
import sc.ete.backstage.utils.MD5;
import sc.ete.backstage.utils.R;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 教师信息表 前端控制器
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/backstage/teacher-info")
public class TeacherInfoController {
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassTargetService classTargetService;
    @Autowired
    private CourseTeacherService courseTeacherService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private CourseDetailsService courseDetailsService;

    @PostMapping("/save")
    public R saveTeacherInfo(@RequestBody TeacherRequestVO teacherRequestVO){
        //创建教师基本信息
        final TeacherInfo teacherInfo = new TeacherInfo();
        BeanUtils.copyProperties(teacherRequestVO,teacherInfo);
        //日期处理
        final DateTime dateTime = DateUtil.parseDate(teacherRequestVO.getBirthday());
        teacherInfo.setBirthday(dateTime);
        teacherInfoService.save(teacherInfo);
        //根据老师工号创建用户
        final User user = new User();
        user.setUsername(teacherInfo.getTeacherNum());
        user.setPassword(MD5.encrypt(teacherInfo.getTeacherNum()));
        userService.save(user);
        //根据老师ID绑定班级+课程
        final ClassTarget classTarget = new ClassTarget();
        classTarget.setTargetId(teacherInfo.getTeacherId());
        teacherRequestVO.getClassList().forEach(id -> {
            classTarget.setClassId(id);
            classTargetService.save(classTarget);
        });
        final CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setTeacherId(teacherInfo.getTeacherId());
        teacherRequestVO.getCourseList().forEach(id -> {
            courseTeacher.setCourseId(id);
            courseTeacherService.save(courseTeacher);
        });
        return R.right();
    }

    @GetMapping("/getById/{id}")
    public R getByTeacherId(@PathVariable(name = "id")Integer id){
        if (id != null) {
            //查teacher
            final TeacherInfo teacherInfo = teacherInfoService.getById(id);
            //查class
            final QueryWrapper<ClassTarget> classTargetQueryWrapper = new QueryWrapper<>();
            classTargetQueryWrapper.eq("target_id",id);
            final List<ClassTarget> classTargetList = classTargetService.list(classTargetQueryWrapper);
            final List<Integer> classIds = classTargetList.stream().map(ClassTarget::getClassId).collect(Collectors.toList());
//            final List<ClassInfo> classInfos = classInfoService.listByIds(classIds);

            //查course
            final QueryWrapper<CourseTeacher> courseTeacherQueryWrapper = new QueryWrapper<>();
            courseTeacherQueryWrapper.eq("teacher_id",id);
            final List<CourseTeacher> courseTeachers = courseTeacherService.list(courseTeacherQueryWrapper);
            final List<Integer> courseIds = courseTeachers.stream().map(CourseTeacher::getCourseId).collect(Collectors.toList());
//            final QueryWrapper<CourseDetails> courseDetailsQueryWrapper = new QueryWrapper<>();
//            courseDetailsQueryWrapper.select("course_id","course_name");
//            courseDetailsQueryWrapper.in("course_id",courseIds);
//            final List<CourseDetails> courseDetails = courseDetailsService.list(courseDetailsQueryWrapper);
            //响应结果封装
            final TeacherRequestVO teacherRequestVO = new TeacherRequestVO();
            BeanUtils.copyProperties(teacherInfo,teacherRequestVO);
            teacherRequestVO.setClassList(classIds);
            teacherRequestVO.setCourseList(courseIds);
            teacherRequestVO.setBirthday(teacherInfo.getBirthday().toString());
            return R.right().data("data",teacherRequestVO);
        }
        return R.error();
    }

    @PostMapping("/getAll")
    public R getAll(@RequestBody TeacherQueryVO teacherQueryVO) {
        if (teacherQueryVO == null) {
            return  R.error();
        }
        final Page<TeacherInfo> teacherInfoPage = new Page<>(teacherQueryVO.getPage(), teacherQueryVO.getLimit());
        final QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(teacherQueryVO.getTeacherNum())) {
            teacherInfoQueryWrapper.like("teacher_name",teacherQueryVO.getTeacherName());
        }
        if (StrUtil.isNotEmpty(teacherQueryVO.getTeacherNum())) {
            teacherInfoQueryWrapper.eq("teacher_num",teacherQueryVO.getTeacherNum());
        }
        if (StrUtil.isNotEmpty(teacherQueryVO.getOfficeLocation())) {
            teacherInfoQueryWrapper.like("office_location",teacherQueryVO.getOfficeLocation());
        }
        if (StrUtil.isNotEmpty(teacherQueryVO.getDepartment())) {
            teacherInfoQueryWrapper.like("department",teacherQueryVO.getDepartment());
        }
        final Page<TeacherInfo> result = teacherInfoService.page(teacherInfoPage, teacherInfoQueryWrapper);
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",result.getRecords());
    }

    @PutMapping("/update")
    public R updateInfo(@RequestBody TeacherUpdateVO teacherRequestVO){
        //创建教师基本信息
        final TeacherInfo teacherInfo = new TeacherInfo();
        BeanUtils.copyProperties(teacherRequestVO,teacherInfo);
        teacherInfoService.updateById(teacherInfo);
        //删除班级课程
        final QueryWrapper<CourseTeacher> courseTeacherQueryWrapper = new QueryWrapper<>();
        courseTeacherQueryWrapper.eq("teacher_id",teacherInfo.getTeacherId());
        courseTeacherService.remove(courseTeacherQueryWrapper);
        final QueryWrapper<ClassTarget> classTargetQueryWrapper = new QueryWrapper<>();
        classTargetQueryWrapper.eq("target_id",teacherInfo.getTeacherId());
        classTargetService.remove(classTargetQueryWrapper);
        //根据老师ID绑定班级+课程
        final ClassTarget classTarget = new ClassTarget();
        classTarget.setTargetId(teacherInfo.getTeacherId());
        teacherRequestVO.getClassList().forEach(id -> {
            classTarget.setClassId(id);
            classTargetService.saveOrUpdate(classTarget);
        });
        final CourseTeacher courseTeacher = new CourseTeacher();
        courseTeacher.setTeacherId(teacherInfo.getTeacherId());
        teacherRequestVO.getCourseList().forEach(id -> {
            courseTeacher.setCourseId(id);
            courseTeacherService.saveOrUpdate(courseTeacher);
        });
        return R.right();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable(name ="id")Integer id){
        if (id != null) {
            teacherInfoService.removeById(id);
            final QueryWrapper<CourseTeacher> courseTeacherQueryWrapper = new QueryWrapper<>();
            courseTeacherQueryWrapper.eq("teacher_id",id);
            courseTeacherService.remove(courseTeacherQueryWrapper);
            final QueryWrapper<ClassTarget> classTargetQueryWrapper = new QueryWrapper<>();
            classTargetQueryWrapper.eq("target_id",id);
            classTargetService.remove(classTargetQueryWrapper);
            return R.right();
        }
        return R.error();
    }
}

