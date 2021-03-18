package test.pattern.buliderPattern;

public abstract class Bulider {
     
	protected prodocter pro=new prodocter(); 
	public abstract void buliderPartA();
	public abstract void buliderPartB();
	public abstract void buliderPartC();
	
	public prodocter getResult() {
		return pro;
	}
	
	
}
