package sc.ete.backstage.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sc.ete.backstage.entity.Question;
import sc.ete.backstage.entity.VO.QuestionRequestVO;
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

    @PostMapping("/save")
    public R saveQuestion(@RequestBody QuestionRequestVO questionRequestVO) {
        if (questionRequestVO != null) {
            //拿出question
            final List<QuestionRequestVO> domains = questionRequestVO.getDomains();
            domains.add(questionRequestVO);
            //存储
            domains.forEach(domain ->{
                final Question question = new Question();
                BeanUtils.copyProperties(domain,question);
                questionService.save(question);
            });
        }
        return R.error();
    }
}

