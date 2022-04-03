package sc.ete.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author arvin
 * @since 2022-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="QuestionManageTarget对象", description="")
public class QuestionManageTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷id")
    private Integer manageId;

    @ApiModelProperty(value = "答卷人id")
    private Integer targetId;


}
