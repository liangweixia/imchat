package com.wayonsys.cn.ichat.service;


import com.wayonsys.kefu.ichat.domain.ImChatLog;
import com.wayonsys.kefu.ichat.enums.StatusEnum;
import com.wayonsys.kefu.ichat.repository.ImChatLogRepository;
import com.wayonsys.kefu.ichat.vo.ImChatLogForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImChatLogService {

    private static final Logger log = LoggerFactory.getLogger(ImChatLogService.class);

    @Autowired
    private ImChatLogRepository repository;
    @Autowired
    private ImChatLogQueryService queryService;


    public Long createImChatLog(ImChatLogForm form){
        log.info("method:{} form:{}","createImChatLog",form.toString());
        ImChatLog imChatLog = ImChatLog.createImChatLog(form);
        Long id = repository.save(imChatLog).getId();
        return id;
    }

    /**
     * 更新消息状态
     * @param form
     * @return
     */
    public void updateStatus(ImChatLogForm form){
        log.info("method:{} form:{}","createImChatLog",form.toString());
    }

    /**
     * 更新未读消息为已读
     */
    public void updateToClientStatus(Long toClientId){
        List<ImChatLog> imChatLogs =  queryService.findByToClient(toClientId);
        for(ImChatLog imChatLog: imChatLogs ){
            imChatLog.setStatus(StatusEnum.READ.getCode());
        }
        repository.saveAll(imChatLogs);
    }
}
