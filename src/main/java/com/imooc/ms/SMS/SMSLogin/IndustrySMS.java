package com.imooc.ms.SMS.SMSLogin;

import java.net.URLEncoder;
import java.util.Random;

import org.springframework.boot.SpringApplication;

import com.imooc.ms.App;

public class IndustrySMS {
	private static String operation = "/distributor/sendSMS";

	private static String accountSid = SMSLoginConfig.ACCOUNT_SID;
	private static String to = "17780068027";
	private static String smsContent = "【成都菁软信息技术有限公司】登录验证码：{" + runNumber().toString()
			+ "}，如非本人操作，请忽略此短信。";

	/**
	 * 
	 * @author yangdd
	 * @return 生成4位数验证码
	 */
	public static String runNumber() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		System.out.println(sb.toString());
		String code = sb.toString();
		return code;
	}

	/**
	 * 验证码通知短信
	 */
	public static void execute() {
		String tmpSmsContent = null;
		try {
			tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
		} catch (Exception e) {
		}
		String url = SMSLoginConfig.BASE_URL + operation;
		String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
				+ HttpUtil.createCommonParam();

		// 提交请求
		String result = HttpUtil.post(url, body);
		System.out.println("result:" + System.lineSeparator() + result);
	}

	public static void main(String[] args) {
		execute();
	}

}