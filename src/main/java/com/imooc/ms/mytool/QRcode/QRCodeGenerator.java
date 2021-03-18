package com.imooc.ms.mytool.QRcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {
	
	private static final String QR_CODE_IMAGE_PATH = "C:/Users/Administrator/Desktop/code.png";
	
	/*
	 * 方法是将二维码保存为图片，
	 */
	private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		
		Path path = FileSystems.getDefault().getPath(filePath);
		
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		
	}
	
	/*
	 * 不想将二维码保存为图片，也可以将其保存为字节数组，可以用zxing 库提供的MatrixToImageWriter.writeToStream()方法
	 */
	
	  public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
	        
	        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
	        byte[] pngData = pngOutputStream.toByteArray(); 
	        return pngData;
	  }
	
	public static void main(String[] args) {
        try {
            generateQRCodeImage("This is my first QR Code,yangduanduan", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
	}
        /** 
 	    * Java拼接多张图片 
 	    *  
 	    * @param imgs 图片地址集合
 	    * @param type 图片类型
 	    * @param dst_pic //输出的文件：F:/test2.jpg
 	    * @return 
 	    */  
 	   public static boolean merge(String[] imgs, String type,String dst_pic){  
 	    //获取需要拼接的图片长度
 	       int len = imgs.length;  
 	       //判断长度是否大于0
 	       if (len < 1) {  
 	           return false;  
 	       }  
 	       File[] src = new File[len];  
 	       BufferedImage[] images = new BufferedImage[len];  
 	       int[][] ImageArrays = new int[len][];  
 	       for (int i = 0; i < len; i++) {  
 	           try {  
 	               src[i] = new File(imgs[i]);  
 	               images[i] = ImageIO.read(src[i]);  
 	           } catch (Exception e) {  
 	               e.printStackTrace();  
 	               return false;  
 	           }  
 	           int width = images[i].getWidth();  
 	           int height = images[i].getHeight();  
 	        // 从图片中读取RGB 像素
 	           ImageArrays[i] = new int[width * height];
 	           ImageArrays[i] = images[i].getRGB(0, 0, width, height,  ImageArrays[i], 0, width);  
 	       }  
 	 
 	       int dst_height = 0;  
 	       int dst_width = images[0].getWidth();  
 	     //合成图片像素
 	       for (int i = 0; i < images.length; i++) {  
 	           dst_width = dst_width > images[i].getWidth() ? dst_width     : images[i].getWidth();  
 	           dst_height += images[i].getHeight();  
 	       }  
 	       //合成后的图片
 	       System.out.println("宽度:"+dst_width);  
 	       System.out.println("高度:"+dst_height);  
 	       if (dst_height < 1) {  
 	           System.out.println("dst_height < 1");  
 	           return false;  
 	       }  
 	       // 生成新图片   
 	       try {  
 	           dst_width = images[0].getWidth();   
 	           BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,  
 	                   BufferedImage.TYPE_INT_RGB);  
 	           int height_i = 0;  
 	           for (int i = 0; i < images.length; i++) {  
 	               ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(),  
 	                       ImageArrays[i], 0, dst_width);  
 	               height_i += images[i].getHeight();  
 	           }  
 	 
 	           File outFile = new File(dst_pic);  
 	           ImageIO.write(ImageNew, type, outFile);// 写图片 ，输出到硬盘 
 	       } catch (Exception e) {  
 	           e.printStackTrace();  
 	           return false;  
 	       }  
 	       return true;  
 	   }  

		
}
