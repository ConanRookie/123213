package cn.com.bmsoft.baseProject.common.model.response;

import cn.com.bmsoft.baseProject.common.model.poi.DataImportResultItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据导入响应结果
 */
@ApiModel(value = "DataImportResult", description = "数据导入响应结果")
@Data
@ToString
public class DataImportResult extends ResponseResult {
    //导入结果列表
    @ApiModelProperty("结果列表")
	private List<DataImportResultItem> items = new ArrayList<DataImportResultItem>();

    //成功数
    @ApiModelProperty("成功数")
	private int totalSuccess = 0;

	//失败数
    @ApiModelProperty("失败数")
	private int totalFails = 0;

	public DataImportResult(ResultCode resultCode){
		super(resultCode);
	}
}
