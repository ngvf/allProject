package com.imooc.ms.redis;

public class OrderKey extends BasePrefix {

	public static final OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

	public OrderKey(String prefix) {
		super(prefix);
	}

}
