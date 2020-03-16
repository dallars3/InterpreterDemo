package handler;

import java.util.HashMap;
import java.util.LinkedList;

import func.Print;
import token.Token;
import token.TokenType;
import token.Var;

public class FuncHandler implements Handler {
	static Handler handler = null;
	private FuncHandler() {}
	public static Handler getHandler(){
		if(handler == null) handler = new FuncHandler();
		return handler;
	}
	@Override
	public void run(LinkedList<Token> tList, HashMap<String, Var> varMap) {
		switch(tList.removeFirst().getContent().toString()){
		case "print":
			funcPrint(tList, varMap);
			break;
			
			
		}

	}
	//printº¯Êý
	private void funcPrint(LinkedList<Token> tList, HashMap<String, Var> varMap){
		Print print = new Print();
		Token token;
		do{
			token = tList.removeFirst();
			if(token.getType() == TokenType.ID){
				print.run(varMap.get(token.getContent()).getData().toString());
			}else if(token.getType() == TokenType.VAR){
				print.run(((Var)(token.getContent())).getData().toString());
			}
		}while(token.getType() != TokenType.SEM);
	}

}
