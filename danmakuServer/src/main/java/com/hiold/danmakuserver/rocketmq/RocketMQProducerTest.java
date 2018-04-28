package com.hiold.danmakuserver.rocketmq;

import com.alibaba.rocketmq.common.message.Message;

public class RocketMQProducerTest {
	public static void main(String[] args) {

		String mqNameServer = "192.168.181.128:9876";
		String mqTopics = "MQ-MSG-TOPICS-TEST";

		String producerMqGroupName = "PRODUCER-MQ-GROUP";
		RocketMQProducer mqProducer = new RocketMQProducer(mqNameServer, producerMqGroupName, mqTopics);
		mqProducer.init();

		int i = 0;
		while (true) {

			Message message = new Message();
			message.setBody(("I send message to RocketMQ " + ++i).getBytes());
			// mqProducer.send(message);

			try {
				Thread.sleep(1000 * 5L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
