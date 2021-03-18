package com.imooc.ms.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@Service
public class RedisService {

	@Autowired
	JedisPool jedisPool;

	/*
	 * 获取单个值
	 */
	public <T> T get(KeyPrefix keyPrefix,String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			String string = jedis.get(realKey);
			T t = stringToBean(string, clazz);
			return t;
		} finally {
			returnToPool(jedis);
		}

	}
/**
 * 设置单个值
 * @author yangdd
 * @param <T>
 * @param prefix
 * @param key
 * @param value
 * @return
 */
	public <T> boolean set(KeyPrefix prefix,String key, T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			
			String strValue = beanToString(value);
			if (strValue == null || strValue.length() < 0) { return false; }
			String realKey=prefix.getPrefix()+key;
			int expireSeconds = prefix.expireSeconds();
			if(expireSeconds<=0){
				jedis.set(realKey, strValue);
			}else {
				jedis.setex(realKey, expireSeconds, strValue);	
			}
			return true;
		} finally {
			returnToPool(jedis);
		}

	}

	
	public static <T> String beanToString(T value) {
		// TODO Auto-generated method stub
		if (value == null) { return null; }
		Class<? extends Object> clazz = value.getClass();

		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		}
		else if (clazz == String.class) {
			return (String) value;
		}
		else if (clazz == long.class || clazz == Long.class) {
			return "" + value;
		}
		else {
			return JSON.toJSONString(value);

		}
	}
	/*
	 * 判断可以是否存在
	 * 
	 */
	public <T> boolean exists(KeyPrefix keyPrefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			return jedis.exists(realKey);
		} finally {
			returnToPool(jedis);
		}
	}
	/*
	 * 值增加
	 */
	
	public <T> Long incr(KeyPrefix keyPrefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			return jedis.incr(realKey);
		} finally {
			returnToPool(jedis);
		}

	}	
	
	/*
	 * 值减少
	 */
	public <T> Long decr(KeyPrefix keyPrefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
			return jedis.decr(realKey);
		} finally {
			returnToPool(jedis);
		}

	}	
	

	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String string, Class<T> clazz) {
		// TODO Auto-generated method stub

		if (string == null || string.length() <= 0 || clazz == null) {

		}

		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(string);
		}
		else if (clazz == String.class) {
			return (T) string;
		}
		else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(string);
		}
		else {
			return JSON.toJavaObject(JSON.parseObject(string), clazz);

		}

	}

	private void returnToPool(Jedis jedis) {
		// TODO Auto-generated method stub
		if (jedis != null) {
			jedis.close();
		}
	}
	//删除缓存
	public  boolean delete(KeyPrefix keyPrefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey=keyPrefix.getPrefix()+key;
            if( exists(keyPrefix,key)) {
            	Long del = jedis.del(realKey);
            	if(del>0) {
            		return true;
            	}
            	  return false;
            }
            return false;
		} finally {
			returnToPool(jedis);
		}
	}
	
	public boolean delete(KeyPrefix prefix) {
		if(prefix == null) {
			return false;
		}
		List<String> keys = scanKeys(prefix.getPrefix());
		if(keys==null || keys.size() <= 0) {
			return true;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	
	
	public List<String> scanKeys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<String>();
			String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*"+key+"*");
			sp.count(100);
			do{
				ScanResult<String> ret = jedis.scan(cursor, sp);
				List<String> result = ret.getResult();
				if(result!=null && result.size() > 0){
					keys.addAll(result);
				}
				//再处理cursor
				cursor = ret.getStringCursor();
			}while(!cursor.equals("0"));
			return keys;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

}
