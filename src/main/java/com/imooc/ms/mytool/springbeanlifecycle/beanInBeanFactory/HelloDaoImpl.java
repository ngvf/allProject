package com.imooc.ms.mytool.springbeanlifecycle.beanInBeanFactory;

import org.springframework.beans.BeansException;

import org.springframework.beans.factory.BeanFactory;

import org.springframework.beans.factory.BeanFactoryAware;

import org.springframework.beans.factory.BeanNameAware;

import org.springframework.beans.factory.DisposableBean;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.context.ApplicationContext;

import org.springframework.context.ApplicationContextAware;

public class HelloDaoImpl implements BeanNameAware, BeanFactoryAware,

		InitializingBean, ApplicationContextAware, DisposableBean {

	private String content;
   //1.实例化
	public HelloDaoImpl() {

		System.out.println("----------1.HelloDaoImpl实例化");

	}
    //2.設值屬性值
	public void setContent(String content) {

		System.out.println("----------2.通过依赖注入的内容是:" + content);

		this.content = content;

	}

	@Override
	//3. 調用BeanNameAware的setBeanName（）
	public void setBeanName(String beanId) {

		System.out.println("----------3.输出BeanId:" + beanId);

	}

	@Override
	// 4.調用BeanFactoryAware的setBeanFactory（）
	public void setBeanFactory(BeanFactory factory) throws BeansException {

		System.out.println("----------4.設置BeanFactory:" + factory.getClass());

	}

	@Override
	// 5.調用ApplicationContextAware的setApplicationContext（）
	public void setApplicationContext(ApplicationContext applicationContext)

			throws BeansException {

		System.out.println("----------5.設置setApplicationContext：" + applicationContext);

	}

	@Override
	// 7.調用InitializingBean的afterPropertiesSet（）
	public void afterPropertiesSet() throws Exception {

		System.out.println("----------7.afterPropertiesSet");

	}
    //8.調用定制的初始化方法
	public void init() {

		System.out.println("----------8.初始化方法");

	}

	@Override
	/*
	 * 容器關閉后
	 */
	// 10.調用DisposableBean的destroy（）
	public void destroy() throws Exception {

		System.out.println("----------10.bean被销毁");

	}

	//11.調用定制的銷毀方法
	public void close() {

		System.out.println("----------11.close");

	}

}