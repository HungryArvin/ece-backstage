package sc.ete.backstage.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author ：Arvin
 * @Description:TODO
 * @name：TeacherRequestVO
 * @date ：2022/3/30 21:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TeacherQueryVO {

    private Integer page;
    private Integer limit;
    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "教师编号")
    private String teacherNum;

    @ApiModelProperty(value = "办公室地点")
    private String officeLocation;

    private String department;
}
