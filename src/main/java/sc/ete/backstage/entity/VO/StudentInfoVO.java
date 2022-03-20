package sc.ete.backstage.entity.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：Arvin
 * @Description:TODO
 * @name：StudentInfoVO
 * @date ：2022/3/21 0:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StudentInfoVO对象", description="student信息封装excel处理")
public class StudentInfoVO {
    @ApiModelProperty(value = "学生名称")
    @ExcelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty(value = "学号")
    @ExcelProperty("学号")
    private String studentNum;

    @ApiModelProperty(value = "所在系别")
    @ExcelProperty("所在系别")
    private String department;

    @ApiModelProperty(value = "所在班级")
    @ExcelProperty("所在班级")
    private String className;

    @ApiModelProperty(value = "入学年份")
    @ExcelProperty("入学年份")
    private String level;
}
