package com.Tony.notice.service;

import com.Tony.notice.dao.NoticeDao;
import com.Tony.notice.dao.NoticeFreshDao;
import com.Tony.notice.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
