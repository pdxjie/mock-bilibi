package com.pdx.mockbilli.handler;

import com.pdx.mockbilli.entity.JsonResponse;
import com.pdx.mockbilli.entity.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kengge
 * @author 派 大 星
 * @website https://blog.csdn.net/Gaowumao
 * @Date 2022-07-04 22:53
 * @Description
 */

/**
 * 优先级是最高的
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CommonGlobalException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonException(HttpServletRequest request,Exception e){
        String errorMsg = e.getMessage();
        if (e instanceof ConditionException){
            String errorCode = ((ConditionException)e).getCode();
            return new JsonResponse<>(errorCode,errorMsg);
        }else {
            return new JsonResponse<>("500",errorMsg);
        }
    }

}
