package sc.ete.backstage.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.Date;

/**
 * @author ：Arvin
 * @Description:TODO
 * @name：TeacherRequestVO
 * @date ：2022/3/30 21:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TeacherRequestVO {
    @ApiModelProperty(value = "自增ID")
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "性别")
    private Integer teacherSex;

    @ApiModelProperty(value = "教师编号")
    private String teacherNum;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "办公室地点")
    private String officeLocation;

    @ApiModelProperty(value = "职称")
    private String professional;

    @ApiModelProperty(value = "所在部门")
    private String department;

    @ApiModelProperty(value = "教师简介")
    private String teacherIntroduction;

    @ApiModelProperty(value = "教师头像地址")
    private String teacherCover;

    private List<Integer> classList;

    private List<Integer> courseList;

}
