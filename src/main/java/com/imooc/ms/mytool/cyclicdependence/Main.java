package com.imooc.ms.mytool.cyclicdependence;

public class Main {
	/*
	 * 预加载调用
	 */
//	  public static void main(String[] args) {
//	        Cycle cycle = new Cycle();
//	        cycle.init();
//	        UserService userService = (UserService) cycle.getBean("userService");
//	        OrderService orderService = (OrderService) cycle.getBean("orderService");
//	        System.out.println(userService.orderService);
//	        System.out.println(orderService.userService);
//	    }
	  
	  /*
	   * 懒加载调用
	   */
	  public static void main(String[] args) {
	        Cycle cycle = new Cycle();
	        UserService userService = (UserService) cycle.getBean("userService");
	        OrderService orderService = (OrderService) cycle.getBean("orderService");
	        System.out.println(userService.orderService);
	        System.out.println(orderService.userService);
	    }
}
