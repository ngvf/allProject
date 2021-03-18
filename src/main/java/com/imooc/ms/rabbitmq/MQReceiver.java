package com.imooc.ms.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.ms.miaosha.entity.MiaoshaOrder;
import com.imooc.ms.miaosha.service.GoodsService;
import com.imooc.ms.miaosha.service.MiaoshaService;
import com.imooc.ms.miaosha.service.OrderService;
import com.imooc.ms.miaosha.vo.GoodsVo;
import com.imooc.ms.redis.RedisService;
import com.imooc.ms.user.entity.MiaoshaUser;

@Service
public class MQReceiver {
	private static Logger log=LoggerFactory.getLogger(MQReceiver.class);
	

	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;
	
	 @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void miaoShaReceive(String meassage) {
		MiaoshaMessage mm = RedisService.stringToBean(meassage, MiaoshaMessage.class);
	    log.info("miaoShaReceive-----："+meassage);
	    	
		MiaoshaUser user = mm.getUser();
		long goodsId = mm.getGoodsId();
		
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		return;
    	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		return;
    	}
    	//减库存 下订单 写入秒杀订单
    	miaoshaService.miaosha(user, goods);
	    	
	    	
		}
	
	
    @RabbitListener(queues=MQConfig.QUEUE)
	public void receive(String meassage) {
    	log.info("Receiver-----："+meassage);
	}
    
	@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
	public void receiveTopic1(String message) {
		log.info(" topic  queue1 message:"+message);
	}
	
	
	@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
	public void receiveTopic2(String message) {
		log.info(" topic  queue2 message:"+message);
	}
	
	
	@RabbitListener(queues=MQConfig.HEADER_QUEUE)
	public void receiveHeaderQueue(byte[] message) {
		log.info(" header  queue message:"+new String(message));
	}
    
	
}
