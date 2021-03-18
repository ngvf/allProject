package com.imooc.ms.validator.MethodParamValidator.one;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//**定义限额注解*/

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface BankTransferMoney {

	boolean required() default true;

	double maxMoney() default 10000;

	String message() default "转账金额大于限额，转账失败";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
