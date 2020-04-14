package com.Tony.article;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */


//spring启动类
@SpringBootApplication
//配置mapper包扫描
@MapperScan("com.Tony.article.dao")
public class ArticleApplication {

    public static void main(String[] args){
        SpringApplication.run(ArticleApplication.class,args);
    }
    //创建IdWorker实例用于生成唯一ID
    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(1,1);//第一个是机器号，第二个是序列号
    }
}

