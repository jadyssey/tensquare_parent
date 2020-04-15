package com.Tony.encrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy  //开启zuul网关
public class EncryptApplication {

    public static void main(String[] args){
        SpringApplication.run(EncryptApplication.class,args);
    }

}
