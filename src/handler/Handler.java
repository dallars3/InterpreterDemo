package handler;

import java.util.HashMap;
import java.util.LinkedList;

import token.Token;
import token.Var;

public interface Handler {
	public void run(LinkedList<Token> tList, HashMap<String, Var> varMap);
}
