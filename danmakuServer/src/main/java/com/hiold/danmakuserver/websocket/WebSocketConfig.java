package com.hiold.danmakuserver.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Created by zhu_kai1 on 2017/2/23.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	// webSocket
	private static final String WEBSOCKET_SERVER = "/webSocketServer";
	private static final String ECHO = "/echo";
	// 不支持webSocket的话用sockjs
	private static final String SOCKJS = "/sockjs/webSocketServer";

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 支持websocket 的访问链接
		registry.addHandler(systemWebSocketHandler(), WEBSOCKET_SERVER).addInterceptors(handshakeInterceptor());
		registry.addHandler(systemWebSocketHandler(), ECHO).addInterceptors(handshakeInterceptor());
		// 不支持websocket的访问链接
		registry.addHandler(systemWebSocketHandler(), SOCKJS).addInterceptors(handshakeInterceptor()).withSockJS();
	}

	@Bean
	public WebSocketHandler systemWebSocketHandler() {
		return new SystemWebSocketHandler();
	}

	@Bean
	public HandshakeInterceptor handshakeInterceptor() {
		return new WebsocketHandshakeInterceptor();
	}

	// Allow serving HTML files through the default Servlet

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
