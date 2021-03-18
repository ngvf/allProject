package com.imooc.ms.redis;

public interface KeyPrefix {

	public int expireSeconds();
	
	public String getPrefix();
}
