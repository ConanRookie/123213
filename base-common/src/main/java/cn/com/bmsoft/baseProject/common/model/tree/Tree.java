package cn.com.bmsoft.baseProject.common.model.tree;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用树
 */
@ApiModel(value = "Tree", description = "通用树")
@Data
@NoArgsConstructor
public class Tree {
	/**
	 * 节点ID
	 */
	@ApiModelProperty("节点ID")
	private Object id;
	/**
	 * 图标
	 */
    @ApiModelProperty("图标")
	private String icon;
	/**
	 * 链接
	 */
    @ApiModelProperty("链接")
	private String url;
	/**
	 * 节点文本
	 */
    @ApiModelProperty("节点文本")
	private String text;
	/**
	 * 节点是否展开，true false
	 */
    @ApiModelProperty("节点是否展开，true false")
	private boolean expanded = false;
	/**
	 * 节点是否被选中 true false
	 */
    @ApiModelProperty("节点是否被选中 true false")
	private boolean checked = false;
	/**
	 * 节点是否可被选中 true false
	 */
	@ApiModelProperty("节点是否可被选中 true false")
    private boolean checkAbled = true;
	/**
	 * 节点属性
	 */
    @ApiModelProperty("节点属性")
	private Map<String, Object> attributes = new HashMap<>();
	/**
	 * 节点的子节点
	 */
    @ApiModelProperty("节点的子节点")
	private List<Tree> children;
	/**
	 * 父标识
	 */
    @ApiModelProperty("父标识")
	private Object parentId;
	/**
	 * 是否有父节点
	 */
    @ApiModelProperty("是否有父节点")
	private boolean hasParent = false;
	/**
	 * 是否有子节点
	 */
    @ApiModelProperty("是否有子节点")
	private boolean hasChildren = false;
	/**
	 * 是否叶子节点
	 */
	@ApiModelProperty("是否叶子节点")
	private boolean isLeaf = true;
	/**
	 * 深度
	 */
	@ApiModelProperty("深度")
	private int depth = 0;
	/**
	 * 路径
	 */
	@ApiModelProperty("路径")
	private String path;
	/**
	 * 是否禁用
	 */
	@ApiModelProperty("是否禁用")
	private Boolean disabled = false;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}