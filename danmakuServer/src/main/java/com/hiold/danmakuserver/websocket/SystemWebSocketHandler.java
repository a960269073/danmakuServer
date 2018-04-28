package com.hiold.danmakuserver.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONArray;
import com.hiold.danmakuserver.bean.Danmaku;

/**
 * Created by zhu_kai1 on 2017/2/23.
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

	@Autowired
	public static final String USERNAME = "userName";
	private static Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	protected static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());

	public SystemWebSocketHandler() {
	}

	// 连接建立后处理
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		logger.info("webSocket连接已建立");
		sessions.add(webSocketSession);
		// String userLogin = (String)
		// webSocketSession.getAttributes().get(USERNAME);
		// BaseRestPaginationResult<NoticeResult> result = null;
		// if(StringUtils.isNotEmpty(userLogin)){
		// result = commonService.getNoticeInfo(userLogin);
		// }
		// if(null !=result){
		// sendMessageToAll(new
		// TextMessage(StringUtils.toString(result.getTotal())));
		// }
	}

	// 接收客户端消息，并发送出去
	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage)
			throws Exception {
		logger.info("发送消息" + webSocketMessage.toString());
	}

	// 抛出异常时处理
	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		sessions.remove(webSocketSession);
		logger.info("webSocket异常处理" + throwable.getMessage());
		// throw new SystemException(throwable);
	}

	// 连接关闭后处理
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		logger.info("webSocket连接已关闭......" + closeStatus.getReason());
		sessions.remove(webSocketSession);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 */
	public void sendMessageToAll(Danmaku danmaku) {
		WebSocketMessage<String> message = new TextMessage(JSONArray.toJSONString(danmaku));
		for (WebSocketSession session : sessions) {
			try {
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				// throw new SystemException(e.getMessage());
			}
		}
	}

}
