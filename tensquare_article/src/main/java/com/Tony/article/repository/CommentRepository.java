package com.Tony.article.repository;

import com.Tony.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
public interface CommentRepository extends MongoRepository<Comment,String> {
}
