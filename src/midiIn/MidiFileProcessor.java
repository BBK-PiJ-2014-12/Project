package suffix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;

public class MidiFileProcessor {
	
	public static void main(String[] args) throws InvalidMidiDataException, IOException {
		MidiFileProcessor test = new MidiFileProcessor();
		test.launch();
	}
	public void launch() throws InvalidMidiDataException, IOException {
		ChordTable ct = new ChordTable();
//		NodeFactory factory = new NodeFactory();
		SuffixTree tree = new SuffixTree();
		
		String title = "Hard To Handle";
		File song = new File(title + ".mid");
		TrackAnalyser analyser = new TrackAnalyser(ct);
		analyser.splitByChannel(song);
		
		List<StringBuilder> lines = analyser.turnIntToString();
		for(int i = 1; i <lines.size(); i++) {
			System.out.println(lines.get(i).toString());
			if(!lines.get(i).toString().isEmpty()) tree.analyseWord(lines.get(i).toString().substring(1),i + 1);
		}
	}
}
