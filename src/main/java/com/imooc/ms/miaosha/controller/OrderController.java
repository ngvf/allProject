package com.imooc.ms.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.ms.miaosha.entity.OrderInfo;
import com.imooc.ms.miaosha.service.GoodsService;
import com.imooc.ms.miaosha.service.OrderService;
import com.imooc.ms.miaosha.vo.GoodsVo;
import com.imooc.ms.miaosha.vo.OrderDetailVo;
import com.imooc.ms.redis.RedisService;
import com.imooc.ms.result.CodeMsg;
import com.imooc.ms.result.Result;
import com.imooc.ms.user.entity.MiaoshaUser;
import com.imooc.ms.user.service.MiaoshaUserService;



@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model,MiaoshaUser user,
    		@RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
