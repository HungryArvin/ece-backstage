package sc.ete.backstage.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sc.ete.backstage.entity.DepartTarget;
import sc.ete.backstage.entity.Question;
import sc.ete.backstage.entity.QuestionManage;
import sc.ete.backstage.entity.VO.QuestionRequestVO;
import sc.ete.backstage.service.DepartTargetService;
import sc.ete.backstage.service.QuestionManageService;
import sc.ete.backstage.service.QuestionService;
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
@RequestMapping("/backstage/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private DepartTargetService departTargetService;

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
}

