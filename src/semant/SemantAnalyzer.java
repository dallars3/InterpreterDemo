package semant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import token.*;

public class SemantAnalyzer {
	//变量类型
	private VarType type;
	//变量名集合
	private HashSet<String> varName;
	//是否数组
	private boolean arr = false;
	//数组长度
	private int arrLength = 0;
	//变量存储
	HashMap<String, Var> varMap;
	public SemantAnalyzer(HashMap<String, Var> varMap){
		varName = new HashSet<String>();
		this.varMap = varMap;
		
	}
	//分发器方法，根据当前state决定应执行的函数
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
	//增加一个变量
	private void addVarName(String name){
		varName.add(name);
	}
	//设置变量类型
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
	//设置数组长度
	private void setArrLength(Var<Integer> v){
		arrLength = v.getData();
		arr = true;
	}
	//声明变量
	private void addVarMap(){
		//是数组，申请数组空间
		if(arr){
			VarArr v = new VarArr(type, arrLength);
			Iterator<String> it = varName.iterator();
			while(it.hasNext()){
				varMap.put(it.next(), v);
			}
		}
		//是变量，申请变量空间
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
