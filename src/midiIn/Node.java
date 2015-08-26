package suffix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Node {
	End end;
	int start;
	int length;
	int referenceChannel;
	Map<Integer, Integer> channels;
	Map<Character, Node> edges;
	Node parent;
	Node link;
	
	public Node(int start, End end, Node parent, int referenceChannel) {
		this.parent = parent;
		this.edges = new HashMap();
		this.channels = new HashMap();
		this.referenceChannel = referenceChannel;
		channels.put(referenceChannel, start);
		this.end = end; 
		this.start = start;
		this.length = end.end - start;
	}
	
	public void passingNode(int referenceChannel, int start) {
		channels.put(referenceChannel, start);
	}
	
	public void printNode() {
		if(start < end.end) {
			System.out.print("[<" + Channels.list.get(referenceChannel).substring(start, end.end) + ">");
			if(parent != null) {
				System.out.print(" P:" + parent.getWord());
			}
			if(link != null) {
				System.out.print(" L:" + link.getWord());
			}
//			System.out.print(" C:");
//			for(int i: channels.keySet()) {
//				System.out.print(i + " ");
//			}
			if(!edges.isEmpty()) {
				for(Node n: edges.values()) {
					n.printNode();
				}
			}
			System.out.print("]");
		}
	}
	
	public String getWord() {
		int start = channels.get(referenceChannel);
		return Channels.list.get(referenceChannel).substring(start, end.end);
	}
	
	public int getStartPos(int refenceChannel) {
		return channels.get(referenceChannel);
	}
	
	public void setStartPos(int referenceChannel, int start) {
		channels.put(referenceChannel, start);
	}
	
	public void updateStartPos(int length) { 
		start = start + length;
		for(int c: channels.keySet()) {
			channels.put(c, channels.get(c) + length);
		}
	}
}
