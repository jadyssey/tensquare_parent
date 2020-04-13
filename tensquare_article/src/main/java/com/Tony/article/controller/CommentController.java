package com.Tony.article.controller;

import com.Tony.article.pojo.Comment;
import com.Tony.article.repository.CommentRepository;
import com.Tony.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //DELETE /comment/{commentId} 根据Id删除评论
    @RequestMapping(value = "{commentId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String commentId){
        commentService.deleteById(commentId);
        return new Result(true,StatusCode.OK,"删除评论成功");
    }

    //PUT /comment/{commentId}  修改评论
    @RequestMapping(value = "{commentId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String commentId,
                             @RequestBody Comment comment){
        comment.set_id(commentId);
        commentService.updateById(comment);
        return new Result(true,StatusCode.OK,"修改评论成功");

    }

    //Post /comment   新增评论
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true,StatusCode.OK,"新增评论成功");

    }

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
