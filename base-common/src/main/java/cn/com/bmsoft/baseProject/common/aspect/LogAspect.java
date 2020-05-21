package cn.com.bmsoft.baseProject.common.aspect;

import cn.com.bmsoft.baseProject.common.exception.CustomException;
import cn.com.bmsoft.baseProject.common.model.log.Log;
import cn.com.bmsoft.baseProject.common.model.response.CommonCode;
import cn.com.bmsoft.baseProject.common.model.response.ResultCode;
import cn.com.bmsoft.baseProject.common.model.ucenter.UserInfo;
import cn.com.bmsoft.baseProject.common.service.impl.LogService;
import cn.com.bmsoft.baseProject.common.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * AOP 记录用户操作日志
 *
 * @author MrBird
 * @link https://mrbird.cc/Spring-Boot-AOP%20log.html
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Value("${app.open-aop-log}")
    private boolean openAopLog = false;
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(cn.com.bmsoft.baseProject.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        Throwable exception = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            exception = e;
            throw e;
        } finally {
            if (openAopLog) {
                // 执行时长(毫秒)
                long time = System.currentTimeMillis() - beginTime;
                try {
                    // 保存日志
                    this.saveLog(point, time, exception);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Async
    public void saveLog(ProceedingJoinPoint joinPoint, long time, Throwable exception) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserInfo) {
            username = ((UserInfo)principal).getUsername();
        } else {
            username = (String) principal;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        Log.class.getName();
        cn.com.bmsoft.baseProject.common.annotation.Log logAnnotation = method.getAnnotation(cn.com.bmsoft.baseProject.common.annotation.Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            log.setOperation(logAnnotation.value());
            // 注解上的实体类
            Annotation annotation = logAnnotation.entityClass().getAnnotation(Table.class);
            if (annotation != null) {
                Table tableAnnotation = (Table) annotation;
                log.setRefTable(tableAnnotation.name());
            }
        }
        // 请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            log.setParams(params.toString());
        }

        // 设置IP地址
        String ip = HttpUtil.getIpAddr(request);
        log.setIp(ip);
        log.setUsername(username);
        log.setTime(time);
        log.setCreateTime(new Date());
//        log.setLocation(AddressUtil.getCityInfo(log.getIp()));
        if (exception == null) {
            log.setSuccess(CommonCode.SUCCESS.success());
            log.setCode(CommonCode.SUCCESS.code());
            log.setMessage(CommonCode.SUCCESS.message());
        } else if (exception instanceof CustomException) {
            ResultCode resultCode = ((CustomException)exception).getResultCode();
            log.setSuccess(resultCode.success());
            log.setCode(resultCode.code());
            log.setMessage(exception.getMessage());
        } else {
            log.setSuccess(CommonCode.SERVER_ERROR.success());
            log.setCode(CommonCode.SERVER_ERROR.code());
            log.setMessage(exception.getMessage());
            StringBuffer buffer = new StringBuffer();
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                buffer.append("\t");
                buffer.append(stackTraceElement);
                buffer.append("\n");
            }
            log.setTrace(buffer.toString());
        }

        // 保存系统日志
        this.logService.save(log);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws IOException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                List list = new ArrayList();
                List paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(params, list.toArray(), paramList);

            } else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();
                    try {
                        aClass.getDeclaredMethod("toString", new Class[]{null});
                        // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                        params.append("  ").append(paramNames.get(i)).append(": ").append(JSON.toJSONString(args[i]));
                    } catch (NoSuchMethodException e) {
                        params.append("  ").append(paramNames.get(i)).append(": ").append(JSON.toJSONString(ToStringBuilder.reflectionToString(args[i])));
                    }

                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    params.append("  ").append(paramNames.get(i)).append(": ").append(file.getName());
                } else {
                    params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
                }
            }
        }
        return params;
    }
}
