package com.Tony.article.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AntonTony
 * @version 1.0
 * @GitHub https://github.com/AntonTony
 */
@TableName("tb_notice")
public class Notice implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 接收消息的用户ID
     */
    private String receiverId;

    /**
     * 进行操作的用户ID
     */
    private String operatorId;
    /**
     * 进行操作的用户昵称
     */
    @TableField(exist = false)
    private String operatorName;

    /**
     * 操作类型（评论，点赞等）
     */
    private String action;
    /**
     * 对象类型（评论，点赞等）
     */
    private String targetType;

    /**
     * 被操作对象名称或简介
     */
    @TableField(exist = false)
    private String targetName;
    /**
     * 被操作对象的id，例如文章的id，评论的id
     */
    private String targetId;

    /**
     * 创建日期
     */
    private Date createtime;
    /**
     * 消息类型 (sys系统消息  user用户消息)
     */
    private String type;
    /**
     * 消息状态（0 未读，1 已读）
     */
    private String state;

    //构造函数
    public Notice() {
    }

    public Notice(String receiverId, String operatorId,
                  String action, String targetType, String targetId,
                  Date createtime, String type, String state) {
        this.receiverId = receiverId;
        this.operatorId = operatorId;
        this.action = action;
        this.targetType = targetType;
        this.targetId = targetId;
        this.createtime = createtime;
        this.type = type;
        this.state = state;
    }

    //geter seter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}