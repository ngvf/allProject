package com.imooc.ms.mytool.lambda;

public class Student {
private String name;
private int age;
private int stature;
private SpecialityEnum specialities;

public  Student(String name, int age, int stature) {
	super();
	this.name = name;
	this.age = age;
	this.stature = stature;
	
}

public Student(String name, int age, int stature, SpecialityEnum specialities) {
	super();
	this.name = name;
	this.age = age;
	this.stature = stature;
	this.specialities = specialities;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public int getStature() {
	return stature;
}
public void setStature(int stature) {
	this.stature = stature;
}
public SpecialityEnum getSpecialities() {
	return specialities;
}
public void setSpecialities(SpecialityEnum specialities) {
	this.specialities = specialities;
} 

}


