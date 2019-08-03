package com.promote.controller;

import com.promote.error.BusinessException;
import com.promote.error.EmBusinessError;
import com.promote.response.CommonReturnType;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseController {
public static final String CONTENT_TYPE_FORMED = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleeException(HttpServletRequest request, Exception ex) {
        System.out.println(ex.toString());
        Map<String,Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrorCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else if (ex instanceof BindException){
            BindException bindException = (BindException)ex;
            responseData.put("errCode", EmBusinessError.PARAMETER_BIND_ERROR.getErrorCode());
            responseData.put("errMsg",EmBusinessError.PARAMETER_BIND_ERROR.getErrMsg());
            String message = bindException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            responseData.put("errDetail",message);
        } else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
            responseData.put("errDetail",ex.getLocalizedMessage());
        }

        return  CommonReturnType.create(responseData,"fail");
    }
}
