package com.Tony.article.repository;

import com.Tony.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
public interface CommentRepository extends MongoRepository<Comment,String> { //id类型是String

    //springDataMongoDB，支持通过查询方法名进行查询定义方式;List<Comment> findby方法
    //例一：根据发布时间和点赞数查询
    List<Comment> findByPublishdateAndThumbup(Date date,Integer thumbup);
    //例二：根据用户ID查询，并且根据发布时间倒序排序Desc
    List<Comment> findByUseridOrderByPublishdateDesc(String userId);

    //根据文章id查询评论
    List<Comment> findByArticleid(String articleId);


}
