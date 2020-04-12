package com.Tony.article.service;

import com.Tony.article.dao.ArticleDao;
import com.Tony.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    //查询所有文章
    public List<Article> findAll(){
       return articleDao.selectList(null);

    }

    //根据Id查询文章
    public Article findById(String articleId){
        return articleDao.selectById(articleId);
        //selectById括号传序列化接口的参数,因为String天生实现了序列化接口所以直接用
    }

    //使用雪花算法生成器生成ID
    @Autowired
    private IdWorker idWorker;
    //添加文章,不需要返回值
    public void addArticle(Article article){
        //使用分布式Id生成器
        String id = idWorker.nextId()+"";//因为idWorker生成的是long型的，而articleId是String型，所以要转换成String型
        article.setId(id);

        //初始化部分数据；不设置的话为Null，无法增加
        article.setVisits(0);//浏览量
        article.setComment(0);//评论数
        article.setThumbup(0);//点赞数

        //新增
        articleDao.insert(article);
    }
}
