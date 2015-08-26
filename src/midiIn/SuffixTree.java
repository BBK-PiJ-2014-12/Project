package suffix;

import java.util.HashMap;
import java.util.Map.Entry;

public class SuffixTree {
	End end;
	String word;
	Node root;
	Node activeNode;
	Node prevNode;
	int activeEdge;
	int activeLength;
	int remainder;
	Channels channels;
	
	public SuffixTree () {
		this.root = new Node(0, new End(1) , null, 0);
		this.activeNode = root;
		this.activeEdge = -1;
		this.activeLength = 0;
		this.remainder = 0;
		this.channels = new Channels();
	}
	
	public void analyseWord(String word, int channelID) { 
		this.word = word;
		this.end = new End(0);
		channels.list.put(channelID, word);
		
		for(int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			end.end++;
			remainder++;

			if(activeLength == 0) {
				if(activeNode.edges.containsKey(c)) {
					if(activeNode.edges.get(c).channels.containsKey(channelID)) {
						activeEdge = activeNode.edges.get(c).channels.get(channelID);
					} else {
						activeEdge = i;
						activeNode.edges.get(c).channels.put(channelID, i);
					}
					activeLength++; 
				} else {
					activeNode.edges.put(c, new Node(i, end, activeNode, channelID));
					remainder--;
				}
			} else if(activeLength == getActiveEdge().end.end - getActiveEdge().start){
				 
					activeNode = getActiveEdge();
					if(activeNode.edges.containsKey(c)) {
						if(activeNode.edges.get(c).channels.containsKey(channelID)) {
							activeEdge = activeNode.edges.get(c).channels.get(channelID);
							activeLength = 1;
						} else {
							activeEdge = i;
							activeNode.channels.put(channelID, i);
							activeLength = 1;
						}
					} else {
						while(remainder > 1) {
							activeNode.edges.put(c, new Node(i, end, activeNode, channelID));
							remainder--;
							if(activeNode.parent == null || activeNode.parent.link == null) {
								activeNode = root;
								activeEdge++;
								activeLength--;
							} else {
								activeNode = activeNode.parent.link;
							}
						}
						if(!root.edges.containsKey(c)) {
							root.edges.put(c, new Node(i, end, activeNode, channelID));
							remainder--;
						} else {
							if(activeNode.edges.get(c).channels.containsKey(channelID)) {
								activeEdge = activeNode.edges.get(c).channels.get(channelID);
							} else {
								activeEdge = i;
								activeNode.edges.get(c).channels.put(channelID, i);
							}
							activeLength = 1;
						}
					}
				} else {
					if(getActiveEdge().getWord().charAt(activeLength) == c) {
						activeLength++; 
					} else {
						insertAll(i, c, channelID);
				}
			}
		}
		reset();
		this.remainder = 0;
		this.word = "";
	}
	
	public String getChannel() {
		return channels.list.get(activeNode.referenceChannel);
	}
	
	public Node getActiveEdge()	{
   		return activeNode.edges.get(word.charAt(activeEdge));
	}
	
	private void reset() {
		this.activeNode = root;
		this.activeEdge = -1;
		this.activeLength = 0;
	}
	
	private void insertAll(int i, char c, int channelID) {
		int internalStart = getActiveEdge().getStartPos(channelID);
		End internalEnd = new End(internalStart + activeLength);
		int externalStart = i;
		End externalEnd = end;
		
		while(remainder > 1) {
			activeNode = activeNode.edges.get(word.charAt(activeEdge));
			if(activeNode.end.end - activeNode.start > 1) {
				Node internal = new Node(activeNode.start, new End(activeNode.start + activeLength), activeNode.parent, activeNode.referenceChannel);
				if(prevNode != null) {
					prevNode.link = internal;
				}
				Node newExternal = new Node(externalStart, externalEnd, internal, channelID);

				internal.channels = new HashMap(activeNode.channels);
				internal.link = root;
				
				internal.edges.put(c, newExternal);
				activeNode.parent.edges.replace(getChannel().charAt(activeNode.getStartPos(channelID)), internal);
				activeNode.updateStartPos(activeLength);
				activeNode.parent = internal;
				internal.edges.put(activeNode.getWord().charAt(0), activeNode);

				prevNode = internal;
				remainder--;
				
				if(internal.parent.link == null) {
					activeNode = root;
					activeEdge++;
					activeLength--;
				} else {
					activeNode = internal.parent.link;
				}
			} else {
				Node newExternal = new Node(externalStart, externalEnd, activeNode, channelID);
				activeNode.edges.put(c, newExternal);
				
				if(prevNode != null) {
					prevNode.link = activeNode;
				}
				if(activeNode.parent.link == null) {
					activeNode = root;
					activeEdge++;
					activeLength--;
				} else {
					activeNode = activeNode.parent.link;
				}
				remainder--;
			}
		}
		if(!root.edges.containsKey(c)) {
			root.edges.put(c, new Node(i, end, activeNode, channelID));
			remainder--;
		} else {
			if(activeNode.edges.get(c).channels.containsKey(channelID)) {
				activeEdge = activeNode.edges.get(c).channels.get(channelID);
			} else {
				activeEdge = i;
				activeNode.edges.get(c).channels.put(channelID, i);
			}
			activeLength = 1;
		}
		prevNode = null;
	}
}
