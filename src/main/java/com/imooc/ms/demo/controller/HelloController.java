package com.imooc.ms.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.ms.rabbitmq.MQSender;
import com.imooc.ms.result.CodeMsg;
import com.imooc.ms.result.Result;

//@RestController
@Controller
@RequestMapping("/demo")
public class HelloController {
	@Autowired
	MQSender sender;

	@RequestMapping("/")
	@ResponseBody
	public String getHello() {
		return "hello ydd";
	}

	// 1.rest api json输出 2.页面
	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> getSuccess() {
		return Result.success("成功数据");
	}

	@RequestMapping("/helloError")
	@ResponseBody
	public Result<String> getError() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}

	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "wujiaj");
		return "hello1";
	}

	@RequestMapping("/mq")
	@ResponseBody
	public Result<String> mq() {
		sender.send("hallo,ydd");
		return Result.success("csdc");
	}

	
	
	@RequestMapping("/mq/topic")
	@ResponseBody
	public Result<String> topic() {
		sender.sendTopic("hello,楊端端");
		return Result.success("Hello，world");
	}

	@RequestMapping("/mq/fanout")
	@ResponseBody
	public Result<String> fanout() {
		sender.sendFanout("hello,fanout");
		return Result.success("Hello，world");
	}

	@RequestMapping("/mq/header")
	@ResponseBody
	public Result<String> header() {
		sender.sendHeader("hello,imooc");
		return Result.success("Hello，world");
	}

}