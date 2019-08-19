package com.wayonsys.cn.ImChat.contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name="t_clientinfo")
public class ClientInfo {
    @Id
    @NotNull
    private String clientid;
    private Long connected;
    private Long mostsignbits;
    private Long leastsignbits;
    private LocalDateTime lastconnecteddate;
    public String getClientid() {
        return clientid;
    }
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
    public Long getConnected() {
        return connected;
    }
    public void setConnected(Long connected) {
        this.connected = connected;
    }
    public Long getMostsignbits() {
        return mostsignbits;
    }
    public void setMostsignbits(Long mostsignbits) {
        this.mostsignbits = mostsignbits;
    }
    public Long getLeastsignbits() {
        return leastsignbits;
    }
    public void setLeastsignbits(Long leastsignbits) {
        this.leastsignbits = leastsignbits;
    }
    public LocalDateTime getLastconnecteddate() {
        return lastconnecteddate;
    }
    public void setLastconnecteddate(LocalDateTime lastconnecteddate) {
        this.lastconnecteddate = lastconnecteddate;
    }


}
