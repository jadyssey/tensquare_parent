package com.Tony.article.controller;

import com.Tony.article.pojo.Comment;
import com.Tony.article.repository.CommentRepository;
import com.Tony.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // GET /comment/{commentId}  根据评论Id查询评论数据
    @RequestMapping(value = "{commentId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String commentId){
        Comment comment = commentService.findById(commentId);//单个数据用Comment接收
        return new Result(true,StatusCode.OK,"根据评论Id查询评论成功",comment);
    }

    // GET /comment 查询所有评论
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Comment> list  = commentService.findAll();//多个数据用List接收
        return new Result(true, StatusCode.OK,"查询所有成功",list);
    }
}
