package com.wayonsys.cn.ImChat.Service;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wayonsys.cn.ImChat.repository.ClientInfoRepository;
import com.wayonsys.cn.ImChat.contract.ClientInfo;
import com.wayonsys.cn.ImChat.contract.MessageInfo;
import com.wayonsys.cn.ImChat.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MessageEventHandler {
    private final SocketIOServer server;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    private Map map = new HashMap();
    @Autowired
    public MessageEventHandler(SocketIOServer server)
    {
        this.server = server;
    }
    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    //方便后面发送消息时查找到对应的目标client,
    @OnConnect
    public void onConnect(SocketIOClient client)
    {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        map.put(clientId,client.getSessionId());
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
        if (clientInfo != null)
        {
            LocalDateTime nowTime = DateUtils.getCurrentLocalDateTime();
            clientInfo.setConnected((Long)1l);
            clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
            clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
            System.out.println(clientInfo);
            clientInfoRepository.save(clientInfo);
        }
    }

    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client)
    {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
        if (clientInfo != null)
        {
            clientInfo.setConnected((Long) 0l);
            clientInfo.setMostsignbits(null);
            clientInfo.setLeastsignbits(null);
            clientInfoRepository.save(clientInfo);
        }
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        String targetClientId = data.getTargetClientId();
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(targetClientId);
        if (clientInfo != null && clientInfo.getConnected() != 0)
        {
            UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
            System.out.println(uuid.toString());
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId(data.getSourceClientId());
            sendData.setTargetClientId(data.getTargetClientId());
            sendData.setMsgType("chat");
            sendData.setMsgContent(data.getMsgContent());
            client.sendEvent("messageevent", sendData);
            server.getClient(uuid).sendEvent("messageevent", sendData);
        }

    }


}
