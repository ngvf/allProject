package com.imooc.ms.validator.FieldValidator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.imooc.ms.util.ValidatorUtil;

/*
 * IsMobileValidator为自定义注解的校验类
 * ConstraintValidator<IsMobile, String> 第一个自定义注解类，第二个为需要校验的数据类型。
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;
	
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
