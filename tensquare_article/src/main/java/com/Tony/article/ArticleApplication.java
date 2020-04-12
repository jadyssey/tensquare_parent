package com.Tony.article;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}

