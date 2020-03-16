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
	static LinkedList<Token> tList;						//token��list
	static HashMap<String, Var> varMap;			//�������б���
	static Iterator<Token> it;								//tlist��iterator
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
		//ÿ��ȡ��һ�䣬����distribute
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
	//���ݸþ�ĵ�һ��token�����������ĸ�handler���д���
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
	//δ�깤
	private static void whileHandler(LinkedList<Token> list){
		list.removeFirst();
		ExprSDT esdt = new ExprSDT(list, varMap, false);
		Var var = esdt.run();
		if((Boolean)(var.getData()) == false){
			while(it.next().getType() != TokenType.R_BRACES);
		}
	}
	private static void ifHandler(LinkedList<Token> list){
		//����if����߼����ʽ����Ϊfalse��������if���tokenֱ������} �� (Ƕ��ifδ�깤
		list.removeFirst();	
		ExprSDT esdt = new ExprSDT(list, varMap, false);
		Var var = esdt.run();
		if((Boolean)(var.getData()) == false){
			while(it.next().getType() != TokenType.R_BRACES);
		}
	}
}
