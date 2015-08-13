package midiIn;

import java.util.LinkedList;
import java.util.List;

public class Node {
	String text;
	List<Node> suffixes;
	Node link;
	
	public Node(String text) {
		this.text = text;
		suffixes = new LinkedList();
	}
	
	public Node(char text) {
		this.text = "" + text;
		suffixes = new LinkedList();
	}
	
	public void addChar(char c) {
		if(suffixes.isEmpty()) {
			text = text + c;
		} else {
			for(Node node: suffixes) {
				node.addChar(c);
			}
		}
	}
	
	public void setLink(Node link) {
		this.link = link;
	}
	
	public void addSuffix(Node suffix) {
		suffixes.add(suffix);
	}
	
	public void printNode(int n) {
		System.out.println(text);
		if(link != null) {System.out.println("Link: " + link.text);}
		if(suffixes.isEmpty()) {return;}
		for(Node node: suffixes) {
			printSpace(n);
			node.printNode(n + 1);
		}
	}
	
	private void printSpace(int n) {
		if(n == 0) {return;}
		System.out.print("  ");
		printSpace(n - 1);
	}
}
