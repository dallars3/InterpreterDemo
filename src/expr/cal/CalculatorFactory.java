package expr.cal;

public class CalculatorFactory {
	private static CalculatorFactory cf;
	private CalculatorFactory(){}
	public static CalculatorFactory getCalculatorFactory(){
		if(cf == null) cf = new CalculatorFactory();
		return cf;
	}
	public Calculator createCalculator(String op){
		switch(op){
		case "+":
			return AddCalculator.getCalculator();
		case "-":
			return SubCalculator.getCalculator();
		case "*":
			return MulCalculator.getCalculator();
		case "/":
			return DivCalculator.getCalculator();
		case "--":
			return NegCalculator.getCalculator();
		case "&":
			return AndCalculator.getCalculator();
		case "|":
			return OrCalculator.getCalculator();
		case "~":
			return InvCalculator.getCalculator();
		case "&&":
			return LogicAndCalculator.getCalculator();
		case "||":
			return LogicOrCalculator.getCalculator();
		case "!":
			return LogicInvCalculator.getCalculator();
		default:
			return null;
		}
	}
}
