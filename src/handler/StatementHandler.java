package handler;

import java.util.HashMap;
import java.util.LinkedList;

import grammar.SMParser;
import semant.SemantAnalyzer;
import token.Token;
import token.Var;

public class StatementHandler implements Handler {
	static Handler handler = null;
	private StatementHandler() {}
	public static Handler getHandler(){
		if(handler == null) handler = new StatementHandler();
		return handler;
	}
	@Override
	public void run(LinkedList<Token> tList, HashMap<String, Var> varMap) {
		SemantAnalyzer sa = new SemantAnalyzer(varMap);	
		SMParser p = SMParser.getParser();
		p.run(tList, sa);

	}

}
