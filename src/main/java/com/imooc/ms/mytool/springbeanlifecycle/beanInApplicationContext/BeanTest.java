package com.imooc.ms.mytool.springbeanlifecycle.beanInApplicationContext;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;

import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

	@Test

	public void test() {

		ApplicationContext context = new ClassPathXmlApplicationContext(

				"classpath:com/imooc/ms/mytool/springbeanlifecycle/beanInApplicationContext/spring-dao.xml");

		// 强制造型

		ConfigurableApplicationContext cont = (ConfigurableApplicationContext) context;

		// 执行关闭 可以考到 destory-method的方法的执行
		ConfigurableListableBeanFactory beanFactory = cont.getBeanFactory();
		Object bean = beanFactory.getBean("helloDaoImpl");
		HelloDaoImpl  helloDaoImpl = (HelloDaoImpl)cont.getBean("helloDaoImpl");
		
		 
		 cont.close();

	}

}