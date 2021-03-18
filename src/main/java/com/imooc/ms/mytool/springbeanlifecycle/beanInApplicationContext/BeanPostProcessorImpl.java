package com.imooc.ms.mytool.springbeanlifecycle.beanInApplicationContext;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;

import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanPostProcessorImpl implements BeanPostProcessor {

	@Override
   //6.調用BeanPostProcessor的postProcessBeforeInitialization（）
	/*
	 * BeanPostProcessor的預初始化方法
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName)

			throws BeansException {

		System.out.println("6.我把:" + beanName + "实例化化之前的操作");

		return bean;

	}

	@Override
	//9.調用BeanPostProcessor的postProcessAfterInitialization（）
	//BeanPostProcessor的后初始化方法
	/*
	 * 在這之後bean就可以使用了
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName)

			throws BeansException {
		Field[] fields = bean.getClass().getDeclaredFields();
		int length = fields.length;
		for(int i=0;i<length;i++) {
			String name = fields[i].getName();
			System.out.println("屬性名：" +name);
		}
		System.out.println("9.我把:" + beanName + "实例化化之后的操作");
		return bean;

	}

}
