package com.imooc.ms.util;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * @author yangdd
	 * @return 生成唯一性主键id
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
//		return UUID.randomUUID().toString();

	}

	public static void main(String[] args) {
		System.out.println(UUIDUtil.uuid());//9ee68e7893a942d09311469470e9756a
	}                                        

}
