package com.Tony.article.client;

import com.Tony.article.pojo.Notice;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author larry
 * @date 20/8/21 16:41
 * @description
 */

@FeignClient("tensquare-notice")
public interface NoticeClient {
    /**
     * 添加消息
     * @param notice
     * @return
     */
    @PostMapping("notice")
    public Result add(@RequestBody Notice notice);
}
