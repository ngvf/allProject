package com.imooc.ms.validator.MethodParamValidator.two;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

import org.junit.Test;

//校驗方法參數
public class PersonService {

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

	public Person getOne(@NotNull @Min(1) Integer id, String name) throws NoSuchMethodException {
		// 校验逻辑
		Method currMethod = this.getClass().getMethod("getOne", Integer.class, String.class);
		Set<ConstraintViolation<PersonService>> validResult = obtainExecutableValidator()
				.validateParameters(this, currMethod, new Object[] { id, name });
		if (!validResult.isEmpty()) {
			// ... 输出错误详情validResult
			validResult.stream().map(
					v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue())
					.forEach(System.out::println);
			throw new IllegalArgumentException("参数错误");
		}
		return null;
	}

	@Test
	public void test2() throws NoSuchMethodException {
		new PersonService().getOne(0, "A哥");
	}

}
