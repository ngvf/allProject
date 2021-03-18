package com.imooc.ms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@SpringBootApplication
@MapperScan("com.imooc.ms.demo.dao,com.imooc.ms.user.dao,")//使用MapperScan批量扫描所有的Mapper接口；
@ComponentScan("com.imooc.ms.*") //扫描ms以下的所有包
public class App 
{
    public static void main(String[] args)
    {
    	  SpringApplication.run(App.class, args);
    }
    
}


