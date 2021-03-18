package com.imooc.ms.demo.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;//编号
    private String username;//用户名
    private String password;//密码
    //省略get set方法
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}