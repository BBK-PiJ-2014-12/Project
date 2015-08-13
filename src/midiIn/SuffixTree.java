package midiIn;

import java.util.Optional;

public class SuffixTree {
	Node root;
	Node nextNode;
	Node activeNode;
	Node activeEdge;
	Node prevNode;
	int activeLength;
	char activePoint;
	int remainder;
	String toInsert;
	NodeFactory factory;
	boolean completeInsert;
	
	public SuffixTree(NodeFactory factory) {
		this.root = new Node("");
		this.activeNode = root;
		this.prevNode = null;
		this.activeEdge = null;
		this.activeLength = 0;
		this.remainder = 1;
		this.toInsert = "";
		this.factory = factory;
		this.completeInsert = false;
	}
	
	public void analyseString(String word) {
		for(int i = 0; i < word.length(); i++) {
			addNextChar(word.charAt(i));
		}
	}

	private void addNextChar(char c) {
		toInsert = toInsert + c;
		for(Node node: root.suffixes) {
			node.addChar(c);
		}
		if(activeEdge != null) {
			activeLength++;
			if(activeEdge.text.charAt(activeLength - 1) != c) {
				insertNode();
			} else if (activeEdge.text.length() == toInsert.length()){
				activeLength = activeLength - activeEdge.text.length();
				activeNode = activeEdge; 
				activeEdge = null;
			}
		} else {
			for(Node node: activeNode.suffixes) {
				if(node.text.charAt(0) == c) {
					activeEdge = node; 
					activeLength++;
				}
			}	
			if(activeEdge == null) {
				insertNode();
			} else if (activeEdge.text.length() == toInsert.length()){
				activeLength = activeLength - activeEdge.text.length();
				activeNode = activeEdge; 
				activeEdge = null;
			}
		}
	}
	
	private void insertNode() {
		insertHelper(0, activeLength);
		activeNode = root;
		if(completeInsert){
			for (int j = 0; j < toInsert.length() - 1; j++) {
				int length = toInsert.length();
				char c = toInsert.charAt(j);
				if (nextNode(c).isPresent()) {
					Node tempNode = nextNode(c).get();
	
					tempNode.suffixes.add(new Node(tempNode.text.substring(length - 1)));
					tempNode.suffixes.add(new Node(toInsert.charAt(toInsert.length() - 1)));
					tempNode.text = tempNode.text.substring(0, length - 1);
					length--;
					
					if (prevNode != null) {
						prevNode.link = tempNode;
					}
					prevNode = tempNode;
				}
			}
			completeInsert = false;
		}
		clearUp();
	}
	
	public void insertHelper(int i, int length) {
		if(length == 0) return;
		for (int j = i; j < toInsert.length() - 1; j++) {
			char c = toInsert.charAt(j);
			if (nextNode(c).isPresent()) {
				Node tempNode = nextNode(c).get();

				tempNode.suffixes.add(new Node(tempNode.text.substring(length - 1)));
				tempNode.suffixes.add(new Node(toInsert.charAt(toInsert.length() - 1)));
				tempNode.text = tempNode.text.substring(0, length - 1);
				length--;

				if (prevNode != null) {
					prevNode.link = tempNode;
				}
				prevNode = tempNode;
			}
		}
		toInsert = toInsert.substring(1);
		if(activeNode.link != null) {
			completeInsert = true;
			activeNode = activeNode.link;
			insertHelper(i, activeLength); 
		} 
	}
	
	public void clearUp() {
		if(toInsert != "") root.suffixes.add(new Node(toInsert.charAt(toInsert.length() - 1)));
		activeEdge = null;
		activeNode = root;
		activeLength = 0;
		toInsert = "";
		prevNode = null;
	}
	public Optional<Node> nextNode(char c) {
		return activeNode.suffixes.stream()
				.filter(node -> node.text.charAt(0) == c)
				.findFirst();
	}
}
