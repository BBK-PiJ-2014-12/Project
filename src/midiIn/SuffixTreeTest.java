package suffix;

public class SuffixTreeTest {
	public static void main(String[] args) {
		SuffixTreeTest main = new SuffixTreeTest();
		main.launch();
	}
	
	public void launch() {
		SuffixTree tree = new SuffixTree();
		tree.analyseWord("abcabxabcd", 2);
		tree.analyseWord("ccbaxdcbb$", 8);
		tree.analyseWord("ccbaxdcbaxtb$", 3);
		tree.analyseWord("bxcccabxccda$", 4);
		tree.analyseWord("abcfe", 9);
		tree.root.printNode();
	}
}