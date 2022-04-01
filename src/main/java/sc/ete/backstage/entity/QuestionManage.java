package sc.ete.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2022-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="QuestionManage对象", description="")
public class QuestionManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问卷名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    @ApiModelProperty(value = "问卷状态 是否已经开放1（开放）0（未开放）")
    private Integer status;

    @ApiModelProperty(value = "问卷完成人数")
    private Integer completedCount;


}
