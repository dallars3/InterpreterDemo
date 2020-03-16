package expr;

public class ExprTreeFactory {
	static ExprTreeFactory etf;
	private ExprTreeFactory() {
	}
	public static ExprTreeFactory getExprTreeFactory(){
		if(etf == null)
			etf = new ExprTreeFactory();
		return etf;
	}
	public ExprTreeNode createExprTree(String op, int n){
		ExprTreeNode node;
		if(n == 1){
			if(op.equals("-")){
				op = "--";
			}
			node = new ExprTreeNode(op);
			node.setRightChild(new ExprTreeNode("E"));
		}else if(n == 2){
			node = new ExprTreeNode(op);
			node.setLeftChild(new ExprTreeNode("E"));
			node.setRightChild(new ExprTreeNode("E"));
		}else{
			node = new ExprTreeNode(op);
		}
		return node;
	}

}
