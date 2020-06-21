package com.kok.sport.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kok.sport.base.Result;

@ControllerAdvice
public class ExceptionHandle {
@ExceptionHandler(value = Exception.class)
@ResponseBody
public Result handle(Exception e) {
	//ExceptionHandle
//    if (e instanceof GirlException) {
//        GirlException girlException = (GirlException) e;
//        return ResultUtil.error(girlException.getCode(), girlException.getMessage());
//    } else {
        return  new Result("501", "服务端调用异常", e);
        		//ResultUtil.error(-1,"未知错误");
    }
 
}
 
