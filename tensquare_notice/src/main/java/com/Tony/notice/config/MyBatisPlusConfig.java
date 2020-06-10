package com.Tony.notice.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//配置Mapper包扫描
@MapperScan("com.Tony.notice.dao")
public class MyBatisPlusConfig {

//  用于分页
    @Bean
    public PaginationInterceptor createPaginationInterceptor() {
        return new PaginationInterceptor();
    }
}