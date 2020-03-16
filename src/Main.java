import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.naming.InitialContext;

import expr.ExprSDT;
import handler.FuncHandler;
import handler.Handler;
import handler.StatementHandler;
import handler.ValueHandler;
import lex.LexAnalyzer;
import token.Token;
import token.TokenType;
import token.Var;

public class Main {
	static LinkedList<Token> tList;						//token的list
	static HashMap<String, Var> varMap;			//保存所有变量
	static Iterator<Token> it;								//tlist的iterator
	public static void main(String[] args) {
		init();
		run();
	}
	private static void init() {
		LexAnalyzer la = LexAnalyzer.getLexAnalyzer();
		tList = la.getTokenList();
		tList.removeFirst();
		varMap = new HashMap<String, Var>();
	}
	private static void run(){
		it = tList.iterator();
		//每次取出一句，进行distribute
		while(it.hasNext()){
			LinkedList<Token> tempList = new LinkedList<Token>();
			while(it.hasNext()){
				Token token = it.next();
				tempList.add(token);
				if(token.getType() == TokenType.SEM 
					||token.getType() == TokenType.L_BRACES
					||token.getType() == TokenType.R_BRACES) break;
			}
			distribute(tempList);
		}
		//System.out.println("     "+varMap.get("c").getData());
		
		
	}
	//根据该句的第一个token，决定调用哪个handler进行处理
	private static void distribute(LinkedList<Token> list){
		//System.out.println(list.getFirst());
		Handler handler = null;
		switch (list.getFirst().getType()) {
		case DATATYPE:
			handler = StatementHandler.getHandler();
			break;
		case ID:
			handler = ValueHandler.getHandler();
			break;
		case FUNC:
			handler = FuncHandler.getHandler();
			break;
		case WHILE:
			whileHandler(list);
			return;
		case IF:
			ifHandler(list);
			return;
		default:
			return;
		}
		handler.run(list, varMap);
	}
	//未完工
	private static void whileHandler(LinkedList<Token> list){
		list.removeFirst();
		ExprSDT esdt = new ExprSDT(list, varMap, false);
		Var var = esdt.run();
		if((Boolean)(var.getData()) == false){
			while(it.next().getType() != TokenType.R_BRACES);
		}
	}
	private static void ifHandler(LinkedList<Token> list){
		//计算if后的逻辑表达式，若为false，则跳过if后的token直至遇到} 。 (嵌套if未完工
		list.removeFirst();	
		ExprSDT esdt = new ExprSDT(list, varMap, false);
		Var var = esdt.run();
		if((Boolean)(var.getData()) == false){
			while(it.next().getType() != TokenType.R_BRACES);
		}
	}
}
