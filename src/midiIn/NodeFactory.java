package midiIn;

public class NodeFactory {
	public Node getNode(String text) {
		return new Node(text);
	}
	public Node getNode(char c) {
		return new Node(c);
	}
}
