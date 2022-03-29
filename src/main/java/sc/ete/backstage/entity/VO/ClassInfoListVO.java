package sc.ete.backstage.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * @author ：Arvin
 * @Description:TODO
 * @name：ClassInfoListVO
 * @date ：2022/3/29 19:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassInfoListVO {
    @ApiModelProperty(value = "班级名称")
    private Set<String> className;

    @ApiModelProperty(value = "入学年份")
    private Set<String> level;
}
