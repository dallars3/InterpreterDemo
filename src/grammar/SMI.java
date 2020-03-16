package grammar;

import java.util.Stack;

import semant.SemantAnalyzer;
import token.TokenType;
/*
 * ������������Ӧ�� LR(1)�Զ���
 * ��������ǰ�Զ�����״̬
 * */
public class SMI {
	//state״̬
	private int state = 0;
	//�ж��Ƿ��ǵ�һ��I10����I11
	private boolean flag10 = true;
	//ÿ������ʽ����벿���±�Ϊ����ʽ���
	private final TokenType[] re = {TokenType.D, TokenType.D, TokenType.D, TokenType.T, TokenType.B, 
			TokenType.B, TokenType.B, TokenType.B, TokenType.B, TokenType.C, TokenType.C, TokenType.IDS, TokenType.IDS};
	//ÿ�ι�Լ��Ӧ������״̬���±�Ϊ����ʽ��ţ�-2ΪIDS,IDS�Ĺ�Լ��-3Ϊid�Ĺ�Լ��
	private final int[] rs = {-1, 0, -1, 0, 0, 0, 0, 0, 0, 4, 4, -2, -3};
	//�ж�id��Լ���IDS,IDS��Լ��Ӧ������״̬���õ�ջ
	private Stack<Integer> stkId, stkIDS;

	
	public SMI(){
		stkId = new Stack<Integer>();
		stkIDS = new Stack<Integer>();
	}
	public int getState(){
		return state;
	}
	//��ת״̬����Ӧ״̬ת�����е�s
	public void s(int state){
		//System.out.print(" ��"+state+" ");
		//�������ʽIDS->id�Ĺ�Լ״̬(6)����ջǰһ״̬���Ա��Լ������
		if(state == 6){
			stkId.push(this.state);
		}
		//��I2����I7��Ϊ�״��Ƶ�����ʽIDS->IDS,IDS����ջI2���Ա��Լ������
		else if(state == 7){
			stkIDS.push(2);
		}
		//��I10����I11�����״��Ƶ�����ʽIDS->IDS,IDS�������״ν���I11����ջI10���Ա��Լ������
		/*
		 * Ϊ�Ρ� �����״ν���I11����ջI10 ����
		 * 
		 * ��Ϊ�״��Ƶ�IDS->IDS,IDSʱ���������I11�����״�
		 * �Ƶ��ò���ʽ�Ĺ�Լ��Ӧ����I2��һ������
		 * */
		else if(state == 10){
			if(!flag10){
				stkIDS.push(10);
			}else
				flag10 = false;
		}
		this.state = state;
		
	}
	//��Լ��proΪ����ʽ��ţ��ú�����Ӧ״̬ת�����r������ֵΪ��Լ�Ľ��
	public TokenType r(int pro){
		//����ʽΪidʱ����ջstkId����ת��ջ��״̬
		if(rs[pro] == -3){
			state = stkId.pop();
		}
		//����ʽΪIDS,IDSʱ����ջstkIDS������ջ��״̬
		else if(rs[pro] == -2){
			state = stkIDS.pop();
		}else
			state = rs[pro];
		
		return re[pro];
	}
	

}
