package com.Tony.article.service;

import com.Tony.article.pojo.Comment;
import com.Tony.article.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IdWorker idWorker;
    //查询所有id
    public List<Comment> findAll(){
        List<Comment> list = commentRepository.findAll();
        return list;
    }

    //根据评论Id查评论
    public Comment findById(String commentId) {
        /*   使用该方法可以防止value == null抛出 NoSuchElementException异常
        Optional<Comment> optional = commentRepository.findById(commentId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
        */
        return  commentRepository.findById(commentId).get();  //如果评论被删除了，会报异常

    }

    //新增评论
    public void save(Comment comment) {
        //分布式Id生成器
        String id = idWorker.nextId()+"";
        comment.set_id(id);
        //初始化点赞数
        comment.setThumbup(0);
        comment.setPublishdate(new Date());
        commentRepository.save(comment);//到这一步comment一定要拥有完整的所有字段
    }
}
