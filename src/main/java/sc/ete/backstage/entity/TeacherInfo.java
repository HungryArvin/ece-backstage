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
 * 教师信息表
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TeacherInfo对象", description="教师信息表")
public class TeacherInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增ID")
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;

    @ApiModelProperty(value = "教师名称")
    private String teacherName;

    @ApiModelProperty(value = "性别")
    private Integer teacherSex;

    @ApiModelProperty(value = "教师编号")
    private String teacherNum;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;

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

    @ApiModelProperty(value = "满意分值")
    private Integer satisfactionPoint;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
