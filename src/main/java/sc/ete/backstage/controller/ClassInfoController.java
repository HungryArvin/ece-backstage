package sc.ete.backstage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.VO.ClassInfoListVO;
import sc.ete.backstage.service.ClassInfoService;
import sc.ete.backstage.utils.R;

import java.util.List;


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
}

