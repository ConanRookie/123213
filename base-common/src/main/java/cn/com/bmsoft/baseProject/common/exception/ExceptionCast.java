package cn.com.bmsoft.baseProject.common.exception;


import cn.com.bmsoft.baseProject.common.model.response.ResultCode;

/**
 * 自定义异常工具类
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }

    public static void cast(ResultCode resultCode, String message) {
        throw new CustomException(resultCode, message);
    }
}
