package com.wayonsys.cn.ichat.service;


import com.wayonsys.common.exception.ServiceException;
import com.wayonsys.kefu.ichat.domain.ImChatLog;
import com.wayonsys.kefu.ichat.enums.StatusEnum;
import com.wayonsys.kefu.ichat.repository.ImChatLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImChatLogQueryService {

    @Autowired
    private ImChatLogRepository imChatLogRepository;

    //查询未读消息
    public List<ImChatLog> findByToClient(Long toClient){
        if(toClient==null){
            throw  new ServiceException("传入的id为空!");
        }
        List<ImChatLog> imChatLogs = imChatLogRepository.findByToClientIdAndStatus(toClient, StatusEnum.UNREAD.getCode());
        return imChatLogs;
    }
}
