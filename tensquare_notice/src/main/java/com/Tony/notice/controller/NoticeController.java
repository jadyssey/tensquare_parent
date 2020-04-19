package com.Tony.notice.controller;

import com.Tony.notice.pojo.Notice;
import com.Tony.notice.service.NoticeService;
import com.baomidou.mybatisplus.plugins.Page;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.aspectj.weaver.ast.Not;
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
//    http://127.0.0.1:9014/notice/search/{page}/{size}   POST
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result search(@PathVariable Integer page,
                         @PathVariable Integer size,
                         @RequestBody Notice notice){
//        使用 Mybatis Plus 提供的Page对象
        Page<Notice> pageData = noticeService.SelectByPage(page,size,notice);

//      使用分页结果集显示查询数据
        PageResult<Notice> pageResult = new PageResult<>(pageData.getTotal(),pageData.getRecords());
        return new Result(true,StatusCode.OK,"条件分页查询成功",pageResult);
    }

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
