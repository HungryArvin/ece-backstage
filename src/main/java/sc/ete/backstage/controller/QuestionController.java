package sc.ete.backstage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sc.ete.backstage.entity.*;
import sc.ete.backstage.entity.VO.QuestionRequestVO;
import sc.ete.backstage.service.*;
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
@RequestMapping("/backstage/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private DepartTargetService departTargetService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private ClassTargetService classTargetService;


    @PostMapping("/save")
    public R saveQuestion(@RequestBody QuestionRequestVO questionRequestVO) {
        if (questionRequestVO != null) {
            //拿出question
            final List<QuestionRequestVO> domains = questionRequestVO.getDomains();
            domains.add(questionRequestVO);
            //创建问题管理表单
            final QuestionManage questionManage = new QuestionManage();
            questionManage.setName(questionRequestVO.getName());
            questionManage.setStatus(0);
            questionManage.setCompletedCount(0);
            questionManageService.save(questionManage);
            //存储
            domains.forEach(domain ->{
                final Question question = new Question();
                BeanUtils.copyProperties(domain,question);
                questionService.save(question);
                //将问题与管理id关联
                final DepartTarget departTarget = new DepartTarget();
                departTarget.setDepId(questionManage.getId());
                departTarget.setTargetId(question.getId()+"");
                departTargetService.save(departTarget);
            });
            return  R.right();
        }
        return R.error();
    }

    @PostMapping("/create")
    public R saveQuestion(@RequestBody QuestionRequestVO questionRequestVO, HttpServletRequest request) {
        //创建问题
        //拿出question
        final List<QuestionRequestVO> domains = questionRequestVO.getDomains();
        domains.add(questionRequestVO);
        final Question question = new Question();
        domains.forEach(q -> {
            question.setQuestion(q.getQuestion());
            question.setType(3);
            questionService.save(question);
            //创建老师与个人问卷的联系
            final String username = JwtUtil.getUsernameFromToken(request.getHeader("X-Token"));
            final QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
            teacherInfoQueryWrapper.eq("teacher_num",username);
            final TeacherInfo teacherInfo = teacherInfoService.getOne(teacherInfoQueryWrapper);
            final DepartTarget departTarget = new DepartTarget();
            departTarget.setDepId(teacherInfo.getTeacherId());
            departTarget.setTargetId(question.getId()+"");
            departTargetService.save(departTarget);
        });
        return R.right();

    }

    @GetMapping("/checkTeacherQuestion/{id}")
    public R  checkTeacherPresentQuestion(@PathVariable(name = "id")Integer id,
                                          HttpServletRequest request) {
        final StudentInfo studentInfo = studentInfoService.getById(id);
        if (studentInfo == null) {
            return R.error();
        }
        //查询老师的个人问卷id
        final String username = JwtUtil.getUsernameFromToken(request.getHeader("X-Token"));
        final QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        teacherInfoQueryWrapper.eq("teacher_num",username);
        final TeacherInfo teacherInfo = teacherInfoService.getOne(teacherInfoQueryWrapper);
        final Integer teacherID = teacherInfo.getTeacherId();
        final QueryWrapper<DepartTarget> departTargetQueryWrapper = new QueryWrapper<>();
        departTargetQueryWrapper.eq("dep_id",teacherID);
        final List<DepartTarget> departTargetList = departTargetService.list(departTargetQueryWrapper);
        final List<String> questionIds = departTargetList.stream().map(DepartTarget::getTargetId).collect(Collectors.toList());
        if (questionIds.isEmpty()) {
            return R.error().message("您还没有设置个人问卷，请到个人问卷位置添加");
        }
        final List<Question> questions = questionService.listByIds(questionIds);

        final List<Integer> ids = questions.stream().map(Question::getId).collect(Collectors.toList());
        //查询该学生是否回答
        final QueryWrapper<QuestionAnswer> questionAnswerQueryWrapper = new QueryWrapper<>();
        questionAnswerQueryWrapper.in("question_id",ids);
        final List<QuestionAnswer> questionAnswerList = questionAnswerService.list(questionAnswerQueryWrapper);
        if (questionAnswerList.isEmpty()) {
            return R.error().message("学生没有回答");
        }
        questionAnswerList.forEach(questionAnswer -> {
            for (Question question : questions) {
                if(questionAnswer.getQuestionId().equals(question.getId())){
                    question.setAnswer(questionAnswer.getAnswer());
                }
            }
        });

        return R.right().data("list",questions).data("name",studentInfo.getStudentName());
    }

    @GetMapping("/getPresentQuestion")
    public R getTeacherPresentQuestion(HttpServletRequest request){
        String studentId = JwtUtil.getMemberIdByJwtToken(request);
        final QueryWrapper<StudentInfo> studentInfoQueryWrapper = new QueryWrapper<>();
        studentInfoQueryWrapper.eq("user_id", studentId);
        final StudentInfo studentInfo = studentInfoService.getOne(studentInfoQueryWrapper);

        studentId = studentInfo.getStudentId()+"";
        final StudentInfo byId = studentInfoService.getById(studentId);
        if (byId == null) {
            return R.error().message("账号数据错误！");
        }
        final String classId = byId.getClassId();
        final QueryWrapper<ClassTarget> classTargetQueryWrapper = new QueryWrapper<>();
        classTargetQueryWrapper.eq("class_id",classId);
        //查询班级绑定的老师
        final List<ClassTarget> classTargets = classTargetService.list(classTargetQueryWrapper);
        final List<Integer> ids = classTargets.stream().map(ClassTarget::getTargetId).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return R.error().message("对应班级暂时没有老师管理");
        }
        final QueryWrapper<TeacherInfo> teacherInfoQueryWrapper = new QueryWrapper<>();
        teacherInfoQueryWrapper.in("teacher_id",ids);
        final List<TeacherInfo> teacherInfos = teacherInfoService.list(teacherInfoQueryWrapper);
        final List<Integer> teacherIds = teacherInfos.stream().map(TeacherInfo::getTeacherId).collect(Collectors.toList());
        if (teacherIds.isEmpty()) {
            return R.right().message("老师没有设置个人问卷");
        }
        //通过老师id查个人问卷
        final QueryWrapper<DepartTarget> departTargetQueryWrapper = new QueryWrapper<>();
        departTargetQueryWrapper.in("dep_id",teacherIds);
        final List<DepartTarget> departTargetList = departTargetService.list(departTargetQueryWrapper);
        final List<String> questionIds = departTargetList.stream().map(DepartTarget::getTargetId).collect(Collectors.toList());
        if (questionIds.isEmpty()) {
            return R.error().message("问卷丢失");
        }
        final QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("type",3);
        questionQueryWrapper.in("id",questionIds);
        final List<Question> questions = questionService.list(questionQueryWrapper);
        return R.right().data("list",questions);
    }
    @GetMapping("/checkStudentAnswer/{id}")
    public R  checkStudentAnswer(@PathVariable(name = "id")Integer id) {
        final StudentInfo studentInfo = studentInfoService.getById(id);
        if (studentInfo == null) {
            return R.error();
        }
        //查询教评问卷
        final List<QuestionManage> questionManages = questionManageService.list();
        final List<Integer> manageIds = questionManages.stream().map(QuestionManage::getId).collect(Collectors.toList());
        final QueryWrapper<DepartTarget> departTargetQueryWrapper = new QueryWrapper<>();
        departTargetQueryWrapper.in("dep_id",manageIds);
        final List<DepartTarget> departTargetList = departTargetService.list(departTargetQueryWrapper);
        final List<String> questionIds = departTargetList.stream().map(DepartTarget::getTargetId).collect(Collectors.toList());
        if (questionIds.isEmpty()) {
            return R.error().message("您还没有设置问卷，请到问卷栏目添加添加问卷");
        }
        final QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.in("type",1,2);
        questionQueryWrapper.in("id",questionIds);
        final List<Question> questions = questionService.list(questionQueryWrapper);

        final List<Integer> ids = questions.stream().map(Question::getId).collect(Collectors.toList());
        //查询该学生是否回答
        final QueryWrapper<QuestionAnswer> questionAnswerQueryWrapper = new QueryWrapper<>();
        questionAnswerQueryWrapper.in("question_id",ids);
        final List<QuestionAnswer> questionAnswerList = questionAnswerService.list(questionAnswerQueryWrapper);
        if (questionAnswerList.isEmpty()) {
            return R.error().message("学生没有回答");
        }
        questionAnswerList.forEach(questionAnswer -> {
            for (Question question : questions) {
                if(questionAnswer.getQuestionId().equals(question.getId())){
                    question.setAnswer(questionAnswer.getAnswer());
                }
            }
        });

        return R.right().data("list",questions).data("name",studentInfo.getStudentName());
    }

}

