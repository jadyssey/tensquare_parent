package com.Tony.article.service;

import com.Tony.article.dao.ArticleDao;
import com.Tony.article.pojo.Article;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

        //初始化部分数据，否则Null无法增加；最好在建表语句中设置DEFAULT 0 ，建表语句中该字段默认为0则无需初始化;
        article.setVisits(0);//浏览量
        article.setComment(0);//评论数
        article.setThumbup(0);//点赞数

        //新增
        articleDao.insert(article);
    }

    //根据Id修改文章
     public void updateById(Article article) {
        //第一种方法：根据主键ID修改
        articleDao.updateById(article);

        //根据任意条件修改: 此处根据id
         EntityWrapper<Article> wrapper = new EntityWrapper<>();//创建条件对象，因为要调用里面的方法
         wrapper.eq("id",article.getId());//设置根据article中的id来更新表中的id字段
         articleDao.update(article,wrapper);
    }
//根据Id删除文章
    public void deleteById(String articleId) {
        articleDao.deleteById(articleId);
    }

    //根据条件分页查询
    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        //设置条件查询
        EntityWrapper<Article> wrapper =new EntityWrapper<>();
        //遍历map拿到key
        Set<String> keySet= map.keySet();
        for (String key: keySet){
            //此if与下方语句效果相同
//            if(map.get(key)!= null){   wrapper.eq(key,map.get(key));   }
            //第一个参数是Boolean型，判断是否把后面的条件加入到查询条件中，实现动态SQL
            wrapper.eq(null != map.get(key),key,map.get(key));
        }

        //设置分页参数,使用 Mybatis Plus 提供的Page对象
        Page<Article> pageData = new Page<>(page,size);

        //执行查询,第一个是分页参数，第二个是查询条件:得到结果集
         List<Article> list = articleDao.selectPage(pageData,wrapper);

         //将结果集放入pageData
         pageData.setRecords(list);

        return pageData;
    }
}
