package token;

import java.util.ArrayList;

public class VarArr<T> extends Var<T> {
	private int length;
	private ArrayList<T> dataArr;
	public VarArr(VarType type, int length){
		super(type);
		this.length = length;
		dataArr = new ArrayList<T>(length);
		arr = true;
	}
	public void add(T data){
		if(dataArr.size() < length)
			dataArr.add(data);
		else{
			System.out.print("数组越界！");
			System.exit(-1);
		}
			
	}
}
