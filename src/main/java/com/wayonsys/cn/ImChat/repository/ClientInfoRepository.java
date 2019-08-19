package com.wayonsys.cn.ImChat.repository;

import com.wayonsys.cn.ImChat.contract.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {
    ClientInfo findClientByclientid(String clientId);
}