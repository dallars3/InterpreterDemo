package expr.cal;

public class NegCalculator implements Calculator {
	static private Calculator self = null;
	private NegCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new NegCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return - Float.valueOf(num2);
	}
}
