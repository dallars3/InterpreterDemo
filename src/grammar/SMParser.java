package grammar;

import java.util.HashMap;
import java.util.LinkedList;

import semant.SemantAnalyzer;
import token.*;

public class SMParser {
	//private LinkedList<Token> tl;
	private SMI smi;
	private TokenFactory tf;
	private SemantAnalyzer sa;
	private static SMParser p = null;
	/*lr1 状态转化表 数组下标为自动机当前状态，key为下个token的type，
	*value非负数为s的参数，负数为r的参数的相反数
	 * */
	private HashMap<TokenType, Integer>[] hm;
	//每个产生式的右部的token数量，删除时使用
	private int[] rReduce = {1, 3, 0, 2, 1, 1, 1, 1, 1, 0, 3, 3, 1};
	private SMParser(){
		tf = TokenFactory.getTokenFactory();
		init();
	}
	public static SMParser getParser(){
		if(p == null){
			p = new SMParser();
		}
		return p;
	}
	public void init(){
		//输入 LR1状态转化表
		hm = new HashMap[17];
		for(int i = 0; i < 17; i++){
			hm[i] = new HashMap<TokenType,Integer>();
		}
		hm[0].put(TokenType.DATATYPE, 3);
		hm[0].put(TokenType.D, 1);
		hm[0].put(TokenType.T, 2);
		hm[0].put(TokenType.B, 4);
		hm[2].put(TokenType.ID, 6);
		hm[2].put(TokenType.IDS, 7);
		hm[3].put(TokenType.ID, -4);
		hm[3].put(TokenType.L_MBRACKET, -4);
		hm[3].put(TokenType.IDS, -4);
		hm[3].put(TokenType.C, -4);
		hm[4].put(TokenType.ID, 13);
		hm[4].put(TokenType.L_MBRACKET, 14);
		hm[4].put(TokenType.IDS, 13);
		hm[4].put(TokenType.C, 12);
		hm[6].put(TokenType.COMMA, -12);
		hm[6].put(TokenType.SEM, -12);
		hm[7].put(TokenType.COMMA, 10);
		hm[7].put(TokenType.SEM, 8);
		hm[8].put(TokenType.END, -1);
		hm[10].put(TokenType.ID, 6);
		hm[10].put(TokenType.IDS, 11);
		hm[11].put(TokenType.COMMA, 10);
		hm[11].put(TokenType.SEM, -11);
		hm[12].put(TokenType.ID, -3);
		hm[12].put(TokenType.IDS, -3);
		hm[13].put(TokenType.ID, -9);
		hm[13].put(TokenType.IDS, -9);
		hm[14].put(TokenType.VAR, 15);
		hm[15].put(TokenType.R_MBRACKET, 16);
		hm[16].put(TokenType.ID, -10);
		hm[16].put(TokenType.IDS, -10);
	}
	public void run(LinkedList<Token> tl, SemantAnalyzer sa){
		smi = new SMI();
		Token token;
		int i = 0;
		try {
			
		
			while(tl.getFirst().getType() != TokenType.D){

				//取一个token
				int retu;
				token = tl.get(i);
				//System.out.println(" "+smi.getState()+" "+token.getType());
				if(smi.getState() == 8) retu = -1;
				else retu = hm[smi.getState()].get(token.getType());
				if(retu >= 0){
					smi.s(retu);
					if(smi.getState() != 13){
						sa.distribute(retu, token);
						tl.removeFirst();
						
					}
					
				}else{
					//System.out.print(" 归约"+(-retu)+" ");
					TokenType type = smi.r(-retu);
					
					//最后一个被归约的token被替换为归约结果
					//i--;
					//System.out.print("  remove"+i+"  ");
					tl.addFirst(tf.createToken(type, ""));
					//删除其余被归约的token
					/*for(int j = 0; j < rReduce[-retu] - 1; j++){
						
						i--;
						System.out.print("  remove"+i+"  ");
						tl.remove(i);
					}*/
					
				}
				
			}
		} catch (Exception e) {
			return;
		}
			
			
		
		
		
	}
}
