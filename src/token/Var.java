package token;

public class Var<T> {
	private VarType type;
	private T data;
	protected boolean arr = false;
	public Var(){}
	public Var(VarType type, T data){
		this.type = type;
		this.data = data;
	}
	public Var(VarType type){
		this.type = type;
	}
	public boolean arrTOF(){
		return arr;
	}
	public VarType getType() {
		return type;
	}
	public void setType(VarType type) {
		this.type = type;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
