package com.Tony.article.controller;

import com.Tony.article.pojo.Article;
import com.Tony.article.service.ArticleService;
import com.baomidou.mybatisplus.plugins.Page;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 *
 * @CrossOrigin 使当前类中的方法支持跨域
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 测试统一异常处理,造一个异常
     * @return
     */
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public Result testError() {
        int a = 1 / 0;
        return null;
    }
/*     @RequestMapping(value = "/exception",method = RequestMethod.GET)
    public Result exception() throws Exception{
        throw new Exception("测试统一异常处理");
    }
*/
    /**
     * 文章分页
     * POST /article/search/{page}/{size}
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findByPage(@PathVariable Integer page,//当前页数
                             @PathVariable Integer size,//每页显示的结果集个数
                             @RequestBody Map<String, Object> map) { //使用集合的方式遍历,Key存字段名字，value存数据

        //根据条件分页查询,使用 Mybatis Plus 提供的Page对象
        Page<Article> pageData = articleService.findByPage(map, page, size);
        //封装分页返回对象
        PageResult<Article> pageResult = new PageResult<>(
                pageData.getTotal(), pageData.getRecords()  //总记录数和当前页的数据结果集
        );
        return new Result(true, StatusCode.OK, "分页查询成功", pageResult);
    }

    /**
     * 根据ID删除文章
     * DELETE/article/{articleId}
     * @param articleId
     * @return
     */
    @RequestMapping(value = "{articleId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId) {
        articleService.deleteById(articleId);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /**
     *  修改文章
     *  PUT/article/{articleId}
     * @param articleId
     * @param article
     * @return
     */
    @RequestMapping(value = "{articleId}", method = RequestMethod.PUT)
    public Result updateById(@PathVariable String articleId, @RequestBody Article article) { //接收URL的Id和Json数据
        //合并传入的两组数据
        article.setId(articleId);
        articleService.updateById(article);
        return new Result(true, StatusCode.OK, "修改文章成功");
    }


    /**
     * 查询文章全部列表
     * GET请求/article
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        //多个数据用list接收
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * GET/article/{articleId}
     * 根据ID查询文章
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId) { //@PathVariable接收网页传参
        //单个数据用Article接收
        Article article = articleService.findById(articleId);
        return new Result(true, StatusCode.OK, "根据ID查询成功", article);
    }

    /**
     * POST/article
     * 增加文章
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) { //@RequestBody将json格式数据转化为对象
        articleService.addArticle(article);
        return new Result(true, StatusCode.OK, "添加文章成功");
    }

    /**
     * 订阅或者取消订阅文章作者
     * @param map
     * 测试数据
     * {
     * 	"userId":"1",
     * 	"articleId":"2"
     * }
     * @return
     */
    @PostMapping("/subscribe")
    private Result subscribe(@RequestBody Map map){
        //根据文章ID，订阅文章作者，返回订阅状态，true表示订阅成功，false表示取消订阅成功
        Boolean flag = articleService.subscribe(map.get("userId").toString(),map.get("articleId").toString());

        if(flag){
            return new Result(true,StatusCode.OK,"订阅成功");
        }else{
            return new Result(true,StatusCode.OK,"订阅取消");
        }
    }
}
