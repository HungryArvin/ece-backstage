package sc.ete.backstage.service.impl;

import sc.ete.backstage.entity.Question;
import sc.ete.backstage.mapper.QuestionMapper;
import sc.ete.backstage.service.QuestionService;
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
