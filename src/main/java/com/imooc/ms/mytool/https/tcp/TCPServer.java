package com.imooc.ms.mytool.https.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5000);// 创建TCP连接
			
			Socket s = ss.accept();// 阻塞接收client
			
			InputStream is = s.getInputStream();// 提取输入流

			byte[] buf = new byte[1024];

			is.read(buf, 0, buf.length); // 将输入流的信息读出到buf

			String mes = new String(buf);
			System.out.println(mes);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ss != null) {
					ss.close();// 用完就关闭
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
