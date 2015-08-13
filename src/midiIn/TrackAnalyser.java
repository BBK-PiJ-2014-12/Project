package midiIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;

public class TrackAnalyser {
	
	public static void main(String[] args) throws InvalidMidiDataException, IOException {
		TrackAnalyser test = new TrackAnalyser();
		test.launch();
	}
	public void launch() throws InvalidMidiDataException, IOException {
		ChordTable ct = new ChordTable();
		NodeFactory factory = new NodeFactory();
		SuffixTree tree = new SuffixTree(factory);
		
		String title = "Hard To Handle";
		File song = new File(title + ".mid");
		MidiFileProcessor mfp = new MidiFileProcessor(ct);
		mfp.splitByChannel(song);
		
		for(StringBuilder line: mfp.turnIntToString()) {
			System.out.println(line.toString());
			tree.analyseString(line.toString().substring(1));
		}
	}
}
