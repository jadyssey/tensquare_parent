package com.Tony.article.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@ControllerAdvice //异常类声明
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class) //捕捉Exception下的子类异常
    @ResponseBody //处理结果转成json数据返回
    public Result error(Exception e){
        System.out.println("BaseException处理异常运行了");
        e.printStackTrace(); //输出异常到控制台
//        logger.error("异常",e);
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
