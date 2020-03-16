package expr.cal;

public class SubCalculator implements Calculator {
	static private Calculator self = null;
	private SubCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new SubCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return Float.valueOf(num1) - Float.valueOf(num2);
	}

}
