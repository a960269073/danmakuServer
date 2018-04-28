package com.hiold.danmakuserver.rocketmq;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

@Component
public class RocketMQConsumer {
	private DefaultMQPushConsumer consumer;

	@Autowired
	private MessageListener listener;

	@Value("${rocketMQ.server}")
	private String nameServer;

	@Value("${rocketMQ.group}")
	private String groupName;

	@Value("${rocketMQ.topic}")
	private String topics;

	public RocketMQConsumer(MessageListener listener, String nameServer, String groupName, String topics) {
		this.listener = listener;
		this.nameServer = nameServer;
		this.groupName = groupName;
		this.topics = topics;
	}

	public RocketMQConsumer() {
	}

	@PostConstruct
	public void init() {
		consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(nameServer);
		try {
			consumer.subscribe(topics, "*");
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		consumer.setInstanceName(UUID.randomUUID().toString());
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		consumer.registerMessageListener((MessageListenerConcurrently) this.listener);

		try {
			consumer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		System.out.println("RocketMQ Consumer服务初始化完成! group=" + consumer.getConsumerGroup() + " instance="
				+ consumer.getInstanceName());
	}

	public DefaultMQPushConsumer getConsumer() {
		return consumer;
	}

	public void setConsumer(DefaultMQPushConsumer consumer) {
		this.consumer = consumer;
	}

	public MessageListener getListener() {
		return listener;
	}

	public void setListener(MessageListener listener) {
		this.listener = listener;
	}

	public String getNameServer() {
		return nameServer;
	}

	public void setNameServer(String nameServer) {
		this.nameServer = nameServer;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

}
