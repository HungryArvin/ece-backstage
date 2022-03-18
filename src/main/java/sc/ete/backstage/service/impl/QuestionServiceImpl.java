package com.baomidou.backstage.service.impl;

import com.baomidou.backstage.entity.Question;
import com.baomidou.backstage.mapper.QuestionMapper;
import com.baomidou.backstage.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
