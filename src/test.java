import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import expr.*;
import grammar.SMParser;
import lex.*;
import semant.SemantAnalyzer;
import token.*;

public class test {

	public static void main(String[] args) {
		LexAnalyzer la = LexAnalyzer.getLexAnalyzer();
		
		System.out.println("------------------------------------");
		LinkedList<Token> tList = la.getTokenList();
		tList.removeFirst();
		//ExprSDT esdt = new ExprSDT(tList, false);
		//esdt.run();
		/*HashMap<String, Var> varMap = new HashMap<String, Var>();
		SemantAnalyzer sa = new SemantAnalyzer(varMap);
		SMParser p = SMParser.getParser(tList, sa);
		p.run();*/
		

	}

}
