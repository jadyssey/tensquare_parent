package com.Tony.notice.service;

import com.Tony.notice.dao.NoticeDao;
import com.Tony.notice.dao.NoticeFreshDao;
import com.Tony.notice.pojo.Notice;
import com.Tony.notice.pojo.NoticeFresh;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

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


    public Notice selectById(String noticeId) {
       return noticeDao.selectById(noticeId);
    }

    public Page<Notice> selectByPage(Integer page, Integer size, Notice notice) {
        //分页参数
        Page<Notice> pageData = new Page<>(page,size);
//        查询条件
        EntityWrapper<Notice> wrapper =new EntityWrapper<>(notice);

//        执行分页查询
        List<Notice> noticeList = noticeDao.selectPage(pageData, wrapper);

        //设置结果集到page对象中
        pageData.setRecords(noticeList);
        return  pageData;
    }


    public void save(Notice notice) {
//        设置初始值
//        设置状态  0 未读，1 已读
        notice.setState("0");
        notice.setCreatetime(new Date());

//        使用分布式生成器生成ID
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);

//        新的待推送消息入库，新消息提醒
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);
//        待通知用户ID
        noticeFresh.setUserId(notice.getReceiverId());
         noticeFreshDao.insert(noticeFresh);
    }
}
