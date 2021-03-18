package com.imooc.ms.mytool.springbeanlifecycle.beanInBeanFactory;

import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/*
 *   在BeanFactory中Bean的生命周期与ApplicationContext中bean的生命周期唯一不同的是：
 *   若bean实现了ApplicationContextAware()接口，在ApplicationContext中bean的生命周期中setApplicationContext()方法会被调用,
 *   而在BeanFactory中Bean的生命周期此方法不会被调用。
 */
public class BeanTest {

	@Test

	public void test() {

		// ApplicationContext context = new ClassPathXmlApplicationContext(
		//
		// "classpath:com/imooc/ms/mytool/springbeanlifecycle/spring-dao.xml");
		//
		// // 强制造型
		//
		// ConfigurableApplicationContext cont =
		// (ConfigurableApplicationContext) context;
		//
		// // 执行关闭 可以考到 destory-method的方法的执行
		//
		// cont.close();

		/*
		 * 应用上下文与bean工厂的另一个重要区别是关于单例bean如何被加载。

           bean工厂延迟加载所有bean，直到getBean()方法被调用。

                           应用上下文会在启动后预载入所有单例bean.这样可确保应用不需要等待他们被创建。
		 */
		
		// 创建资源对象

		ClassPathResource resource = new ClassPathResource(
				"com/imooc/ms/mytool/springbeanlifecycle/beanInBeanFactory/spring-dao.xml");

		// 采用BeanFactory初始化容器

		ConfigurableBeanFactory cbf = new XmlBeanFactory(resource);

		// bean后置处理器必须通过addBeanPostProcessor(new BeanPostProcessorImpl())手动添加

		cbf.addBeanPostProcessor(new BeanPostProcessorImpl());

		// 在配置文件中给BeanPostProcessorImpl的bean加上id="beanPostProcessorImpl"
		// //cbf.addBeanPostProcessor(cbf.getBean("beanPostProcessorImpl",
		// BeanPostProcessorImpl.class));

		// 获取Bean对象

		HelloDaoImpl bean = cbf.getBean("helloDaoImpl", HelloDaoImpl.class);

		// 销毁

		cbf.destroySingletons();

	}

}