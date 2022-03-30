package sc.ete.backstage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sc.ete.backstage.entity.ClassInfo;
import sc.ete.backstage.entity.CourseDetails;
import sc.ete.backstage.service.CourseDetailsService;
import sc.ete.backstage.utils.R;

import java.util.List;

/**
 * <p>
 * 课程详情表 前端控制器
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@RestController
@RequestMapping("/backstage/course-details")
public class CourseDetailsController {
    @Autowired
    private CourseDetailsService courseDetailsService;

    @PostMapping("/save")
    public R saveCourseDetails(@RequestBody CourseDetails courseDetails){
        if (courseDetails.getCourseCover().isEmpty()) {
            courseDetails.setCourseCover("http://arvin-bucket.oss-cn-beijing.aliyuncs.com/image/2022/03/30/bbb62bfenglish2.webp");
        }
        if (!courseDetails.getStartTime().isEmpty()) {
            final String[] s_t = courseDetails.getStartTime().split("T");
            courseDetails.setStartTime(s_t[0]);
        }
        if (!courseDetails.getEndTime().isEmpty()) {
            final String[] s_t = courseDetails.getEndTime().split("T");
            courseDetails.setEndTime(s_t[0]);
        }
        final boolean save = courseDetailsService.saveOrUpdate(courseDetails);
        return R.right();
    }

    @GetMapping("/getAll/{page}/{size}")
    public R getAll(@PathVariable(name = "page",required = true)Integer page,
                    @PathVariable(name = "size" )Integer size) {
        final Page<CourseDetails> classInfoPage = new Page<>(page, size);

        final Page<CourseDetails> result = courseDetailsService.page(classInfoPage);
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",result.getRecords());
    }

    @DeleteMapping("/delete/{id}")
    public R deleteCourseById(@PathVariable(name = "id")Integer id){
        final boolean b = courseDetailsService.removeById(id);
        return R.right();
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable(name = "id")Integer id){
        final CourseDetails courseDetails = courseDetailsService.getById(id);
        return R.right().data("courseDetails",courseDetails);
    }

    @GetMapping("/getNameList")
    public R getCourseList(){
        final QueryWrapper<CourseDetails> courseDetailsQueryWrapper = new QueryWrapper<>();
        courseDetailsQueryWrapper.select("course_id","course_name");
        final List<CourseDetails> list = courseDetailsService.list(courseDetailsQueryWrapper);
        return R.right().data("list",list);
    }
}

