package com.api.upstagram.common.Exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import com.api.upstagram.common.vo.ErrorResponseVO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ErrorResponseVO handleAllException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO("99", "문제가 발생하였습니다.\n고객센터에 문의해주세요.");

        return error;
    }

    // 제약조건 오류
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponseVO handleSQLDataException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO("99", "데이터 처리 중 오류가 발생하였습니다.");

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
