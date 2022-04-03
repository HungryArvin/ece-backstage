package sc.ete.backstage.entity.VO;

import java.util.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sc.ete.backstage.entity.Role;

/**
 * @author ：Arvin
 * @Description:TODO
 * @name：ResponseUserVO
 * @date ：2022/3/20 18:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserVO对象", description="user响应结果封装")
public class ResponseUserVO {
    @ApiModelProperty(value = "用户名称")
    private String name;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户简介")
    private String introduction;

    //如果角色是老师的话 插入侧屏得分
    private Integer value;

    @ApiModelProperty(value = "角色")
    private List<String> roles;

}
