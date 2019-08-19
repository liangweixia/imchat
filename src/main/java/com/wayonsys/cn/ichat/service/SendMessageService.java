package com.wayonsys.cn.ichat.service;


import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.wayonsys.kefu.ichat.vo.ImChatLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class SendMessageService {



    public void sendMessageToTargetClient(final SocketIOServer server, UUID uuid, ImChatLogForm form){
          SocketIOClient client =  server.getClient(uuid);
          client.sendEvent(form.getEventName(),form);
    }

    public void sendMessageToSelfClient(final SocketIOClient client, UUID uuid, ImChatLogForm form){
        client.sendEvent(form.getEventName(),form);
    }
}
