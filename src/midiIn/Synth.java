package midiIn;

import javax.sound.midi.*;

public class Synth {
	public static void main(String[] args) throws MidiUnavailableException {
		Synth instance = new Synth();
		instance.synth();
	}
	public void synth() throws MidiUnavailableException{
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();
		Receiver rec = synth.getReceiver();
		Transmitter transmitter = MidiSystem.getTransmitter();
		transmitter.setReceiver(rec);
		//synth.getTransmitter().
	}
}
