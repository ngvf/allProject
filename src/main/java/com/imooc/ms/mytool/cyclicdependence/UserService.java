package com.imooc.ms.mytool.cyclicdependence;

public class UserService {
	@CodeBearAutowired
    public OrderService orderService;
}
