package com.Tony.user.controller;

import com.Tony.user.pojo.User;
import com.Tony.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@RestController
@RequestMapping("/user")
//使当前类中的方法支持跨域
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    //GEt   http://127.0.0.1:9008/user/{userId}     根据用户Id查询用户
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("userId") String userId){
        User user = userService.findById(userId);
        return new Result(true, StatusCode.OK,"根据ID查询用户成功",user);
    }
}
