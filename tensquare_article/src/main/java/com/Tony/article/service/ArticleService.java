package com.Tony.article.service;

import com.Tony.article.dao.ArticleDao;
import com.Tony.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //查询所有
    public List<Article> findAll(){
       return articleDao.selectList(null);
    }
}
