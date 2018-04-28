package com.hiold.danmakuserver.rocketmq;

public class RocketMQConsumerTest {
	public static void main(String[] args) {

		String mqNameServer = "192.168.181.128:9876";
		String mqTopics = "MQ-MSG-TOPICS-TEST";

		String consumerMqGroupName = "CONSUMER-MQ-GROUP";
		RocketMQListener mqListener = new RocketMQListener();
		RocketMQConsumer mqConsumer = new RocketMQConsumer(mqListener, mqNameServer, consumerMqGroupName, mqTopics);
		mqConsumer.init();

		try {
			Thread.sleep(1000 * 6L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
