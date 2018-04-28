package com.hiold.danmakuserver.rocketmq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.hiold.danmakuserver.bean.Danmaku;
import com.hiold.danmakuserver.utils.SerializeUtil;
import com.hiold.danmakuserver.websocket.SystemWebSocketHandler;

@Component
public class RocketMQListener implements MessageListenerConcurrently {

	@Autowired
	private SystemWebSocketHandler handler;

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

		// System.out.println("get data from rocketMQ:" + msgs);
		for (MessageExt message : msgs) {

			// String msg = new String(message.getBody());
			Danmaku danmaku = (Danmaku) SerializeUtil.deserializeObject(message.getBody());
			System.out.println("收到弹幕:" + danmaku.toString());
			handler.sendMessageToAll(danmaku);
		}

		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	public SystemWebSocketHandler getHandler() {
		return handler;
	}

	public void setHandler(SystemWebSocketHandler handler) {
		this.handler = handler;
	}

}
