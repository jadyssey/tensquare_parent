package com.Tony.user.controller;

import com.Tony.user.pojo.User;
import com.Tony.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    //GEt   http://127.0.0.1:9008/User/{userId}     根据用户Id查询用户
    @RequestMapping(value = "{userId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String userId){
        User user = userService.findById(userId);
        return new Result(true, StatusCode.OK,"根据ID查询用户成功",user);
    }
}
