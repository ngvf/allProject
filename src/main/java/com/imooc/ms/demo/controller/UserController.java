package com.imooc.ms.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.ms.demo.entity.User;
import com.imooc.ms.demo.service.IUserService;
import com.imooc.ms.demo.service.UserServiceimpl;
import com.imooc.ms.redis.RedisService;
import com.imooc.ms.redis.UserKey;
import com.imooc.ms.result.Result;
import com.imooc.ms.util.RedisUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserServiceimpl userService;
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
    private RedisService redisService;
	
	
	

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }
    
    
    
    @RequestMapping("/testTX")
    public Result<Integer> testTX(){
        
         return Result.success( userService.testTX());
        
    }
    
    @RequestMapping("/findRedis")
    public Result<String> findRedis(){
//    	redisUtil.set("yang", "duandaun11111111111");
//    	 String name=(String)redisUtil.get("yang");
    	 return  Result.success("dsd");
    }
    
    
    
    @RequestMapping("/redis/get")
    public Result<String> redisGet(){
    	redisService.set(UserKey.getById,"yang", "duanduan2020914");
        String string = redisService.get(UserKey.getById,"yang", String.class);
    	 return  Result.success(string);
    }
    
    
    
    
    
    
    
    
    

}