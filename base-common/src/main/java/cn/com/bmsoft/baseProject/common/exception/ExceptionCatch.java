package cn.com.bmsoft.baseProject.common.exception;


import cn.com.bmsoft.baseProject.common.model.response.CommonCode;
import cn.com.bmsoft.baseProject.common.model.response.ResponseResult;
import cn.com.bmsoft.baseProject.common.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获类
 */
@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //ImmutableMap构建后元素不可修改
    //定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    //捕获CustomException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException) {
        ResultCode resultCode = customException.getResultCode();
        LOGGER.error("{}", customException.getMessage());
        return new ResponseResult(resultCode, customException.getMessage());
    }

    //捕获Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        //构建EXCEPTIONS
        if(EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode == null) {
            resultCode = CommonCode.SERVER_ERROR;
        }
        //打印堆栈信息
        StringBuffer stackTrace = new StringBuffer();
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stackTrace.append("\t");
            stackTrace.append(stackTraceElement);
            stackTrace.append("\n");
        }
        LOGGER.error("{}\n{}", exception.getMessage(), stackTrace.toString());

        return new ResponseResult(resultCode);
    }

    static {
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        builder.put(EmptyResultDataAccessException.class, CommonCode.INVALID_PARAM);
    }
}
