package com.api.upstagram.common.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.upstagram.common.vo.ErrorResponseVO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ErrorResponseVO handleAllException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO("00", e.getMessage());

        return error;
    }

    @ExceptionHandler(CustomException.class)
    public ErrorResponseVO handleCustomException(final CustomException e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(e.getCode(), e.getMessage());

        return error;
    }

}
