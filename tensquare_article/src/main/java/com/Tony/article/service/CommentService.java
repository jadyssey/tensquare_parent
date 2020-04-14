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
        //_id不要动，新建字段c_id用于存放idWorker的唯一id
        String id = idWorker.nextId()+"";
        // comment.set_id(id);
        //初始化点赞数
        comment.setThumbup(0);
        comment.setPublishdate(new Date());
        commentRepository.save(comment);//保存所有字段的完整数据
    }

    public void updateById(Comment comment) {
//        MongoRepository中的save方法：如果主键存在，则执行修改，不存在则新增
        commentRepository.save(comment);
    }

    public void deleteById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> findByArticleId(String articleId) {
        //调用持久层根据文章Id查询即可
        List<Comment> list = commentRepository.findByArticleid(articleId);//返回多个数据用list
        return list;
    }

    public void thumbupByCommentId(String commentId) {

//        根据评论集合评论id查询评论功能
        Comment comment = commentRepository.findById(commentId).get();  //类似i++不保证线程安全，可能导致脏读
//        该评论的点赞数据+1
        comment.setThumbup(comment.getThumbup()+1);
//        保存
        commentRepository.save(comment);




    }
}
