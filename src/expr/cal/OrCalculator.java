package expr.cal;

public class OrCalculator implements Calculator {
	static private Calculator self = null;
	private OrCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new OrCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return (int)((float)(Float.valueOf(num1))) | (int)((float)(Float.valueOf(num2)));
	}

}
