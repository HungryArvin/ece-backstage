package sc.ete.backstage.controller;


import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sc.ete.backstage.entity.VO.StudentInfoVO;
import sc.ete.backstage.exception.MyException;
import sc.ete.backstage.handler.StudentExcelHandler;
import sc.ete.backstage.service.StudentInfoService;
import sc.ete.backstage.service.UserService;
import sc.ete.backstage.utils.R;

import java.io.IOException;
import java.io.InputStream;

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
}

