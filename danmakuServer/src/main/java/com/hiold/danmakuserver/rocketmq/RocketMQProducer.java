package com.hiold.danmakuserver.rocketmq;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.hiold.danmakuserver.bean.Danmaku;
import com.hiold.danmakuserver.utils.SerializeUtil;

@Component
public class RocketMQProducer {

	private DefaultMQProducer sender;

	@Value("${rocketMQ.server}")
	private String nameServer;

	@Value("${rocketMQ.group}")
	private String groupName;

	@Value("${rocketMQ.topic}")
	private String topics;

	@PostConstruct
	public void init() {
		sender = new DefaultMQProducer(groupName);
		sender.setNamesrvAddr(nameServer);
		sender.setInstanceName(UUID.randomUUID().toString());
		try {
			sender.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		System.out.println("RocketMQ Producer服务初始化完成! group=" + sender.getProducerGroup() + " instance="
				+ sender.getInstanceName());
	}

	public RocketMQProducer(String nameServer, String groupName, String topics) {
		this.nameServer = nameServer;
		this.groupName = groupName;
		this.topics = topics;
	}

	public void send(Danmaku danmaku) {
		Message message = new Message();
		message.setTopic(topics);
		message.setBody(SerializeUtil.serializeObject(danmaku));
		System.out.println("组合弹幕:" + danmaku.toString());
		try {
			SendResult result = sender.send(message);
			SendStatus status = result.getSendStatus();
			System.out.println("messageId=" + result.getMsgId() + ", status=" + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RocketMQProducer() {
	}
}
