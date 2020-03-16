package token;

public enum TokenType {
	DATATYPE,		//基本数据类型
	ID,					//标识符
	SEM,				//分号
	L_BRACKET,		//左括号
	R_BRACKET,		//右括号
	L_MBRACKET,		//左中括号
	R_MBRACKET,		//右中括号
	L_BRACES,			//左大括号
	R_BRACES,			//右大括号
	EVAL,				//赋值等于
	COMMA,			//逗号
	POINT,				//点号
	COMPARE,		//比较运算符
	LOGIC,				//逻辑运算符
	OP,					//算术运算符
	FUNC,				//自带函数
	VAR,				//数值
	//流程控制
	WHILE,					
	IF,
	BREAK,
	CONTINUE,
	
	
	ERR,					//错误
	END,					//结束
	
	D,			//非终结符
	T,
	IDS,
	B,
	C
	
}
