package com.api.upstagram.common.Exception;

import com.api.upstagram.common.vo.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.upstagram.common.vo.ErrorResponseVO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseVO> handleAllException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(Response.ERROR.getCode(), Response.ERROR.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponseVO> handleAccessException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(Response.ERROR.getCode(), e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 제약조건 오류
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseVO> handleSQLDataException(final Exception e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(Response.CONSTRAINTS_ERROR.getCode(), Response.CONSTRAINTS_ERROR.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Custom Exception 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseVO> handleCustomException(final CustomException e) {
        log.error(e.getClass().getName());
        log.error("error", e);

        ErrorResponseVO error = new ErrorResponseVO(e.getCode(), e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
