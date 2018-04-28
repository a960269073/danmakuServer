package com.hiold.danmakuserver.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	/**
	 * 序列化对象 ＠throws IOException
	 */
	public static byte[] serializeObject(Object object) {
		ByteArrayOutputStream saos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(saos);
			oos.writeObject(object);
			oos.flush();
			return saos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("序列化失败!");
		}
		return null;
	}

	/**
	 * 反序列化对象 ＠throws IOException ＠throws ClassNotFoundException
	 */
	public static Object deserializeObject(byte[] buf) {
		Object object = null;
		ByteArrayInputStream sais = new ByteArrayInputStream(buf);
		try {
			ObjectInputStream ois = new ObjectInputStream(sais);
			object = ois.readObject();
			return object;
		} catch (IOException e) {
			System.err.println("序列化失败!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("序列化失败!");
			e.printStackTrace();
		}
		return null;
	}
}
