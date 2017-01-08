import java.util.ArrayList;

public class Tree {
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public Tree() {
		//empty constructor
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public Node getNode(int i) {
		return nodes.get(i);
	}
	
	public Node getLastNode() {
		return nodes.get(nodes.size()-1);
	}
	
	public int getNumberOfNodes() {
		return nodes.size();
	}
	
	public String getDataOfNode(int i) {
		return (nodes.get(i)).getData();
	}
	
	public int getLevelOfNode(int i) {
		return (nodes.get(i)).getLevel();
	}
	
	public int getPCofNode(int i) {
		return (nodes.get(i)).getPC();
	}
	
	public ArrayList<Node> getListOfNodes() {
		return nodes;
	}
	
	public void clearNodes() {
		nodes.clear();
	}
	
	public int getDeepestLevel() {
		int deepestLevel = 0;
		
		for(int i = 0; i < nodes.size(); i++) {
			if ((nodes.get(i)).getLevel() > deepestLevel) {
				deepestLevel = (nodes.get(i)).getLevel();
			}
		}
		return deepestLevel;
	}

}
