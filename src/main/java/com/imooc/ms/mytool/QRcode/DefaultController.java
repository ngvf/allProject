package com.imooc.ms.mytool.QRcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;

@Controller
public class DefaultController {
	@GetMapping("qrcode")
	@ResponseBody
    public String qrcode() {
    	return "/qrcode";
    }
    
    @GetMapping(value="/qrimage")
	public List<ResponseEntity<byte[]>> getQRImage() {
    	List list=new ArrayList<ResponseEntity>();
		for(int i=0;i<10;i++) {
		//二维码内的信息
		String info = "This is my first QR Code,yangdd";
		
		byte[] qrcode = null;
		try {
			qrcode = QRCodeGenerator.getQRCodeImage(info, 360, 360);
		} catch (WriterException e) {
			System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
		} 
 
	    // Set headers
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
 
	    list.add(new ResponseEntity<byte[]> (qrcode, headers, HttpStatus.CREATED));
		}
		return list;
	}
}
