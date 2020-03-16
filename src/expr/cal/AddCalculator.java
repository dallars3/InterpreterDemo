package expr.cal;

public class AddCalculator implements Calculator {
	static private Calculator self = null;
	private AddCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new AddCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return Float.valueOf(num1) + Float.valueOf(num2);
	}


}
