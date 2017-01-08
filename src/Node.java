
public class Node {
	
	private String data;
	private int level;
	private int principalConnective = -1;
	/*
	 * -1 for non-pv and untouched
	 * 0 for pv
	 * 1 for ~
	 * 2 for =>
	 * 3 for &
	 * 4 for ||
	 * 5 for <=>
	 */
	private Node leftChild;
	private Node rightChild;
	private boolean truthValue;
	
	public Node(String data, int level) {
		this.data = data;
		this.level = level;
	}
	
	public String getData() {
		return data;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	
	public void setLeftChild(Node n) {
		leftChild = n;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	
	public void setRightChild(Node n) {
		rightChild = n;
	}
	
	public int getPC() {
		return principalConnective;
	}
	
	public void setPC() {
		if (Parser.isPV(data)) {
			principalConnective = 0;
		}
		else if(data.equals("~" + leftChild.data)) {
			principalConnective = 1;
		}
		else if (data.equals("(" + leftChild.data + "=>" + rightChild.data + ")")) {
			principalConnective = 2;
		}
		else if (data.equals("(" + leftChild.data + "&" + rightChild.data + ")")) {
			principalConnective = 3;
		}
		else if (data.equals("(" + leftChild.data + "||" + rightChild.data + ")")) {
			principalConnective = 4;
		}
		else if (data.equals("(" + leftChild.data + "<=>" + rightChild.data + ")")) {
			principalConnective = 5;
		}
	}
	
	public boolean getTV() {
		return truthValue;
	}
	
	public void setTV(boolean v) {
		truthValue = v;
	}

}
