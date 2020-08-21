package com.Tony.notice.service;

import com.Tony.notice.client.ArticleClient;
import com.Tony.notice.client.UserClient;
import com.Tony.notice.dao.NoticeDao;
import com.Tony.notice.dao.NoticeFreshDao;
import com.Tony.notice.pojo.Notice;
import com.Tony.notice.pojo.NoticeFresh;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@Service
public class NoticeService {

    @Autowired
    private  NoticeDao noticeDao;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 接口注入完善消息内容
     */
    @Autowired
    private ArticleClient articleClient;
    @Autowired
    private UserClient userClient;


    /**
     * 根据用户ID和文章ID查询用户昵称和文章标题
     */
    private void getNoticeInfo(Notice notice){
        //    获得用户信息
        Result userResult = userClient.findById(notice.getOperatorId());
        HashMap userMap = (HashMap)userResult.getData();

        //遍历HashMap用于调试
        //for (Object key:userMap.keySet()) {
        //    System.out.println("key="+key +" ，Value="+userMap.get(key));
        //}

        //将得到的用户昵称设置到notice实体类中
        notice.setOperatorName(userMap.get("nickname").toString());

        //   如果TargetType为文章类型，则通过TargetId去匹配文章Id，以获取文章标题
        if("article".equals(notice.getTargetType())){
            Result articleResult = articleClient.findById(notice.getTargetId());
            HashMap articleMap = (HashMap)articleResult.getData();
            notice.setTargetName(articleMap.get("title").toString());
        }
    }

    //普通查询
    //public Notice selectById(String noticeId) {
    //   return noticeDao.selectById(noticeId);
    //}
    /**
     * 根据ID查询实体，能将用户ID和文章ID转换成可阅读的用户昵称和文章标题
     * @param id
     * @return
     */
    public Notice selectById(String id) {
        Notice notice = noticeDao.selectById(id);
        getNoticeInfo(notice);
       return notice;
    }

    /**
     * 条件查询
     * @param page
     * @param size
     * @param notice
     * @return
     */
    public Page<Notice> selectByPage(Integer page, Integer size, Notice notice) {
        //分页参数
        Page<Notice> pageData = new Page<>(page,size);
        //查询条件
        EntityWrapper<Notice> wrapper =new EntityWrapper<>(notice);
        //执行分页查询
        List<Notice> noticeList = noticeDao.selectPage(pageData, wrapper);

        //用户ID和文章ID转换为可阅读的用户昵称和文章标题
        for (Notice n:
                noticeList) {
            getNoticeInfo(n);
        }

        //设置结果集到page对象中
        pageData.setRecords(noticeList);
        return  pageData;
    }


    public void save(Notice notice) {
        //设置初始值
        //设置状态  0 未读，1 已读
        notice.setState("0");
        notice.setCreatetime(new Date());
        //使用分布式生成器生成ID
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);

        //新的待推送消息入库，新消息提醒
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);
        //待通知用户ID
        noticeFresh.setUserId(notice.getReceiverId());
        noticeFreshDao.insert(noticeFresh);
    }

    public void updateById(Notice notice) {
        noticeDao.updateById(notice);
    }

    public Page<NoticeFresh> freshPage(String userId, Integer page, Integer size) {
        //封装查询条件
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setUserId(userId);

        //创建分页对象
        Page<NoticeFresh> pageData = new Page<>(page,size);

        //执行查询
        List<NoticeFresh> list = noticeFreshDao.selectPage(pageData, new EntityWrapper<>(noticeFresh));
        //设置查询结果集到分页对象中
        pageData.setRecords(list);
        //返回结果
        return pageData;
    }

    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshDao.delete(new EntityWrapper<>(noticeFresh));
    }



}
