package expr.cal;

public class MulCalculator implements Calculator {
	static private Calculator self = null;
	private MulCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new MulCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return Float.valueOf(num1) * Float.valueOf(num2);
	}

}
