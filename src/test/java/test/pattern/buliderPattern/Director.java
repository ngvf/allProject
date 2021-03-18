package test.pattern.buliderPattern;

public class Director {

	public Bulider bulider;
	
	public Director(Bulider bulider) {
		this.bulider=bulider;
	}
	
	public prodocter construct() {
		bulider.buliderPartA();
		bulider.buliderPartB();
		bulider.buliderPartC();
		return bulider.getResult();
	}
	
	
	
	
	
	
	
}
