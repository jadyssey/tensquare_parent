package com.Tony.notice.service;

import com.Tony.notice.dao.NoticeDao;
import com.Tony.notice.dao.NoticeFreshDao;
import com.Tony.notice.pojo.Notice;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Notice selectById(String noticeId) {
       return noticeDao.selectById(noticeId);
    }

    public Page<Notice> SelectByPage(Integer page, Integer size, Notice notice) {
        //分页参数
        Page<Notice> pageData = new Page<>(page,size);
//        查询条件
        EntityWrapper<Notice> wrapper =new EntityWrapper<>(notice);

        List<Notice> noticeList = noticeDao.selectPage(pageData, wrapper);

        //设置结果集到page对象中
        pageData.setRecords(noticeList);
        return  pageData;
    }
}
