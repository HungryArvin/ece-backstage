package com.baomidou.backstage.entity;

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
 * 学生信息表
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StudentInfo对象", description="学生信息表")
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增ID")
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    @ApiModelProperty(value = "学生名称")
    private String studentName;

    @ApiModelProperty(value = "学号")
    private String studentNum;

    @ApiModelProperty(value = "学生对应的账号id")
    private Integer userId;

    @ApiModelProperty(value = "所在部门")
    private String department;

    @ApiModelProperty(value = "所在班级")
    private String classId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
