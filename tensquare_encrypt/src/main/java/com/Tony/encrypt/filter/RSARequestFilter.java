package com.Tony.encrypt.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@Component
public class RSARequestFilter extends ZuulFilter {


    @Override
    public String filterType() {
        //过滤器在什么环境执行，解密操作需要在转发之前执行
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //设置过滤器执行顺序
        return FilterConstants.PRE_DECORATION_FILTER_ORDER +1 ;
    }

    @Override
    public boolean shouldFilter() {
        //是否使用过滤器，True是使用过滤器
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuulException执行了");
        return null;
    }
}
