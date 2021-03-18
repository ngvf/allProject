package com.imooc.ms.miaosha.service;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.ms.miaosha.dao.OrderDao;
import com.imooc.ms.miaosha.entity.MiaoshaOrder;
import com.imooc.ms.miaosha.entity.OrderInfo;
import com.imooc.ms.miaosha.vo.GoodsVo;
import com.imooc.ms.redis.OrderKey;
import com.imooc.ms.redis.RedisService;
import com.imooc.ms.user.entity.MiaoshaUser;

@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	@Autowired
	RedisService redisService;
	
	/*
	 * 优化：将秒杀订单写入缓存，不走数据库
	 */
	
//	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
//		return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
//	}


	
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		//return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
		return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
	}
	
	public OrderInfo getOrderById(long orderId) {
		return orderDao.getOrderById(orderId);
	}
	
	
	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId = orderDao.insert(orderInfo);
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderInfo.getId());
		miaoshaOrder.setUserId(user.getId());
		orderDao.insertMiaoshaOrder(miaoshaOrder);
		
		redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+user.getId()+"_"+goods.getId(), miaoshaOrder);
		return orderInfo;
	}
	
	public void deleteOrders() {
		orderDao.deleteOrders();
		orderDao.deleteMiaoshaOrders();
	}
	
	
}
