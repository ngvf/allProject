package com.imooc.ms.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.imooc.ms.demo.entity.User;

@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {
	@Select("SELECT * FROM tb_user")
    List<User> findAll();
	
	
	@Insert("insert  into tb_user (id,username,password) values(#{id},#{username},#{password})")
    Integer insertUser(User user);
	
}