package expr;

public class ExprTreeNode {
	private String data;
	private ExprTreeNode parent;
	private ExprTreeNode leftChild;
	private ExprTreeNode rightChild;
	public ExprTreeNode(String data) {
		this.data = data;
	}
	public ExprTreeNode rightLeaf(){
		if(rightChild == null) return this;
		if(! rightChild.getData().equals("E")){
			return rightChild.rightLeaf();
		}
		return rightChild;
	}
	public ExprTreeNode getRoot(){
		if(parent == null) return this;
		if(parent.getParent() != null){
			return parent.getRoot();
		}
		return parent;
	} 
	public void addTree(ExprTreeNode node){}
	
	public ExprTreeNode getParent() {
		return parent;
	}
	public void setParent(ExprTreeNode parent) {
		this.parent = parent;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ExprTreeNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(ExprTreeNode leftChild) {
		this.leftChild = leftChild;
		leftChild.parent = this;
	}
	public ExprTreeNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(ExprTreeNode rightChild) {
		this.rightChild = rightChild;
		rightChild.parent = this;
	}
	

}
