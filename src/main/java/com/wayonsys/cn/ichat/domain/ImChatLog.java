package com.wayonsys.cn.ichat.domain;


import com.wayonsys.common.entity.DomainEntity;
import com.wayonsys.common.utils.DateUtils;
import com.wayonsys.kefu.ichat.enums.StatusEnum;
import com.wayonsys.kefu.ichat.vo.ImChatLogForm;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="im_chat_log")
public class ImChatLog extends DomainEntity {

    private Long toClientId;
    private Long fromClientId;
    private String status;
    private LocalDateTime sendTime;
    private String msgContent;

    public ImChatLog() {
    }

    public ImChatLog(Long toClientId, Long fromClientId, String msgContent) {
        this.toClientId = toClientId;
        this.fromClientId = fromClientId;
        this.status = StatusEnum.UNREAD.getCode();
        this.sendTime = DateUtils.getCurrentLocalDateTime();
        this.msgContent = msgContent;
    }

    public final static ImChatLog createImChatLog(ImChatLogForm form){
        ImChatLog imChatLog = new ImChatLog();
        imChatLog.setFromClientId(form.getFromClientId());
        imChatLog.setToClientId(form.getToClientId());
        imChatLog.setMsgContent(form.getMsgContent());
        imChatLog.setStatus(StatusEnum.UNREAD.getCode());
        imChatLog.setSendTime(DateUtils.getCurrentLocalDateTime());
        return imChatLog;
    }


    public Long getToClientId() {
        return toClientId;
    }

    public void setToClientId(Long toClientId) {
        this.toClientId = toClientId;
    }

    public Long getFromClientId() {
        return fromClientId;
    }

    public void setFromClientId(Long fromClientId) {
        this.fromClientId = fromClientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
