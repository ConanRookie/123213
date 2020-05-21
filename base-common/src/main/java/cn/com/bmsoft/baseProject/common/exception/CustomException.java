package cn.com.bmsoft.baseProject.common.exception;


import cn.com.bmsoft.baseProject.common.model.response.ResultCode;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    //错误代码
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
