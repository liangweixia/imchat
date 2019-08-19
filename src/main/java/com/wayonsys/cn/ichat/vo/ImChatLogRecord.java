package com.wayonsys.cn.ichat.vo;

public class ImChatLogRecord {

    //源客户端id
    private Long fromClientId;
    //目标客户端id
    private Long toClientId;
    //消息类型
    private String msgType;
    //消息内容
    private String msgContent;


    public Long getFromClientId() {
        return fromClientId;
    }

    public void setFromClientId(Long fromClientId) {
        this.fromClientId = fromClientId;
    }

    public Long getToClientId() {
        return toClientId;
    }

    public void setToClientId(Long toClientId) {
        this.toClientId = toClientId;
    }

    public String getMsgType() {
        return msgType;
    }
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public String getMsgContent() {
        return msgContent;
    }
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

}
