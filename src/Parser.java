
public class Parser {
	
	public static void parse(Node n, Tree t) throws Exception{
		String s = n.getData();
		int level = n.getLevel();
		int length = s.length();
		String alpha;
		//alpha is the x in s = ~...~x if s begins with ~, or it's x1 = ~...~p'...' or x2 = ~...~(...) or x3 = p'...'
		//or x4 = (...) if s begins with ( followed by x1 or x2 or x3 or x4.
		String beta;
		//beta is the segment following alpha and the principal binary connective (if there is one), excluding the last ')'.
		
		if (isPV(s)) {
			n.setPC();
		}
		else {
			if (s.charAt(0) == '~') {
				if (length >= 2) {
					alpha = s.substring(1,length);
					t.addNode(new Node(alpha, level+1));
					n.setLeftChild(t.getLastNode());
					parse(t.getLastNode(), t);
				}
				else {
					throw new Exception();
				}
			}
			else if (s.charAt(0) == '(') {
				if (length >= 2) {
					alpha = getAlpha(s);
					
					if (!(alpha.equals(""))) {
						int alphaLength = alpha.length();
						int newIndex = connectiveEnd(s, 1 + alphaLength);
						
						if (newIndex != -1 && newIndex < s.length()-1 && s.charAt(s.length()-1) == ')') {
							t.addNode(new Node(alpha, level+1));
							n.setLeftChild(t.getLastNode());
							parse(t.getLastNode(), t);
							
							beta = s.substring(newIndex, s.length()-1);
							t.addNode(new Node(beta, level+1));
							n.setRightChild(t.getLastNode());
							parse(t.getLastNode(), t);
						}
						else {
							throw new Exception();
						}
					}
					else {
						throw new Exception();
					}
				}
				else {
					throw new Exception();
				}
			}
			else {
				throw new Exception();
			}
		}
	}
	
	//returns alpha, or an empty string, which indicates that s = (~...~ or s = ((... or s = ()..., where
	//(... or )... is such that #leftParentheses != #rightParentheses.
	public static String getAlpha(String s) {
		int length = s.length();
		int index = 1;
		int balance = 0;
		//the balance between left and right parentheses
		
		while(s.charAt(index) == '~') {
			index = index + 1;
			if (index >= length) {
				return "";
			}
		}
		//if program reaches this line, guaranteed that there's a character at index.
		
		do {
			if (index < length) {
				if (s.charAt(index) == '(') {
					balance = balance + 1;
				}
				else if (s.charAt(index) == ')') {
					balance = balance - 1;
				}
				else if (Character.isLetter(s.charAt(index))) {
					int i = index + 1;
					if (i < length) {
						while(s.charAt(i) == '\'') {
							index = index + 1;
							i = i + 1;
							if (i >= length) {
								break;
							}
						}
					}
				}
				index = index + 1;
			}
			else {
				return "";
				}
			}
		while (balance != 0);
		
	return s.substring(1, index);
	//if we reach the above return statement, the index is, at a minimum, 2.
	//note that s.substring(i,j) returns the part of s starting at index i and ending at index j-1, inclusive.
	}
	
	//receives the index where the connective should begin, and 
	//returns the index of the character after the connective, if any; otherwise, returns -1:
	public static int connectiveEnd(String s, int index) {
		int length = s.length();
		
		if (index < length) {
			if (s.charAt(index) == '&') {
				return index + 1;
			}
			if ((length - index) >= 2) {
				if ((s.charAt(index) == '|') && s.charAt(index+1) == '|') {
					return index + 2;
				}
				else if ((s.charAt(index) == '=') && s.charAt(index+1) == '>') {
					return index + 2;
				}
				if ((length - index) >= 3) {
					if ((s.charAt(index) == '<') && (s.charAt(index+1) == '=') && s.charAt(index+2) == '>') {
						return index + 3;
					}
				}
			}
		}
		return -1;
	}
	
	//checks whether a string is a propositional variable.
	public static boolean isPV(String s) {
		if (Character.isLetter(s.charAt(0))) {
			if ((s.length() == 1) || (isSoA(s.substring(1)))) {
				return true;
			}
		}
		return false;
	}
	
	//checks whether a string is a string of apostrophes.
	public static boolean isSoA(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '\'') {
				return false;
			}
		}
		return true;
	}

}
