package lex;

import java.util.LinkedList;

import token.*;

public class LexAnalyzer {
	LexReader ld;
	private static LexAnalyzer la = null;
	//单例模式
	public static LexAnalyzer getLexAnalyzer(){
		if(la == null) la = new LexAnalyzer();
		return la;
	}
	private LexAnalyzer(){
		ld = new LexReader();
	}
	//构建Token
	private Token getToken(){
		Token<String> token;
		Token<Var> tokenV;
		TokenFactory tf = TokenFactory.getTokenFactory();
		String[] str = ld.getLex();
		
		switch(str[0]){
		case "NULL":
			return null;
		case "&":
		case "|":
		case "~":
		case "+":
		case "-":
		case "*":
		case "/":
			token = tf.createToken(TokenType.OP, str[0]);
			return token;
		case ">":
		case "<":
		case "==":
			token = tf.createToken(TokenType.COMPARE, str[0]);
			return token;
		case "&&":
		case "||":
		case "!":
			token = tf.createToken(TokenType.LOGIC, str[0]);
			return token;
		case ";":
			token = tf.createToken(TokenType.SEM, str[0]);
			return token;
		case ".":
			token = tf.createToken(TokenType.POINT, str[0]);
			return token;
		case ",":
			token = tf.createToken(TokenType.COMMA, str[0]);
			return token;
		case "=":
			token = tf.createToken(TokenType.EVAL, str[0]);
			return token;
		case "(":
			token = tf.createToken(TokenType.L_BRACKET, str[0]);
			return token;
		case ")":
			token = tf.createToken(TokenType.R_BRACKET, str[0]);
			return token;
		case "[":
			token = tf.createToken(TokenType.L_MBRACKET, str[0]);
			return token;
		case "]":
			token = tf.createToken(TokenType.R_MBRACKET, str[0]);
			return token;
		case "{":
			token = tf.createToken(TokenType.L_BRACES, str[0]);
			return token;
		case "}":
			token = tf.createToken(TokenType.R_BRACES, str[0]);
			return token;
		case "NUM":
			//整数
			if(str[1].indexOf('.') == -1){
					Var<Integer> content = new Var<Integer>(VarType.INT, Integer.valueOf(str[1]));
					tokenV = tf.createToken(TokenType.VAR, content);
			}
			//小数
			else{
				Var<Float> content;
				try{
					content = new Var<Float>(VarType.FLOAT, Float.valueOf(str[1]));
				}catch(NumberFormatException nfe){
					System.out.println("Number Format Error!");
					content = new Var<Float>(VarType.FLOAT, (float)0.0);
				}
				tokenV = tf.createToken(TokenType.VAR, content);
			}
			return tokenV;
		case "WORD":
			switch(str[1]){
			case "int":
			case "float":
			case "boolean":
			case "char":
			case "string":
				token = tf.createToken(TokenType.DATATYPE, str[1]);
				return token;
			case "while":
				token = tf.createToken(TokenType.WHILE, str[1]);
				return token;
			case "if":
				token = tf.createToken(TokenType.IF, str[1]);
				return token;
			case "break":
				token = tf.createToken(TokenType.BREAK, str[1]);
				return token;
			case "continue":
				token = tf.createToken(TokenType.CONTINUE, str[1]);
				return token;
			case "true":
				tokenV = tf.createToken(TokenType.VAR, new Var<Boolean>(VarType.BOOLEAN, true));
				return tokenV;
			case "false":
				tokenV = tf.createToken(TokenType.VAR, new Var<Boolean>(VarType.BOOLEAN, false));
				return tokenV;
			case "print":
				token = tf.createToken(TokenType.FUNC, str[1]);
				return token;
			default:
				token = tf.createToken(TokenType.ID, str[1]);
				return token;
			}
		case "STRING":
			tokenV = tf.createToken(TokenType.VAR, new Var<String>(VarType.STRING, str[1]));
			return tokenV;
		case "CHAR":
			tokenV = tf.createToken(TokenType.VAR, new Var<String>(VarType.CHAR, str[1]));
			return tokenV;
		case "ERR":
			token = tf.createToken(TokenType.ERR, str[0]);
			return token;
		case "END":
			token = tf.createToken(TokenType.END, str[0]);
			return token;
		default:
			token = tf.createToken(TokenType.ERR, str[0]);
			return token;
			
		}
		
		
		
		
	}
	public LinkedList<Token> getTokenList(){
		LinkedList<Token> tokenList = new LinkedList<Token>();
		Token token;
		do{
			token = getToken();
			if(token == null) {
				
				continue;
			}
			tokenList.add(token);
			
		}while(token == null || token.getType() != TokenType.END);
		return tokenList;
	}

}
