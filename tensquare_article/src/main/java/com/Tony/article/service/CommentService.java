package com.Tony.article.service;

import com.Tony.article.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
}
