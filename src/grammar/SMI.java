package grammar;

import java.util.Stack;

import semant.SemantAnalyzer;
import token.TokenType;
/*
 * 声明变量语句对应的 LR(1)自动机
 * 用来代表当前自动机的状态
 * */
public class SMI {
	//state状态
	private int state = 0;
	//判断是否是第一次I10跳至I11
	private boolean flag10 = true;
	//每个产生式的左半部，下标为产生式序号
	private final TokenType[] re = {TokenType.D, TokenType.D, TokenType.D, TokenType.T, TokenType.B, 
			TokenType.B, TokenType.B, TokenType.B, TokenType.B, TokenType.C, TokenType.C, TokenType.IDS, TokenType.IDS};
	//每次归约后，应跳至的状态。下标为产生式序号（-2为IDS,IDS的归约，-3为id的归约）
	private final int[] rs = {-1, 0, -1, 0, 0, 0, 0, 0, 0, 4, 4, -2, -3};
	//判断id归约后或IDS,IDS归约后应跳至的状态设置的栈
	private Stack<Integer> stkId, stkIDS;

	
	public SMI(){
		stkId = new Stack<Integer>();
		stkIDS = new Stack<Integer>();
	}
	public int getState(){
		return state;
	}
	//跳转状态，对应状态转化表中的s
	public void s(int state){
		//System.out.print(" 跳"+state+" ");
		//进入产生式IDS->id的归约状态(6)，入栈前一状态，以便归约后跳回
		if(state == 6){
			stkId.push(this.state);
		}
		//由I2进入I7，为首次推导产生式IDS->IDS,IDS，入栈I2，以便归约后跳回
		else if(state == 7){
			stkIDS.push(2);
		}
		//由I10进入I11，非首次推导产生式IDS->IDS,IDS，若非首次进入I11，入栈I10，以便归约后跳回
		/*
		 * 为何“ 若非首次进入I11，入栈I10 ”？
		 * 
		 * 因为首次推导IDS->IDS,IDS时，即会进入I11，而首次
		 * 推导该产生式的归约后应交由I2下一步处理。
		 * */
		else if(state == 10){
			if(!flag10){
				stkIDS.push(10);
			}else
				flag10 = false;
		}
		this.state = state;
		
	}
	//归约，pro为产生式序号，该函数对应状态转化表的r，返回值为归约的结果
	public TokenType r(int pro){
		//产生式为id时，出栈stkId，跳转至栈顶状态
		if(rs[pro] == -3){
			state = stkId.pop();
		}
		//产生式为IDS,IDS时，出栈stkIDS，跳至栈顶状态
		else if(rs[pro] == -2){
			state = stkIDS.pop();
		}else
			state = rs[pro];
		
		return re[pro];
	}
	

}
