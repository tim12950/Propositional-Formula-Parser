import java.util.ArrayList;

public class TruthFunction {
	
	private ArrayList<Node> domain = new ArrayList<Node>();
	//the domain is a list of the terminal nodes (propositional variables) of the wff; each variable has a user-inputed truth value.
	private ArrayList<Node> superficialDomain = new ArrayList<Node>();
	//used to prompt users to enter truth values; a subset of the domain.
	private ArrayList<Node> tree;
	//given the domain, the tree determines how the truth value of the wff will be computed.
	//the tree field points to the same arraylist object that the nodes field in the tree object points to.
	private int deepestLevel;
	//used to compute the truth values.
	
	public TruthFunction(Tree t) {
		tree = t.getListOfNodes();
	}
	
	public void addToDomain(Node n) {
		domain.add(n);
	}
	
	public int getDomainSize() {
		return domain.size();
	}
	
	public Node getDomainNode(int i) {
		return domain.get(i);
	}
	
	public String getDataFromDomainNode(int i) {
		return (domain.get(i)).getData();
	}
	
	public void clearDomain() {
		domain.clear();
	}
	
	public void createSupDomain() {
		int length = domain.size();
		
		for(int i = 0; i < length; i++) {
			superficialDomain.add(domain.get(i));
		}
		for(int i = 0; i < superficialDomain.size(); i++) {
			for(int j = i+1; j < superficialDomain.size(); j++) {
				if (((superficialDomain.get(j)).getData()).equals((superficialDomain.get(i)).getData())) {
					superficialDomain.remove(j);
					j = j-1;
					//problem: if removed, remaining elements shifted left, and so we might skip one;
					//to solve this, decrement j.
				}
			}
		}
	}
	
	public int getSupDomainSize() {
		return superficialDomain.size();
	}
	
	public Node getSupDomainNode(int i) {
		return superficialDomain.get(i);
	}
	
	public String getDataFromSupDomainNode(int i) {
		return (superficialDomain.get(i)).getData();
	}
	
	public void clearSupDomain() {
		superficialDomain.clear();
	}
	
	public void setDeepestLevel(Tree t) {
		deepestLevel = t.getDeepestLevel();
	}
	
	public void computeTruthValues() {
		for(int i = 0; i < tree.size(); i++) {
			if ((tree.get(i)).getPC() == -1) {
				(tree.get(i)).setPC();
			}
		}
		//sets the principal connective flag of each untouched node (when computeTruthValues() invoked, untouched=>non-pv).
		
		for(int level = deepestLevel-1; level >= 0; level--) {
			for(int j = 0; j < tree.size(); j++) {
				Node node = tree.get(j);
				//current node
				int PC = node.getPC();
				//gets the principal connective flag of the current node.
				
				if (node.getLevel() == level && PC != 0) {
					boolean leftChildTV = ((node.getLeftChild()).getTV());
					//the truth value of the left child
					
					if (PC == 1) {
						if (leftChildTV) {
							node.setTV(false);
						}
						else {
							node.setTV(true);
						}
					}
					else {
						boolean rightChildTV = ((node.getRightChild()).getTV());
						//the truth value of the right child
						
						if (PC == 2) {
							if (leftChildTV && !rightChildTV) {
								node.setTV(false);
							}
							else {
								node.setTV(true);
							}
						}
						else if (PC == 3) {
							if (leftChildTV && rightChildTV) {
								node.setTV(true);
							}
							else {
								node.setTV(false);
							}
						}
						else if (PC == 4) {
							if (!leftChildTV && !rightChildTV) {
								node.setTV(false);
							}
							else {
								node.setTV(true);
							}
						}
						else if (PC == 5) {
							if ((leftChildTV && rightChildTV) || (!leftChildTV && !rightChildTV)) {
								node.setTV(true);
							}
							else {
								node.setTV(false);
							}
						}
					}
				}
			}
		}
	}
	
}
