package suffix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sound.midi.*;

public class TrackAnalyser {
	List<MidiEvent>[] channels;
	List<Integer>[] flatChannel;
	Set<Integer> chord;
	Set<Integer> modChord;
	ChordTable table;
	private final static int CHORD_RANGE = 1;
	
	public TrackAnalyser(ChordTable table) {
		this.channels = new ArrayList[16];
		for(int i = 0; i < 16; i++) {
			channels[i] = new ArrayList();
		}
		this.flatChannel = new ArrayList[16];
		for(int i = 0; i < 16; i++) {
			flatChannel[i] = new ArrayList();
		}
		this.chord = new HashSet();
		this.modChord = new HashSet();
		this.table = table;
	}
	
	/**
	 * Builds a list of midi events of each channels. 
	 * @param file
	 * @throws InvalidMidiDataException
	 * @throws IOException
	 */
	public void splitByChannel(File file) throws InvalidMidiDataException, IOException {
		Sequence seq = MidiSystem.getSequence(file);
		for(Track track: seq.getTracks()) {
			for(int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();
				if(message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					if(sm.getCommand() == ShortMessage.NOTE_ON) {
						channels[sm.getChannel()].add(new MidiEvent(sm,event.getTick()));
					}
				}
			}		
		}
		analyseChannel();
		printChannels();
	}
	
	public void analyseChannel() {
		for(int c = 0; c < channels.length; c++) {
			List<MidiEvent> channel = channels[c];
			int eventNo = 0;
			while(eventNo < channel.size() - 2) {
				if(channel.get(eventNo).getMessage() instanceof ShortMessage){
					ShortMessage sm = (ShortMessage) channel.get(eventNo).getMessage();
					if(sm.getCommand() == ShortMessage.NOTE_ON) {
						if(channel.get(eventNo + 1).getTick() - channel.get(eventNo).getTick() > CHORD_RANGE) {
							flatChannel[c].add(sm.getData1());
							eventNo++;
						} else {
							chord.add(sm.getData1());
							while(channel.get(eventNo + 1).getTick() - channel.get(eventNo).getTick() <= CHORD_RANGE && eventNo < channel.size() - 2) {
								chord.add(((ShortMessage) channel.get(eventNo + 1).getMessage()).getData1());
								eventNo++;
							}
							if(chord.size() == 2) {
								flatChannel[c].add(Collections.min(chord));
							} else {
								for(int i: chord) {
									modChord.add(i % 12);
								}
								if(modChord.size() < 3) {
									flatChannel[c].add(Collections.min(modChord));
								}
								flatChannel[c].add(table.getRoute(modChord));
								modChord.clear();
							}
							chord.clear();
							eventNo++;
						}
					}
				}
			}
		}
	}
	
	public List<StringBuilder> turnIntToString() {
		List<StringBuilder> result = new ArrayList();
		for(List<Integer> channel: flatChannel) {
			if(channel.size() != 0) {
				int prevInt = channel.get(0) % 12;
				StringBuilder line = new StringBuilder();
				line.append("*");
				for(int i = 1; i < channel.size(); i++) {
					int thisInt = channel.get(i) % 12;
					if(thisInt < prevInt) {
						line.append("D");
					} else if (thisInt > prevInt) {
						line.append("U");
					} else {
						line.append("R");
					}
					prevInt = thisInt;
				}
				result.add(line);
			}
		}
		return result;
	}
	
	public void printChannels() {
		for(List<Integer> chan: flatChannel) {
			for(int i: chan) {
				System.out.print(i + ",");
			}
			System.out.println();
		}
	}
}