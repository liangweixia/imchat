package com.wayonsys.cn.ichat.repository;

import com.wayonsys.kefu.ichat.domain.ImChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImChatLogRepository extends JpaRepository<ImChatLog, Long> {

    List<ImChatLog> findByToClientIdAndStatus(Long toClientId, String status);
}
