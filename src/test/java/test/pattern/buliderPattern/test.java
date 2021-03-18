package test.pattern.buliderPattern;

public class test {

	public static void main(String[] args){
		Bulider bd=new impBulider();
		
		Director director = new Director(bd);
		
		prodocter construct = director.construct();
		
		construct.show();
	}
	
	
}
