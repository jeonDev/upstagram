package com.api.upstagram.common.Exception;

import com.api.upstagram.common.vo.Response;
import org.springframework.dao.DataIntegrityViolationException;
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

        ErrorResponseVO error = new ErrorResponseVO(Response.ERROR.getCode(), Response.ERROR.getMessage());

        return error;
    }

    // 제약조건 오류
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponseVO handleSQLDataException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(Response.CONSTRAINTS_ERROR.getCode(), Response.CONSTRAINTS_ERROR.getMessage());

        return error;
    }

    // Custom Exception 처리
    @ExceptionHandler(CustomException.class)
    public ErrorResponseVO handleCustomException(final CustomException e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(e.getCode(), e.getMessage());

        return error;
    }

}
