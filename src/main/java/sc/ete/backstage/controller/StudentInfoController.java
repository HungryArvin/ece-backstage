package sc.ete.backstage.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.StudentInfo;
import sc.ete.backstage.entity.VO.ClassInfoListVO;
import sc.ete.backstage.entity.VO.StudentInfoResponseVO;
import sc.ete.backstage.entity.VO.StudentInfoVO;
import sc.ete.backstage.exception.MyException;
import sc.ete.backstage.handler.StudentExcelHandler;
import sc.ete.backstage.service.ClassInfoService;
import sc.ete.backstage.service.StudentInfoService;
import sc.ete.backstage.service.UserService;
import sc.ete.backstage.utils.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/backstage/student-info")
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassInfoService classInfoService;

    @PostMapping("/upload")
    public R uploadStudentInfoExcel(@RequestParam(value = "studentExcel")MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return R.error();
        }
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException ioException) {
            throw new MyException();
        }
        EasyExcel.read(inputStream, StudentInfoVO.class,new StudentExcelHandler(studentInfoService)).sheet().doRead();
        return R.created();
    }

    @GetMapping("/getAll/{page}/{size}")
    public R getAllStudentInfo(@PathVariable(name = "page")Integer page,@PathVariable(name = "size")Integer size){
        final Page<StudentInfo> studentInfoPage = new Page<>(page,size);
        final Page<StudentInfo> result = studentInfoService.page(studentInfoPage);
        final List<StudentInfo> records = result.getRecords();

        final List<StudentInfoResponseVO> collect = records.stream().map(studentInfo -> {
            final ClassInfo classInfo = classInfoService.getById(studentInfo.getClassId());
            final StudentInfoResponseVO studentInfoResponseVO = new StudentInfoResponseVO();
            final String date = DateUtil.format(studentInfo.getUpdateTime(), "yyyy-MM-dd hh:mm:ss");
            studentInfoResponseVO.setUpdateTime(date);
            BeanUtils.copyProperties(studentInfo, studentInfoResponseVO);
            if (classInfo != null) {
                studentInfoResponseVO.setClassName(classInfo.getClassName());
                studentInfoResponseVO.setLevel(classInfo.getLevel());
            }
            studentInfoResponseVO.setUpdateTime(date);
            return studentInfoResponseVO;
        }).collect(Collectors.toList());
        final ClassInfoListVO classList = classInfoService.getClassList();
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",collect)
                .data("classList",classList);
    }

    @PutMapping("/update")
    public R getAllStudentInfo(@RequestBody StudentInfoResponseVO studentInfoVO){
        if (studentInfoVO == null) {
            return R.error();
        }
        final StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(studentInfoVO,studentInfo);
        final ClassInfo classInfo = new ClassInfo();
        BeanUtils.copyProperties(studentInfoVO,classInfo);
        final Integer id = classInfoService.judgeAndReturn(classInfo);
        studentInfo.setClassId(id+"");
        studentInfoService.updateById(studentInfo);
        return R.right();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteStudentById(@PathVariable(name = "id",required = true) String id) {
        studentInfoService.removeById(id);
        return R.right();
    }

    @GetMapping("/getByQuery/{page}/{size}")
    public R getAllStudentInfo(@PathVariable(name = "page")Integer page,@PathVariable(name = "size")Integer size,
                               @RequestParam(name = "studentName", required = false)String studentName,
                               @RequestParam(name = "studentNum", required = false)String studentNum,
                               @RequestParam(name = "level", required = false)String level,
                               @RequestParam(name = "className", required = false)String className){
        final Page<StudentInfo> studentInfoPage = new Page<>(page,size);
        //条件添加
        final QueryWrapper<StudentInfo> studentInfoQueryWrapper = new QueryWrapper<>();
        List<Integer> classIds = null;
        if (StrUtil.isNotEmpty(className)) {
            final QueryWrapper<ClassInfo> classInfoQueryWrapper = new QueryWrapper<>();
            classInfoQueryWrapper.eq("class_name",className);

            if (StrUtil.isNotEmpty(level)) {
                classInfoQueryWrapper.eq("level",level);
            }
            final List<ClassInfo> classInfos = classInfoService.list(classInfoQueryWrapper);
            classIds = classInfos.stream().map(ClassInfo::getId).collect(Collectors.toList());
        }
        if (StrUtil.isNotEmpty(studentName)) {
            studentInfoQueryWrapper.like("student_name",studentName);
        }
        if (StrUtil.isNotEmpty(studentNum)) {
            studentInfoQueryWrapper.eq("student_num",studentNum);
        }
        if (classIds != null && !classIds.isEmpty()) {
            studentInfoQueryWrapper.in("class_id",classIds);
        }
        final Page<StudentInfo> result = studentInfoService.page(studentInfoPage,studentInfoQueryWrapper);
        final List<StudentInfo> records = result.getRecords();

        final List<StudentInfoResponseVO> collect = records.stream().map(studentInfo -> {
            final ClassInfo classInfo = classInfoService.getById(studentInfo.getClassId());
            final StudentInfoResponseVO studentInfoResponseVO = new StudentInfoResponseVO();
            final String date = DateUtil.format(studentInfo.getUpdateTime(), "yyyy-MM-dd hh:mm:ss");
            studentInfoResponseVO.setUpdateTime(date);
            BeanUtils.copyProperties(studentInfo, studentInfoResponseVO);
            if (classInfo != null) {
                studentInfoResponseVO.setClassName(classInfo.getClassName());
                studentInfoResponseVO.setLevel(classInfo.getLevel());
            }
            studentInfoResponseVO.setUpdateTime(date);
            return studentInfoResponseVO;
        }).collect(Collectors.toList());
        final ClassInfoListVO classList = classInfoService.getClassList();
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",collect)
                .data("classList",classList);
    }

}

