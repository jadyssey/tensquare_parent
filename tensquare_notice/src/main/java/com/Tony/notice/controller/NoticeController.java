package com.Tony.notice.controller;

import com.Tony.notice.pojo.Notice;
import com.Tony.notice.service.NoticeService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService ;

    //根据条件分页查询

//    根据id查询消息通知
//   GET  http://127.0.0.1:9014/notice/{noticeId}
    @RequestMapping(value = "{noticeId}",method = RequestMethod.GET)
    public Result NoticeById(@PathVariable String noticeId){
       Notice notice =  noticeService.selectById(noticeId);
        return new Result(true, StatusCode.OK,"根据Id查询消息成功",notice);
    }

//    根据条件分页查询消息通知


//    新增通知 修改通知


//    根据用户id查询该用户的待推送消息（新消息）


//    删除待推送消息（新消息）
}
