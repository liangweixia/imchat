package com.wayonsys.cn.ichat.config;


import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.corundumstudio.socketio.SocketConfig;




@Configuration
public class SocketIoConfig {

    //存储每一个客户端接入进来的对象
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Bean
    public SocketIOServer socketIOServer()
    {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setPort(8899);
        config.setSocketConfig(new SocketConfig());
        config.setWorkerThreads(100);
        config.setAuthorizationListener(new AuthorizationListener() {
            public boolean isAuthorized(HandshakeData data) {
                return true;
            }
        });
        SocketIOServer server  = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
