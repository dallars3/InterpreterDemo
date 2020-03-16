package expr.cal;

public class LogicAndCalculator implements Calculator {
	static private Calculator self = null;
	private LogicAndCalculator(){}
	

	public static Calculator getCalculator() {
		if(self == null){
			self = new LogicAndCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		
		if(Boolean.valueOf(num1) && Boolean.valueOf(num2))
			return (float)1.0;
		else 
			return (float)0.0;
	}

}
