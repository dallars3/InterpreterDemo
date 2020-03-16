package expr.cal;

public class InvCalculator implements Calculator {
	static private Calculator self = null;
	private InvCalculator(){}
	
	public static Calculator getCalculator() {
		if(self == null){
			self = new InvCalculator();
		}
		return self;
	}
	@Override
	public float compute(String num1, String num2) {
		return ~ (int)((float)(Float.valueOf(num2)));
	}
}
