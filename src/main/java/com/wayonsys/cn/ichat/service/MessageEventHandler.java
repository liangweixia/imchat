package com.wayonsys.cn.ichat.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wayonsys.framework.cache.RedisService;
import com.wayonsys.kefu.ichat.vo.ImChatLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class MessageEventHandler {
    private final SocketIOServer server;
    private  final  static  String PREFIX = "IM";
    @Autowired
    private RedisService redisService;
    @Autowired
    private ImChatLogService service;
    @Autowired
    private ImChatLogQueryService  queryService;
    @Autowired
    private SendMessageService sendMessageService;

    private Map map = new HashMap();
    @Autowired
    public MessageEventHandler(SocketIOServer server)
    {
        this.server = server;
    }
    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    @OnConnect
    public void onConnect(SocketIOClient client)
    {
        //保存redis
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        redisService.set(PREFIX+clientId,client.getSessionId().toString());
    }

    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client)
    {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        redisService.delete(PREFIX+clientId);

    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, ImChatLogForm form)
    {
        Long toClientId = form.getToClientId();
        Long fromClientId = form.getFromClientId();
        //查询fromClient的未读消息,并设置为已读
        service.updateToClientStatus(fromClientId);
        String targetClient = (String)redisService.get(PREFIX+toClientId);
        UUID uuid = UUID.fromString(targetClient);
        //保存数据库，状态为未读
        service.createImChatLog(form);
        if(uuid ==null){
            return;
        }
        //发送消息，更新状态
        sendMessageService.sendMessageToTargetClient(server,uuid,form);
    }


}
