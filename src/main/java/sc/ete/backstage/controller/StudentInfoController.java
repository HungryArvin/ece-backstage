package sc.ete.backstage.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.StudentInfo;
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
            BeanUtils.copyProperties(studentInfo, studentInfoResponseVO);
            studentInfoResponseVO.setClassName(classInfo.getClassName());
            studentInfoResponseVO.setLevel(classInfo.getLevel());
            return studentInfoResponseVO;
        }).collect(Collectors.toList());

        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("date",collect);
    }

}

