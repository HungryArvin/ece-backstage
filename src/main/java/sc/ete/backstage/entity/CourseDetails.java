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
 * 课程详情表
 * </p>
 *
 * @author arvin
 * @since 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CourseDetails对象", description="课程详情表")
public class CourseDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增ID")
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程简介")
    private String courseIntroduction;

    @ApiModelProperty(value = "课程封面地址")
    private String courseCover;

    @ApiModelProperty(value = "课程绩点")
    private Integer courseGradePoint;

    @ApiModelProperty(value = "课程开始时间")
    private Date startTime;

    @ApiModelProperty(value = "课程结束时间")
    private Date endTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
