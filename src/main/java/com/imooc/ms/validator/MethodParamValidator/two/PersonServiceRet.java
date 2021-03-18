package com.imooc.ms.validator.MethodParamValidator.two;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

import org.junit.Test;

public class PersonServiceRet {

	// 用于Java Bean校验的校验器
	private Validator obtainValidator() {
		// 1、使用【默认配置】得到一个校验工厂 这个配置可以来自于provider、SPI提供
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		// 2、得到一个校验器
		return validatorFactory.getValidator();
	}

	// 用于方法校验的校验器
	private ExecutableValidator obtainExecutableValidator() {
		return obtainValidator().forExecutables();
	}

	/*
	 * //校验方法返回值
	 */
	public @NotNull Person getOne(@NotNull @Min(1) Integer id, String name)
			throws NoSuchMethodException {

		// ... 模拟逻辑执行，得到一个result
		Person result = null;

		// 在结果返回之前校验
		Method currMethod = this.getClass().getMethod("getOne", Integer.class, String.class);
		Set<ConstraintViolation<PersonServiceRet>> validResult = obtainExecutableValidator()
				.validateReturnValue(this, currMethod, result);
		if (!validResult.isEmpty()) {
			// ... 输出错误详情validResult
			validResult.stream().map(
					v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue())
					.forEach(System.out::println);
			throw new IllegalArgumentException("参数错误");
		}
		return result;
	}


	
	public void save(@NotNull @Valid Person person) throws NoSuchMethodException {
	    Method currMethod = this.getClass().getMethod("save", Person.class);
	    Set<ConstraintViolation<PersonServiceRet>> validResult = obtainExecutableValidator().validateParameters(this, currMethod, new Object[]{person});
	    if (!validResult.isEmpty()) {
	        // ... 输出错误详情validResult
	        validResult.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
	        throw new IllegalArgumentException("参数错误");
	    }
	}
	
	
	
//	@Test
//	public void test2() throws NoSuchMethodException {
//		// 看到没 IDEA自动帮你前面加了个notNull
//		// @NotNull
//		Person result = new PersonService().getOne(-1, "A哥");
//	}

	@Test
	public void test3() throws NoSuchMethodException {
	    // save.arg0 不能为null: null
	    // new PersonService().save(null);
	    new PersonServiceRet().save(new Person());
	}
	
	
}
