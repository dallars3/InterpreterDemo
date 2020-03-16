package semant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import token.*;

public class SemantAnalyzer {
	//��������
	private VarType type;
	//����������
	private HashSet<String> varName;
	//�Ƿ�����
	private boolean arr = false;
	//���鳤��
	private int arrLength = 0;
	//�����洢
	HashMap<String, Var> varMap;
	public SemantAnalyzer(HashMap<String, Var> varMap){
		varName = new HashSet<String>();
		this.varMap = varMap;
		
	}
	//�ַ������������ݵ�ǰstate����Ӧִ�еĺ���
	@SuppressWarnings("unchecked")
	public void distribute(int s, Token t){
		switch(s){
		case 8:
			addVarMap();
			break;
		case 3:
			setType((String)(t.getContent()));
			break;
		case 15:
			setArrLength((Var<Integer>)(t.getContent()));
			break;
		case 6:
			addVarName((String)(t.getContent()));
			break;
		
		default:	
			break;
		}
	}
	//����һ������
	private void addVarName(String name){
		varName.add(name);
	}
	//���ñ�������
	private void setType(String t){
		switch(t){
		case "int":
			type = VarType.INT;
			break;
		case "string":
			type = VarType.STRING;
			break;
		case "float":
			type = VarType.FLOAT;
			break;
		case "boolean":
			type = VarType.BOOLEAN;
			break;
		case "char":
			type = VarType.CHAR;
			break;
		}
	}
	//�������鳤��
	private void setArrLength(Var<Integer> v){
		arrLength = v.getData();
		arr = true;
	}
	//��������
	private void addVarMap(){
		//�����飬��������ռ�
		if(arr){
			VarArr v = new VarArr(type, arrLength);
			Iterator<String> it = varName.iterator();
			while(it.hasNext()){
				varMap.put(it.next(), v);
			}
		}
		//�Ǳ�������������ռ�
		else{
			Var v = new Var(type);
			Iterator<String> it = varName.iterator();
			while(it.hasNext()){
				varMap.put(it.next(), v);
			}
			
		}
		/*Set s = varMap.keySet();
		Iterator<String> its = s.iterator();
		while(its.hasNext()){
			System.out.print(its.next());
		}*/

	}
	
}
