package token;

import nfinal.*;

public class Token<T> {
	private TokenType type;
	private T content;
	private NFinal n;
	protected Token(TokenType type, T content){
		this.type = type;
		this.content = content;
	}
	public TokenType getType() {
		return type;
	}
	public T getContent() {
		return content;
	}
	@Override
	public String toString(){
		String str = "";
		switch(type){
		case DATATYPE:
			str = "DATATYPE";
			break;
		case ID:
			str = "ID";
			break;
		case SEM:
			str = "SEM";
			break;
		case L_BRACKET:
			str = "L_BRACKET";
			break;
		case R_BRACKET:
			str = "R_BRACKET";
			break;
		case L_MBRACKET:
			str = "L_MBRACKET";
			break;
		case R_MBRACKET:
			str = "R_MBRACKET";
			break;
		case L_BRACES:
			str = "L_BRACES";
			break;
		case R_BRACES:
			str = "R_BRACES";
			break;
		case EVAL:
			str = "EVAL";
			break;
		case COMMA:
			str = "COMMA";
			break;
		case POINT:
			str = "POINT";
			break;
		case COMPARE:
			str = "COMPARE";
			break;
		case LOGIC:
			str = "LOGIC";
			break;
		case OP:
			str = "OP";
			break;
		case FUNC:
			str = "FUNC";
			break;
		case VAR:
			str = "VAR";
			break;
		case WHILE:
			str = "WHILE";
			break;
		case IF:
			str = "IF";
			break;
		case BREAK:
			str = "BREAK";
			break;
		case CONTINUE:
			str = "CONTINUE";
			break;
		case ERR:
			str = "ERR";
			break;
		case END:
			str = "END";
			break;
		case D:
			str = "D";
			break;
		case T:
			str = "T";
			break;
		case IDS:
			str = "IDS";
			break;
		case B:
			str = "B";
			break;
		case C:
			str = "C";
			break;
		}
		str += "\t\t";
		if(type == TokenType.VAR){
			str += ((Var)content).getData();
		}else
			str += (String)content;
		return str;
	}
}
