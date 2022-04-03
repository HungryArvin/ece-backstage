package sc.ete.backstage.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.ete.backstage.entity.Question;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionsSubmitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String manageId;

    private List<Question> questionList;


}
