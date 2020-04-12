package com.Tony.article.controller;

import com.Tony.article.pojo.Article;
import com.Tony.article.service.ArticleService;
import entity.Result;
import entity.StatusCode;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    DELETE/article/{articleId}    根据ID删除文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId){
        articleService.deleteById(articleId);
        return new Result(true,StatusCode.OK,"删除成功");
    }


//    PUT/article/{articleId}    修改文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.PUT)
    public Result UpdateById(@PathVariable String articleId,@RequestBody Article article){ //接收URL的Id和Json数据
        article.setId(articleId);//合并传入的两组数据
        articleService.updateById(article);
        return new Result(true,StatusCode.OK,"修改文章成功");
    }


    // GET请求/article 查询文章全部列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }
    // GET/article/{articleId}    根据ID查询文章
    @RequestMapping(value = "/{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId){ //@PathVariable接收网页传参
        Article article = articleService.findById(articleId);
        return new Result(true,StatusCode.OK,"根据ID查询成功",article);
    }

    // POST/article  增加文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){ //@RequestBody将json格式数据转化为对象
        articleService.addArticle(article);
        return new Result(true,StatusCode.OK,"添加文章成功");
    }

}
