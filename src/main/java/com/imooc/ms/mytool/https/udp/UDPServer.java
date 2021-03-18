package com.imooc.ms.mytool.https.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
	public static void main(String[] args) {
		DatagramSocket ds = null;
		
		try {
			ds = new DatagramSocket(5000);  //创建UDP连接
			System.out.println("udp服务已启动！！！");
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf,buf.length); //将数据拆包到buf
			
			ds.receive(dp); //阻塞接收client发过来的数据包
			
			String mes =  new String(buf);
			
			System.out.println(mes);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ds !=null) {
				ds.close(); //关闭
			}
		}
	}
}
