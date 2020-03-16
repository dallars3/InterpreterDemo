package expr;

import java.util.*;

import expr.cal.Calculator;
import expr.cal.CalculatorFactory;
import token.*;

public class ExprSDT {
	ArrayList<ExprTreeNode> nodeList;			//所有操作符子树的list
	LinkedList<Float> numList;							//所有数字的list					
	LinkedList<Boolean> TFList;
	HashMap<String, Var> varMap;
	LinkedList<Token> tl;
	ExprTreeFactory etf;
	HashMap<String, Integer> prio;				//操作符优先级
	boolean cal;
	public ExprSDT(LinkedList<Token> tl , HashMap<String, Var> varMap, boolean cal) {
		this.tl = tl;
		nodeList = new ArrayList<ExprTreeNode>();
		numList = new LinkedList<Float>();
		TFList = new LinkedList<Boolean>();
		prio = new HashMap<String, Integer>();
		etf = ExprTreeFactory.getExprTreeFactory();
		this.cal = cal;
		this.varMap = varMap;
		init();

	}
	//提取操作符与括号，转换为树并存入list
	private void init() {
		Iterator<Token> itToken = tl.iterator();
		Token pre = null;
		while(itToken.hasNext()) {
			Token token = itToken.next();
			if(token.getType() == TokenType.SEM ||token.getType() == TokenType.L_BRACES) break;
			//如果是操作符token，依托操作符创建树
			if(token.getType() == TokenType.OP || token.getType() == TokenType.LOGIC) {
				ExprTreeNode node;
				//如果该token为第一个token 或 上一个token不是数据(是括号或操作符) 则这是个一元运算符
				if(pre == null || 
					pre.getType() != TokenType.VAR && 
					pre.getType() != TokenType.R_BRACKET &&
					pre.getType() != TokenType.ID) {
					node = etf.createExprTree((String)(token.getContent()), 1);
				}
				//反之，二元运算符
				else{
					node = etf.createExprTree((String)(token.getContent()), 2);
				}
				
				nodeList.add(node);
			}
			//如果是括号token，依托括号创建单node，用于识别出栈入栈操作。
			else if(token.getType() == TokenType.L_BRACKET || token.getType() == TokenType.R_BRACKET){
				ExprTreeNode node = etf.createExprTree((String)(token.getContent()), 0);
				nodeList.add(node);
			}
			//如果是var token，将数字依次存入numList
			else if(token.getType() == TokenType.VAR || token.getType() == TokenType.ID){
				if(cal == true){
					float t;
					if(token.getType() == TokenType.ID){
						String varName = token.getContent().toString();
						
						try{
							t = (float)(varMap.get(varName).getData());
							
						}catch(Exception e){
							t = (int)(varMap.get(varName).getData());
						}
					}else{
						try{
							t = (float)( ( (Var)( token.getContent() ) ).getData() );
						}catch (Exception e) {
							t = (int)( ( (Var)( token.getContent() ) ).getData() );
						}
					}
					numList.add(t);
				}else{
					boolean t;
					if(token.getType() == TokenType.ID){
						String varName = token.getContent().toString();
						t = (Boolean)(varMap.get(varName).getData());
					}else{
						t = (Boolean)(( (Var)( token.getContent() ) ).getData());
					}
					TFList.add(t);
				}
			}
			pre = token;
		}
		//赋予操作符优先级(0最低)
		if(cal == true){
			prio.put("|", 0);
			prio.put("&", 1);
			prio.put("+", 2);
			prio.put("-", 2);
			prio.put("*", 3);
			prio.put("/", 3);
			prio.put("--", 4);
			prio.put("~", 4);
		}else{
			prio.put("||", 0);
			prio.put("&&", 1);
			prio.put("!", 2);
		}
	}
	//连接root1和root2两个树(参考优先级map)，返回连接后的root
	//(若bracket为true，则代表当前root2为括号内树，应直接视其为最高优先级)
	private ExprTreeNode connectTree(ExprTreeNode root1, ExprTreeNode root2, boolean bracket){
		ExprTreeNode re;
		//root1 == null，代表当前root2为整个expr第一个树，直接返回root2
		if(root1 == null) return root2;
		//两个树的root的操作符优先级比较
		if(bracket == false && prio.get(root1.getData()) >= prio.get(root2.getData())){
			//root1操作符优先级高，用root1替换root2的最左叶子，返回root2
			re = root2;
			ExprTreeNode rl = root2.rightLeaf();
			rl.getParent().setLeftChild(root1);			//root2的最左叶子的父节点的右节点 == root2的最左叶子
		}else{
			//与上反之
			re = root1;
			ExprTreeNode rl = root1.rightLeaf();
			rl.getParent().setRightChild(root2);
		}
		return re;
	}
	//连接所有的子树，返回最终expr树的root。过程是在不断取出nodelist中的子树，并把他们不断加入总expr树
	private ExprTreeNode getTotalTree(){
		ExprTreeNode root = null;
		Iterator<ExprTreeNode> nodeIterator = nodeList.iterator();
		//用于应对括号的栈
		/*当遇到左括号时，应开辟一个全新的树来存储该层括号的最终expr树
		 * 所以旧root(即外一层正在拼接的总expr树)应暂时入栈保存，开始拼接当前层expr树
		 * 当遇到右括号时，表示当前括号层结束，应取出栈顶的树(即上一层)，置为root，
		 * 原root(内层最终expr树)用temp存储(即将整个括号内的expr树看做一个整体的操作符expr树处理)
		 * */
		Stack<ExprTreeNode> treeStk = new Stack<ExprTreeNode>();
		while(nodeIterator.hasNext()){
			//temp保存当前遍历到的子树的root
			ExprTreeNode temp = nodeIterator.next();
			//若为左括号，则入栈当前root，并重置root为null
			if(temp.getData().equals("(")){
				treeStk.push(root);
				root = null;
				continue;
			}
			//若为右括号，则当前root变为temp，出栈栈顶，置为root
			else if(temp.getData().equals(")")){
				temp = root;
				root = treeStk.pop();
				root = connectTree(root, temp, true);
				continue;
			}
			//新的root为root和temp连接后的树的root
			root = connectTree(root, temp, false);
		}
		return root;
	}
	//按照后序遍历递归计算整棵树
	private void LRDComputeTree(ExprTreeNode node){
		LinkedList list;
		if(cal){
			list = numList;
		}else{
			list = TFList;
		}
		//System.out.print(node.getData()+" ");
		if(node.getData() == "E"){
			node.setData(""+list.removeFirst());
		}else{
			if(node.getLeftChild() != null)
				LRDComputeTree(node.getLeftChild());
			//System.out.print("RIGHT:");
			LRDComputeTree(node.getRightChild());
			if(node.getLeftChild() != null){
				node.setData(compute(node.getLeftChild().getData(), node.getData(), node.getRightChild().getData()));
			}
			else{
				node.setData(compute(null, node.getData(), node.getRightChild().getData()));
			}
		}
		//System.out.print("Return:");
	}
	//计算单算式 num1 op num2
	private String compute(String num1, String op, String num2){
		CalculatorFactory cf = CalculatorFactory.getCalculatorFactory();
		Calculator cal = cf.createCalculator(op);
		float t = cal.compute(num1, num2);
		if(!this.cal){
			if(t == (float)1.0) return "true";
			else return "false";
		}
		//System.out.print(num1+op+num2+"="+t+" ");
		return t+"";
	}
	public Var run(){
		String re;
		if(nodeList.size() == 0){
			if(cal)
				re = "" + numList.getFirst();
			else 
				re = "" + TFList.getFirst();
			
		}else{
			ExprTreeNode root = getTotalTree();
			LRDComputeTree(root);
			re = root.getData();
		}
		if(! cal){
			boolean temp = Boolean.valueOf(re);
			Var<Boolean> var = new Var<Boolean>(VarType.BOOLEAN, temp);
			return var;
		}
		else if(re.indexOf('.') == -1){
			int temp = Integer.valueOf(re);
			Var<Integer> var = new Var<Integer>(VarType.INT, temp);
			return var;
		}else{
			float temp = Float.valueOf(re);
			Var<Float> var = new Var<Float>(VarType.FLOAT, temp);
			return var;
		}
	}


}
