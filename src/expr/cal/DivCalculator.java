package expr.cal;

public class DivCalculator implements Calculator {
	static private Calculator self = null;
	private DivCalculator(){}
	

	public static Calculator getCalculator() {
		if(self == null){
			self = new DivCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return Float.valueOf(num1) / Float.valueOf(num2);
	}

}
