package cn.com.bmsoft.baseProject.common.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 查询列表响应结果
 */
@ApiModel(value = "QueryResponseResult", description = "查询列表响应结果")
@Data
@ToString
@NoArgsConstructor
public class QueryResponseResult extends ResponseResult {

    //数据列表
    @ApiModelProperty("数据列表")
    private List rows;
    //数据总数
    @ApiModelProperty("数据总数")
    private long total;

    public QueryResponseResult(ResultCode resultCode){
        super(resultCode);
    }

    public QueryResponseResult(ResultCode resultCode, List rows, long total){
        super(resultCode);
        this.rows = rows;
        this.total = total;
    }

    public static QueryResponseResult FAIL(){
        return new QueryResponseResult(CommonCode.FAIL, null, 0);
    }
}
