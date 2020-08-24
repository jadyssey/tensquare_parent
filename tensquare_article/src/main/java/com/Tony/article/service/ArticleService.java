package com.Tony.article.service;

import com.Tony.article.client.NoticeClient;
import com.Tony.article.dao.ArticleDao;
import com.Tony.article.pojo.Article;
import com.Tony.article.pojo.Notice;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.IdWorker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private RedisTemplate redisTemplate;//注入Redis
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private NoticeClient noticeClient;


    /**
     *
     * @return
     */

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

        /*
            添加文章时进行消息通知订阅该作者的用户
         */
        //TODO 使用jwt获取当前用户的userid，也就是文章作者的id
        //注解说明：TODO表示需要实现，但目前还未实现的功能.
        String authorId = "3";

        //获取需要通知的读者
        String authorKey = "article_author_" + authorId;
        Set<String> set = redisTemplate.boundSetOps(authorKey).members();

        for(String uid:set){
            Notice notice = new Notice();
            notice.setReceiverId(uid);
            notice.setOperatorId(authorId);
            notice.setAction("publish");
            notice.setTargetType("article");
            notice.setTargetId(id);
            notice.setCreatetime(new Date());
            notice.setType("sys");
            notice.setState("0");

            noticeClient.add(notice);
        }
        //入库成功后，发送mq消息，内容是消息通知id
        rabbitTemplate.convertAndSend("article_subscribe", authorId, id);

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

        //selectPage方法执行查询,第一个是分页参数，第二个是查询条件:得到结果集
         List<Article> list = articleDao.selectPage(pageData,wrapper);

         //将结果集放入Page对象的实例中返回
         pageData.setRecords(list);

        return pageData;
    }

    /**
     * 功能：根据文章ID，订阅文章作者
     * 定义Rabbitmq的direct类型的交换机
     * 定义用户的Rabbitmq队列
     * 将队列通过路由键绑定或解绑direct交换机
     * @param userId
     * @param articleId
     * @return
     */
    public Boolean subscribe(String userId, String articleId) {
        //根据文章ID查询文章作者ID
        String authorId = articleDao.selectById(articleId).getUserid();

        //创建Rabbit管理器
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());

        //声明exchange
        DirectExchange exchange = new DirectExchange("article_subscribe");
        rabbitAdmin.declareExchange(exchange);

        //创建queue：import org.springframework.amqp.core.Queue;
        //一个用户一个队列，存放消息通知，防止消息错发； true为持久化存储
        Queue queue = new Queue("article_subscribe_"+userId,true);

        /*
        声明exchange和queue的绑定关系，需要确保队列只收到对应作者的新增文章信息
        设置路由键为作者Id进行绑定，队列只收到绑定作者的文章信息
        第一个是队列，第二个是交换机，第三个是路由键作者Id
        */
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(authorId);

        //存放用户订阅作者
        String userKey = "article_subscribe_"+userId;
        //存放作者的订阅者
        String authorKey = "article_author_"+authorId;

        //通过isMember(K key, Object o)方法检查给定的元素是否在变量中
        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(authorId);
        //userKey和authorKey分别为用户集合和作者集合，存放的authorId和userId为Key，防止重复订阅
        if(flag){
            //如何flag为true，已经订阅，则取消
            redisTemplate.boundSetOps(userKey).remove(authorId);
            redisTemplate.boundSetOps(authorKey).remove(userId);

            //删除绑定的队列
            rabbitAdmin.removeBinding(binding);
            return false;
        }else{
            //如何flag为false，没有订阅，则进行订阅
            redisTemplate.boundSetOps(userKey).add(authorId);
            redisTemplate.boundSetOps(authorKey).add(userId);

            //声明队列和绑定队列
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);

            return true;
        }

    }
//    未配置RabbitMq前的方法
/*    public Boolean subscribe(String userId, String articleId) {
        //根据文章ID查询文章作者ID
        String authorId = articleDao.selectById(articleId).getUserid();
        //将订阅设置为redis中的集合
        String userKey = "article_subscribe_"+userId;
        String authorKey = "article_author_"+authorId;

        //通过isMember(K key, Object o)方法检查给定的元素是否在变量中
        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(authorId);
        //userKey和authorKey分别为用户集合和作者集合，存放的authorId和userId为Key，防止重复订阅
        if(flag){
            //如何flag为true，已经订阅，则取消
            redisTemplate.boundSetOps(userKey).remove(authorId);
            redisTemplate.boundSetOps(authorKey).remove(userId);
            return false;
        }else{
            //如何flag为false，没有订阅，则进行订阅
            redisTemplate.boundSetOps(userKey).add(authorId);
            redisTemplate.boundSetOps(authorKey).add(userId);
            return true;
        }

    }*/

    public void thumbup(String articleId,String userid) {
        Article article = articleDao.selectById(articleId);
        article.setThumbup(article.getThumbup()+1);
        articleDao.updateById(article);

        //实现点赞消息通知
        Notice notice = new Notice(
                //利用构造函数简化传参代码
                article.getUserid(),
                userid,
                "thumup",
                "article",
                articleId,new Date(),
                "user",
                "0"
        );

        noticeClient.add(notice);
    }
}
