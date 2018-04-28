package com.hiold.danmakuserver.websocket;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Created by zhu_kai1 on 2017/2/23.
 */
public class WebsocketHandshakeInterceptor implements HandshakeInterceptor {
	private static Logger logger = LoggerFactory.getLogger(WebsocketHandshakeInterceptor.class);

	public WebsocketHandshakeInterceptor() {
	}

	// 初次握手访问前
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
		logger.debug("握手OK!");
		// if (request instanceof ServletServerHttpRequest) {
		// HttpServletRequest servletRequest = ((ServletServerHttpRequest)
		// request).getServletRequest();
		// // 使用userName区分WebSocketHandler，以便定向发送消息
		// User loginUser = (User)
		// servletRequest.getSession().getAttribute("loginUser");
		// //
		// 存入数据，方便在hander中获取，这里只是在方便在webSocket中存储了数据，并不是在正常的httpSession中存储，想要在平时使用的session中获得这里的数据，需要使用session
		// // 来存储一下
		// if (null != loginUser) {
		// map.put("userName", loginUser.getUserLogin());
		// logger.info("当前的登陆者为：{}", loginUser.getUserLogin());
		// } else {
		// logger.error("没有获取session中的当前登陆者信息");
		// }
		// }

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Exception e) {

	}
}
