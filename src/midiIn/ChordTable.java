package midiIn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChordTable {
	Map<Set<Integer>,Integer> table;
	
	/**
	 * Connects to the chord table text file and reads in each lines.
	 * @throws FileNotFoundException
	 */
	public ChordTable() throws FileNotFoundException {
		table = new HashMap();
		File file = new File("chordtable.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.lines().forEach(line -> addToTable(line));
	}
	
	/**
	 * Creates an internal chord table by adding each line to a map where
	 * the set of notes creating a chord is the key and the route is the value. 
	 * @param line
	 */
	private void addToTable(String line) {
		String[] cut = line.split("-");
		int note = Integer.parseInt(cut[1]);
		Set<Integer> chord = new HashSet();
		for(String number: cut[0].split(",")) {
			chord.add(Integer.parseInt(number));
		}
		table.put(chord, note);
	}
	
	/**
	 * Looks up the route of the chord.
	 * If the chord is not found in the table, returns 13.
	 * @param chord
	 * @return route note.
	 */
	public int getRoute(Set<Integer> chord) {
		if(table.get(chord) == null) return 13;
		return table.get(chord);
	}
	
	/**
	 * Returns the musical notation of a note. 
	 * @param note
	 * @return Notes musical notation.
	 */
	public String translate(int note) {
		String sNote;
		switch (note) {
			case 0: sNote = "C"; break;
			case 1: sNote = "C#"; break;
			case 2: sNote = "D"; break;
			case 3: sNote = "D#"; break;
			case 4: sNote = "E"; break;
			case 5: sNote = "F"; break;
			case 6: sNote = "F#"; break;
			case 7: sNote = "G"; break;
			case 8: sNote = "G#"; break;
			case 9: sNote = "A"; break;
			case 10: sNote = "A#"; break;
			case 11: sNote = "B"; break;
			default: sNote = "X";
		}
		return sNote;
	}
}
