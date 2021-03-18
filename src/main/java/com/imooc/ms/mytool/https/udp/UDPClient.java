package com.imooc.ms.mytool.https.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient {
	
	public static void main(String[] args) {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(); //创建UDP连接
 
			byte[] buf = "你好么".getBytes();
 
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					new InetSocketAddress("127.0.0.1", 5000)); //将数据打好包,并指定数据包 送到哪里
 
			ds.send(dp);//将数据报包发送
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ds.close();//关闭
		}
	}
}
