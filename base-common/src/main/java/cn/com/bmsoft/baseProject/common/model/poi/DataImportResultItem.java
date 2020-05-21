package cn.com.bmsoft.baseProject.common.model.poi;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "DataImportResultItem", description = "数据导入条目")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataImportResultItem {
    //行数
    @ApiModelProperty("行数")
	private int lineNo;

    //是否成功
    @ApiModelProperty("是否成功")
	private boolean success = true;

    //错误信息
    @ApiModelProperty("错误信息")
	private String errorMsg = "";

    //数据对象
    @ApiModelProperty("数据对象")
    private Object data;

    public DataImportResultItem(int lineNo) {
        this.lineNo = lineNo;
    }
}
