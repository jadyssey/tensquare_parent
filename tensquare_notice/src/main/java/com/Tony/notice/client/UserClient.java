package com.Tony.notice.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@FeignClient("tensquare_user")
public interface UserClient {

    /**
     * 根据用户Id查询用户
     * GEt
     * http://127.0.0.1:9008/User/{userId}
     * @param userId
     * @return
     */
    @GetMapping("User/{userId}")
    public Result findById(@PathVariable("userId") String userId);
}
