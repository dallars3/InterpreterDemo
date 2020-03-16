package expr.cal;

public class LogicInvCalculator implements Calculator {
	static private Calculator self = null;
	private LogicInvCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new LogicInvCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		if(! Boolean.valueOf(num2))
			return (float)1.0;
		else 
			return (float)0.0;
	}
}
