package com.hiold.danmakuserver.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiold.danmakuserver.bean.Danmaku;
import com.hiold.danmakuserver.rocketmq.RocketMQProducer;

@RestController
public class DanmakuController {

	@Autowired
	private RocketMQProducer danmakuServer;

	@RequestMapping("/danmaku")
	public String danmaku(String danmaku) {
		System.out.println(danmaku);

		Danmaku danmakuObj = new Danmaku();
		danmakuObj.setDanmaku(danmaku);
		danmakuObj.setLunchTime(new Date());

		danmakuServer.send(danmakuObj);

		return "OK!";
	}

	public RocketMQProducer getDanmakuServer() {
		return danmakuServer;
	}

	public void setDanmakuServer(RocketMQProducer danmakuServer) {
		this.danmakuServer = danmakuServer;
	}

}
