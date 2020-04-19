package com.Tony.notice.pojo;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("tb_notice_fresh")
public class NoticeFresh {  //待推送消息表

    private String userId;
    private String noticeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}