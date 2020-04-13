package com.Tony.article.service;

import com.Tony.article.pojo.Comment;
import com.Tony.article.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //查询所有id
    public List<Comment> findAll(){
        List<Comment> list = commentRepository.findAll();
        return list;
    }

    //根据评论Id查评论
    public Comment findById(String commentId) {
        return  commentRepository.findById(commentId).get();  //get方法

    }
 }
