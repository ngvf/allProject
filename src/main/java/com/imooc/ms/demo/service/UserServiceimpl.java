package com.imooc.ms.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.ms.demo.dao.UserMapper;
import com.imooc.ms.demo.entity.User;

@Service("userService")
public class UserServiceimpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

//   @Transactional
	public Integer testTX() {
		// TODO Auto-generated method stub
		User user =new User();
		user.setId(777l);
		user.setUsername("何鹏1");
		user.setPassword("4217");
		userMapper.insertUser(user);
		
		User user1 =new User();
		user1.setId(1l);
		user1.setUsername("杨xo");
		user1.setPassword("8520");
		userMapper.insertUser(user1);
		
		return 2;
	}
}