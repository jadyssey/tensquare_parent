package com.Tony.encrypt.filter;

import com.Tony.encrypt.rsa.RsaKeys;
import com.Tony.encrypt.service.RsaService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@Component
public class RSARequestFilter extends ZuulFilter {

    @Autowired
    private RsaService rsaService; //注入rsaService用于调取解密函数

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
        /**
         * 1. 前端用公钥加密，然后通过请求体传给后端
         * 2. 从request body中读取出加密后的请求参数
         * 3. 将加密后的请求参数用私钥解密
         * 4. 将解密后的请求参数写回request body中
         * 5. 转发请求
         */
        System.out.println("zuulException执行了");

        //获取RequestContext容器 拿到Request请求和Response响应
        RequestContext ctx = RequestContext.getCurrentContext();

        //通过容器获得Request和Response请求响应
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();


        String requestData = null;//声明存放加密后的数据的变量
        String decryptData = null;//声明存放解密后的数据变量

        try {
            //通过Request获取inputStream
            ServletInputStream inputStream = request.getInputStream();

            //从inputsteam中得到加密后的数据
            requestData = StreamUtils.copyToString(inputStream, Charsets.UTF_8);
            System.out.println(requestData);//输出加密后的数据

            //对加密后的数据进行解密操作
            if(!Strings.isNullOrEmpty(requestData)){
               decryptData = rsaService.RSADecryptDataPEM(requestData, RsaKeys.getServerPrvKeyPkcs8());
                System.out.println(decryptData);
            }

            if(!Strings.isNullOrEmpty(decryptData)) {
                System.out.println("json字符串写入request body");
                final byte[] reqBodyBytes = decryptData.getBytes();
                ctx.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }

                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            }

            // 设置request请求头中的Content-Type为application/json，否则api接口模块需要进行url转码操作
            ctx.addZuulRequestHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON) + ";charset=UTF-8");
        } catch (Exception e) {
            System.out.println(this.getClass().getName() + "运行出错" + e.getMessage());
        }
        return null;
    }

}
