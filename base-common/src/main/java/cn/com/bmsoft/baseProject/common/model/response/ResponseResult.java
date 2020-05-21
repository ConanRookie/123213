package cn.com.bmsoft.baseProject.common.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 通用响应结果
 */
@ApiModel(value = "ResponseResult", description = "通用响应结果")
@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {

    //操作是否成功
    @ApiModelProperty("操作是否成功")
    boolean success = SUCCESS;

    //操作代码
    @ApiModelProperty("操作代码")
    int code = SUCCESS_CODE;

    //提示信息
    @ApiModelProperty("提示信息")
    String message;

//    //附带数据
//    @ApiModelProperty("附带数据")
//    Object data;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ResponseResult(ResultCode resultCode, Object data){
        this(resultCode);
//        this.data = data;
    }

    public ResponseResult(ResultCode resultCode, String message) {
        this(resultCode);
        this.message = message;
    }

//    public ResponseResult(ResultCode resultCode, String message, Object data) {
//        this(resultCode);
//        this.message = message;
//        this.data = data;
//    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }

}
