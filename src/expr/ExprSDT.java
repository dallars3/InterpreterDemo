package expr;

import java.util.*;

import expr.cal.Calculator;
import expr.cal.CalculatorFactory;
import token.*;

public class ExprSDT {
	ArrayList<ExprTreeNode> nodeList;			//���в�����������list
	LinkedList<Float> numList;							//�������ֵ�list					
	LinkedList<Boolean> TFList;
	HashMap<String, Var> varMap;
	LinkedList<Token> tl;
	ExprTreeFactory etf;
	HashMap<String, Integer> prio;				//���������ȼ�
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
	//��ȡ�����������ţ�ת��Ϊ��������list
	private void init() {
		Iterator<Token> itToken = tl.iterator();
		Token pre = null;
		while(itToken.hasNext()) {
			Token token = itToken.next();
			if(token.getType() == TokenType.SEM ||token.getType() == TokenType.L_BRACES) break;
			//����ǲ�����token�����в�����������
			if(token.getType() == TokenType.OP || token.getType() == TokenType.LOGIC) {
				ExprTreeNode node;
				//�����tokenΪ��һ��token �� ��һ��token��������(�����Ż������) �����Ǹ�һԪ�����
				if(pre == null || 
					pre.getType() != TokenType.VAR && 
					pre.getType() != TokenType.R_BRACKET &&
					pre.getType() != TokenType.ID) {
					node = etf.createExprTree((String)(token.getContent()), 1);
				}
				//��֮����Ԫ�����
				else{
					node = etf.createExprTree((String)(token.getContent()), 2);
				}
				
				nodeList.add(node);
			}
			//���������token���������Ŵ�����node������ʶ���ջ��ջ������
			else if(token.getType() == TokenType.L_BRACKET || token.getType() == TokenType.R_BRACKET){
				ExprTreeNode node = etf.createExprTree((String)(token.getContent()), 0);
				nodeList.add(node);
			}
			//�����var token�����������δ���numList
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
		//������������ȼ�(0���)
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
	//����root1��root2������(�ο����ȼ�map)���������Ӻ��root
	//(��bracketΪtrue�������ǰroot2Ϊ����������Ӧֱ������Ϊ������ȼ�)
	private ExprTreeNode connectTree(ExprTreeNode root1, ExprTreeNode root2, boolean bracket){
		ExprTreeNode re;
		//root1 == null������ǰroot2Ϊ����expr��һ������ֱ�ӷ���root2
		if(root1 == null) return root2;
		//��������root�Ĳ��������ȼ��Ƚ�
		if(bracket == false && prio.get(root1.getData()) >= prio.get(root2.getData())){
			//root1���������ȼ��ߣ���root1�滻root2������Ҷ�ӣ�����root2
			re = root2;
			ExprTreeNode rl = root2.rightLeaf();
			rl.getParent().setLeftChild(root1);			//root2������Ҷ�ӵĸ��ڵ���ҽڵ� == root2������Ҷ��
		}else{
			//���Ϸ�֮
			re = root1;
			ExprTreeNode rl = root1.rightLeaf();
			rl.getParent().setRightChild(root2);
		}
		return re;
	}
	//�������е���������������expr����root���������ڲ���ȡ��nodelist�е��������������ǲ��ϼ�����expr��
	private ExprTreeNode getTotalTree(){
		ExprTreeNode root = null;
		Iterator<ExprTreeNode> nodeIterator = nodeList.iterator();
		//����Ӧ�����ŵ�ջ
		/*������������ʱ��Ӧ����һ��ȫ�µ������洢�ò����ŵ�����expr��
		 * ���Ծ�root(����һ������ƴ�ӵ���expr��)Ӧ��ʱ��ջ���棬��ʼƴ�ӵ�ǰ��expr��
		 * ������������ʱ����ʾ��ǰ���Ų������Ӧȡ��ջ������(����һ��)����Ϊroot��
		 * ԭroot(�ڲ�����expr��)��temp�洢(�������������ڵ�expr������һ������Ĳ�����expr������)
		 * */
		Stack<ExprTreeNode> treeStk = new Stack<ExprTreeNode>();
		while(nodeIterator.hasNext()){
			//temp���浱ǰ��������������root
			ExprTreeNode temp = nodeIterator.next();
			//��Ϊ�����ţ�����ջ��ǰroot��������rootΪnull
			if(temp.getData().equals("(")){
				treeStk.push(root);
				root = null;
				continue;
			}
			//��Ϊ�����ţ���ǰroot��Ϊtemp����ջջ������Ϊroot
			else if(temp.getData().equals(")")){
				temp = root;
				root = treeStk.pop();
				root = connectTree(root, temp, true);
				continue;
			}
			//�µ�rootΪroot��temp���Ӻ������root
			root = connectTree(root, temp, false);
		}
		return root;
	}
	//���պ�������ݹ����������
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
	//���㵥��ʽ num1 op num2
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
