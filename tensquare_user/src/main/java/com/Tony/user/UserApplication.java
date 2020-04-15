package com.Tony.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
//引导类
@SpringBootApplication
@MapperScan("com.Tony.user.dao")
public class UserApplication {
    public static void main(String[] args){
        SpringApplication.run(UserApplication.class, args);
    }
}
