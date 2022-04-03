package sc.ete.backstage.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.ClassTarget;
import sc.ete.backstage.entity.TeacherInfo;
import sc.ete.backstage.entity.VO.ClassInfoListVO;
import sc.ete.backstage.service.ClassInfoService;
import sc.ete.backstage.service.ClassTargetService;
import sc.ete.backstage.service.TeacherInfoService;
import sc.ete.backstage.utils.JwtUtil;
import sc.ete.backstage.utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/backstage/classInfo")
public class ClassInfoController {
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ClassTargetService classTargetService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @GetMapping("/getAll/{page}/{size}")
    public R getAll(@PathVariable(name = "page",required = true)Integer page,
                    @PathVariable(name = "size" )Integer size) {
        final Page<ClassInfo> classInfoPage = new Page<>(page, size);

        final Page<ClassInfo> result = classInfoService.page(classInfoPage);
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",result.getRecords());
    }

    @PostMapping("/save")
    public R saveClassInfo(@RequestBody ClassInfo classInfo){
        if (classInfo == null) {
            return R.error();
        }
        final Integer id = classInfoService.judgeAndReturn(classInfo);
        return R.right().data("id",id);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteByClassId(@PathVariable(name = "id")Integer id){
        if (id  == null) {
            return R.error();
        }
        classInfoService.removeById(id);
        return R.right();
    }

    @PutMapping("/update")
    public R updateClassInfo(@RequestBody ClassInfo classInfo){
        if (classInfo == null) {
            return R.error();
        }
        classInfoService.updateById(classInfo);
        return R.right();
    }

    @GetMapping("/getList")
    public R getClassAndLevelList(){
        final List<ClassInfo> list = classInfoService.list();
        return R.right().data("list",list);
    }

    @GetMapping("/getListByToken")
    public R getClassListByToken(HttpServletRequest request){
        //获取Id
        final String usernameFromToken = JwtUtil.getUsernameFromToken(request.getHeader("X-Token"));
        if (StrUtil.isBlank(usernameFromToken)) {
            return R.error();
        }
        final QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        teacherInfoQueryWrapper.eq("teacher_num",usernameFromToken);
        final TeacherInfo teacherInfo = teacherInfoService.getOne(teacherInfoQueryWrapper);
        final QueryWrapper<ClassTarget> classTargetQueryWrapper = new QueryWrapper<>();
        classTargetQueryWrapper.eq("target_id",teacherInfo.getTeacherId());
        final List<ClassTarget> classTargets = classTargetService.list(classTargetQueryWrapper);
        final List<Integer> classIds = classTargets.stream().map(ClassTarget::getClassId).collect(Collectors.toList());
        final List<ClassInfo> classInfos = classInfoService.listByIds(classIds);
        return R.right().data("classList",classInfos);
    }
}

