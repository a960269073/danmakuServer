package com.hiold.danmakuserver.bean;

import java.io.Serializable;
import java.util.Date;

public class Danmaku implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String danmaku;
	private Date lunchTime;

	public String getDanmaku() {
		return danmaku;
	}

	public void setDanmaku(String danmaku) {
		this.danmaku = danmaku;
	}

	public Date getLunchTime() {
		return lunchTime;
	}

	public void setLunchTime(Date lunchTime) {
		this.lunchTime = lunchTime;
	}

	@Override
	public String toString() {
		return "Danmaku [danmaku=" + danmaku + ", lunchTime=" + lunchTime + "]";
	}

}
