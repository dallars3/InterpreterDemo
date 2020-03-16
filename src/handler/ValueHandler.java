package handler;

import java.util.HashMap;
import java.util.LinkedList;

import expr.ExprSDT;
import token.Token;
import token.TokenType;
import token.Var;
import token.VarType;

public class ValueHandler implements Handler {
	static Handler handler = null;
	private ValueHandler() {}
	public static Handler getHandler(){
		if(handler == null) handler = new ValueHandler();
		return handler;
	}
	@Override
	public void run(LinkedList<Token> tList, HashMap<String, Var> varMap) {
		String varName = ""+tList.removeFirst().getContent();
		if(tList.removeFirst().getType() != TokenType.EVAL){
			return;
		}
		//根据接受赋值的变量的类型，而决定右部是逻辑算式还是数字算式
		boolean cal = true;
		if(varMap.get(varName).getType() == VarType.BOOLEAN) 
			cal = false;
		ExprSDT esdt = new ExprSDT(tList, varMap, cal);
		Var var = esdt.run();
		varMap.replace(varName, var);

	}

}
