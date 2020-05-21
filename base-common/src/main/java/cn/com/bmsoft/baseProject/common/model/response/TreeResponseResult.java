package cn.com.bmsoft.baseProject.common.model.response;

import cn.com.bmsoft.baseProject.common.model.tree.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 树列表响应结果
 * @param
 */
@ApiModel(value = "TreeResponseResult", description = "树列表响应结果")
@Data
@ToString
public class TreeResponseResult extends ResponseResult {

    //树列表
    @ApiModelProperty("树列表")
    private List<Tree> nodes;

    public TreeResponseResult(ResultCode resultCode, List<Tree> nodes){
        super(resultCode);
        this.nodes = nodes;
    }

}
