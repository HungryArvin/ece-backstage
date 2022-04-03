package sc.ete.backstage.controller;


import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sc.ete.backstage.entity.*;
import sc.ete.backstage.entity.VO.QuestionManageVO;
import sc.ete.backstage.entity.VO.QuestionsSubmitVO;
import sc.ete.backstage.service.*;
import sc.ete.backstage.utils.JwtUtil;
import sc.ete.backstage.utils.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author arvin
 * @since 2022-04-01
 */
@RestController
@RequestMapping("/backstage/question-manage")
public class QuestionManageController {
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private DepartTargetService departTargetService;
    @Autowired
    private QuestionManageTargetService questionManageTargetService;
    @Autowired
    private QuestionAnswerService questionAnswerService;

    @PutMapping("/updateStatus/{id}")
    public R updateStatus(@PathVariable(name = "id")Integer id) {
        if (id == null) {
            return R.error();
        }
        //检查数据库状态
        final QuestionManage questionManage = questionManageService.getById(id);
        final Integer status = questionManage.getStatus();
        questionManage.setStatus(status == 0 ? 1 : 0);
        questionManageService.updateById(questionManage);
        return R.right();
    }

    @GetMapping("/getAll/{page}/{size}")
    public R getAll(@PathVariable(name = "page")Integer page,
                    @PathVariable(name = "size")Integer size) {
        final Page<QuestionManage> questionManagePage = new Page<>(page, size);
        final Page<QuestionManage> result = questionManageService.page(questionManagePage);
        final List<QuestionManage> records = result.getRecords();
        final List<QuestionManageVO> list = records.stream().map(questionManage -> {
            final QuestionManageVO questionManageVO = new QuestionManageVO();
            BeanUtils.copyProperties(questionManage, questionManageVO);
            questionManageVO.setStatus(questionManage.getStatus() == 1 ? true : false);
            return questionManageVO;
        }).collect(Collectors.toList());
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",list);
    }

    @GetMapping("/getQuestions/{id}")
    public R getQuestions(@PathVariable(name = "id")Integer id) {
        if (id == null) {
            return R.error();
        }
        final QuestionManage questionManage = questionManageService.getById(id);
        //根据中间表查询出所有子问题
        final QueryWrapper<DepartTarget> departTargetQueryWrapper = new QueryWrapper<>();
        departTargetQueryWrapper.eq("dep_id",id);
        final List<DepartTarget> departTargetList = departTargetService.list(departTargetQueryWrapper);
        final List<String> questionIds = departTargetList.stream().map(DepartTarget::getTargetId).collect(Collectors.toList());
        final List<Question> questionList = questionService.listByIds(questionIds);

        return R.right().data("list",questionList).data("name",questionManage.getName());
    }
    /**
     * create by: Arvin
     * description: 学生获取未填写的问卷
     * @params:
     * @return
     */
    @GetMapping("/getByToken/{page}/{size}")
    public R getByToken(@PathVariable(name = "page")Integer page,
                        @PathVariable(name = "size")Integer size,
                        HttpServletRequest request) {
        //获取学生studentNum
        final String userID = JwtUtil.getMemberIdByJwtToken(request);
        //查询已完成的问卷
        final QueryWrapper<QuestionManageTarget> questionManageTargetQueryWrapper = new QueryWrapper<>();
        questionManageTargetQueryWrapper.eq("target_id",userID);
        final List<QuestionManageTarget> questionManageTargets = questionManageTargetService.list(questionManageTargetQueryWrapper);
        final List<Integer> manageIds = questionManageTargets.stream().map(QuestionManageTarget::getManageId).collect(Collectors.toList());
        //查询未完成的问卷
        final QueryWrapper<QuestionManage> questionManageQueryWrapper = new QueryWrapper<>();
        //过滤未开启的问卷
        questionManageQueryWrapper.eq("status",1);
        if (!manageIds.isEmpty()) {
            questionManageQueryWrapper.notIn("id",manageIds);
        }
        final Page<QuestionManage> questionManagePage = new Page<>(page, size);

        final Page<QuestionManage> result = questionManageService.page(questionManagePage,questionManageQueryWrapper);
        final List<QuestionManage> records = result.getRecords();
        final List<QuestionManageVO> list = records.stream().map(questionManage -> {
            final QuestionManageVO questionManageVO = new QuestionManageVO();
            BeanUtils.copyProperties(questionManage, questionManageVO);
            questionManageVO.setStatus(questionManage.getStatus() == 1 ? true : false);
            return questionManageVO;
        }).collect(Collectors.toList());
        return R.right().data("total",result.getTotal()).data("currentPage",result.getCurrent())
                .data("pages",result.getPages()).data("size",result.getSize()).data("list",list);
    }

    @PostMapping("/submitQuestions")
    public R studentSubmitQuestions(@RequestBody QuestionsSubmitVO questionsSubmitVO,
                                    HttpServletRequest request){
        //获取用户ID
        final String userID = JwtUtil.getMemberIdByJwtToken(request);
        //存储所有问题答案
        final List<Question> questionList = questionsSubmitVO.getQuestionList();
        questionList.forEach(question -> {
            final QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setQuestionId(question.getId());
            questionAnswer.setUserId(Integer.parseInt(userID));
            questionAnswer.setAnswer(question.getAnswer());
            questionAnswerService.save(questionAnswer);
        });
        //存储问卷
        final QuestionManageTarget questionManageTarget = new QuestionManageTarget();
        questionManageTarget.setManageId(Integer.parseInt(questionsSubmitVO.getManageId()));
        questionManageTarget.setTargetId(Integer.parseInt(userID));
        questionManageTargetService.save(questionManageTarget);
        return R.right();
    }
}

