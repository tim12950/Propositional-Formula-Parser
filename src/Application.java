import java.util.Scanner;

public class Application {

	public static void main(String[] args) {
		
		Tree tree = new Tree();
		TruthFunction truthFunction = new TruthFunction(tree);
		Scanner input = new Scanner(System.in);
		//remember to invoke nextLine() after invoking next() and nextInt() so that the user will actually be prompted each time we
		//want him to be.
		
		boolean notWff;
		boolean backToMain = true;
		int option;
		
		int numberOfNodes;
		//number of nodes in the tree
		int deepestLevel;
		//deepestLevel = max(n.level : n is a node of the tree);
		//use when printing out trees
		boolean traversed;
		//prevents redundant actions.
		
		do {
			//clear information from previous parsing attempt, if any:
			tree.clearNodes();
			truthFunction.clearDomain();
			truthFunction.clearSupDomain();
			
			System.out.print("Please enter a string to find out if it's a well-formed formula: ");
			String s = input.next();
			input.nextLine();
			
			Node root = new Node(s,0);
			tree.addNode(root);
			deepestLevel = 0;
			numberOfNodes = 1;
			
			//parses the string:
			try {
				Parser.parse(root,tree);
				notWff = false;
			}
			//if it's NOT a wff:
			catch (Exception e) {
				numberOfNodes = tree.getNumberOfNodes();
				deepestLevel = tree.getDeepestLevel();
				
				System.out.println("\nWhat you entered is NOT a well-formed formula (wff)! Observe the parsing attempt below:");
				
				for(int level = 0; level <= deepestLevel; level++) {
					System.out.print("\nLevel " + level + ": ");
					for(int i = 0; i < numberOfNodes; i++) {
						if (tree.getLevelOfNode(i) == level) {
							System.out.print(tree.getDataOfNode(i) + "   ");
						}
					}
					System.out.println("");
				}
				
				System.out.println("\nPlease check for illegal characters and spaces,"
						+ " and also for missing/extraneous/misplaced propositional variables, parentheses, and characters.");
				notWff = true;
			}
			//if it IS a wff:
			if (!notWff) {
				traversed = false;
				numberOfNodes = tree.getNumberOfNodes();
				deepestLevel = tree.getDeepestLevel();
				int domainSize = 0;
				int supDomainSize = 0;
				
				System.out.println("\nWhat you entered IS a well-formed formula (wff)! Below is its tree:");
				for(int level = 0; level <= deepestLevel; level++) {
					System.out.print("\nLevel " + level + ": ");
					for(int i = 0; i < numberOfNodes; i++) {
						if (tree.getLevelOfNode(i) == level) {
							System.out.print(tree.getDataOfNode(i) + "   ");
						}
					}
					System.out.println("");
				}
				do {
					System.out.print("\n>> MENU <<\n1. Enter another string to parse\n2. Assign truth values to the variables in " +
							s + "\n3. Exit" + "\n>> Select your option (1-3): ");
					try {
						option = input.nextInt();
						input.nextLine();
					}
					catch (Exception e) {
						input.nextLine();
						option = 0;
					}
					System.out.println("");
					if (option == 1) {
						backToMain = true;
					}
					//truth value assignment and evaluation:
					else if (option == 2) {
						boolean exit;
						
						if (!traversed) {
							truthFunction.setDeepestLevel(tree);
							
							//populate the domain and superficial domain:
							for(int i = 0; i < numberOfNodes; i++) {
								if (tree.getPCofNode(i) == 0) {
									truthFunction.addToDomain(tree.getNode(i));
								}
							}
							truthFunction.createSupDomain();
							domainSize = truthFunction.getDomainSize();
							supDomainSize = truthFunction.getSupDomainSize();
						}
						
						for(int i = 0; i < supDomainSize; i++) {
							Node node = truthFunction.getSupDomainNode(i);
							//current node
							
							//prompt user for truth value of current node:
							do {
								System.out.print("Please enter a truth value (T or F) for the variable " + node.getData() + ": ");
								String tv = input.next();
								input.nextLine();
								
								if (tv.equals("T") || tv.equals("F")) {
									if (tv.equals("T")) {
										node.setTV(true);
									}
									else {
										node.setTV(false);
									}
									//assign the user inputed truth value to all nodes n in the domain with
									//n.data = (current node).data
									for(int j = 0; j < domainSize; j++) {
										if ((node.getData()).equals(truthFunction.getDataFromDomainNode(j))) {
											(truthFunction.getDomainNode(j)).setTV(node.getTV());
										}
									}
									exit = true;
								}
								else {
									System.out.print("\nYou did not enter a valid truth value. Try again:\n");
									exit = false;
								}
							}
							while(!exit);
						}
						traversed = true;
						truthFunction.computeTruthValues();
						
						System.out.println("\nUnder the truth assignment you provided, " + s + " is " + 
								(Boolean.toString(root.getTV())).toUpperCase() + "!");
						
					}
					else if (option == 3) {
						backToMain = false;
						input.close();
						System.out.print("Goodbye!");
					}
					else {
						System.out.println("Note the option numbers! Please select one of the options by entering an "
								+ "integer between 1 and 3, inclusive, via the menu:");
					}
				}
				while((option != 1) && (option != 3));
			}
			//if it's NOT a wff:
			else {
				do {
					System.out.print("\n>> MENU <<\n1. Enter another string to parse\n2. Exit" +
							"\n>> Select your option (1-2): ");
					try {
						option = input.nextInt();
						input.nextLine();
					}
					catch (Exception e) {
						input.nextLine();
						option = 0;
					}
					System.out.println("");
					if (option == 1) {
						backToMain = true;
					}
					else if (option == 2) {
						backToMain = false;
						input.close();
						System.out.print("Goodbye!");
					}
					else {
						System.out.println("Note the option numbers! Please select one of the options by entering an "
								+ "integer between 1 and 2, inclusive, via the menu:");
					}
				}
				while((option != 1) && (option != 2));
			}
		}
		while(backToMain);
	}
	
}
