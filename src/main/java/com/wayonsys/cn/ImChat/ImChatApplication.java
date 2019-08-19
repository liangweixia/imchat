package com.wayonsys.cn.ImChat;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class ImChatApplication {


	@Bean
	public SocketIOServer socketIOServer()
	{
		Configuration config = new Configuration();
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
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("50MB"); //KB,MB
		factory.setMaxRequestSize("100MB");
		return factory.createMultipartConfig();
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}
	public static void main(String[] args) {
		SpringApplication.run(ImChatApplication.class, args);
	}
}
