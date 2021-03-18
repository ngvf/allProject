package com.imooc.ms.validator.MethodParamValidator.two;

import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

import org.junit.Test;

/*
 * java bean 校驗
 */
public class Person {
	@NotNull
	public String name;
	@NotNull
	@Min(0)
	public Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

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

	@Test
	public void test1() {
		Validator validator = obtainValidator();

		Person person = new Person();
		person.setAge(-1);
		Set<ConstraintViolation<Person>> result = validator.validate(person);
		Stream<ConstraintViolation<Person>> stream = result.stream();
		
		// 输出校验结果
		result.stream()
				.map(v -> v.getPropertyPath() + "= " + v.getMessage() + ": " + v.getInvalidValue())
				.forEach(System.out::println);
		
		
		
		
	}

}
