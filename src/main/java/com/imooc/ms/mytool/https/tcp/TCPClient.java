package com.imooc.ms.mytool.https.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	public static void main(String[] args) {
		Socket s = null;
		try {
			s = new Socket("127.0.0.1", 5000);// 创建TCP连接
 
			OutputStream os = s.getOutputStream();// 从中获取输出流
 
			os.write("你好".getBytes());// 输出信息
 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (s != null) {// 用完就关闭
					s.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
