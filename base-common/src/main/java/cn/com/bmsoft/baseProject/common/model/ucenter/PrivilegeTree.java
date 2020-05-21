package cn.com.bmsoft.baseProject.common.model.ucenter;

import cn.com.bmsoft.baseProject.common.model.tree.Tree;
import lombok.Data;
import lombok.ToString;

/**
 * @author: Boxing
 * @version: 1.0
 **/
@Data
@ToString
public class PrivilegeTree extends Tree {
    //权限类型
    private Integer category;
    //权限编码
    private String code;
}
