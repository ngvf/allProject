package com.imooc.ms.validator.FieldValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class })//@Constraint指定了校验类，也就是接下来的IsMobileValidator类。
public @interface  IsMobile {
	//值得一提的是除了自定义的message、require属性外，下面的groups和payload也是必须添加的
	/*
	 * 校验时默认有值
	 * 参数是否是必须有的
	 */
	boolean required() default true;
	
	/*
	 * 校验不通过返回的信息
	 * 
	 * 那么如何获取在注解中定义的message信息呢？

          在valid校验中，如果校验不通过，会产生BindException异常，捕捉到异常后可以获取到defaultMessage也就是自定义注解中定义的内容，具体实现如下：
    BindException ex = (BindException)e;
    List<ObjectError> errors = ex.getAllErrors();
    ObjectError error = errors.get(0);
    String msg = error.getDefaultMessage();
	 * 
	 */
	String message() default "手机号码格式错误";

	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
