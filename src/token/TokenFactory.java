package token;

public class TokenFactory {
	static TokenFactory tf = null;
	private TokenFactory(){}
	public static TokenFactory getTokenFactory(){
		if(tf == null) tf = new TokenFactory();
		return tf;
	}
	public Token<String> createToken(TokenType type, String content){
		return new Token<String>(type, content);
	}
	public Token<Var> createToken(TokenType type,  Var content){
		return new Token<Var>(type, content);
	}
	
}
