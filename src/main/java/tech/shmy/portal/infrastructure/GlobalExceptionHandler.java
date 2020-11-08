package tech.shmy.portal.infrastructure;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.shmy.portal.application.domain.ResultBean;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean<Object> defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ResultBean.error(e.getMessage());
    }
}