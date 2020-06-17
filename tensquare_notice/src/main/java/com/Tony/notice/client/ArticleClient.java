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

//要调用的服务名
@FeignClient("tensquare-article")
public interface ArticleClient {

    /**
     * GET
     * /article/{articleId}
     * 根据ID查询文章
     * @param articleId
     * @return
     */
    @GetMapping("article/{articleId}")
    public Result findById(@PathVariable("articleId") String articleId);
}
