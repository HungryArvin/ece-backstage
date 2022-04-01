package sc.ete.backstage.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.io.Serializable;

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
@ApiModel(value="Question对象", description="")
public class QuestionRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    @ApiModelProperty(value = "问题内容")
    private String question;

    @ApiModelProperty(value = "问题类型")
    private Integer type;

    private List<QuestionRequestVO> domains;


}
