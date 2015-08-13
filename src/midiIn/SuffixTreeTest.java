package midiIn;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class SuffixTreeTest {
	public static void main(String[] args) {
		NodeFactory factory = new NodeFactory();
		SuffixTree stb = new SuffixTree(factory);
		stb.analyseString("abcabxabcd");;
		stb.root.printNode(0);
	}
}
