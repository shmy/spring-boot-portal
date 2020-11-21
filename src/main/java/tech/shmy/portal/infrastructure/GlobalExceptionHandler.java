package tech.shmy.portal.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.shmy.portal.application.domain.ResultBean;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean<Object> defaultExceptionHandler(HttpServletRequest req, Exception e) {
        log.error(e.getMessage());
        return ResultBean.error(e.getMessage());
    }
}