package com.Tony.user.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@Configuration
public class MyBatisPlusConfig {

    //配置分页查询插件：分页拦截器
    @Bean
    public PaginationInterceptor createPaginationInterceptor(){
        return new PaginationInterceptor();
    }


}
