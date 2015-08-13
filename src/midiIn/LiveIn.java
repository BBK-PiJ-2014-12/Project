package midiIn;

import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.Info;

public class LiveIn {
	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
		LiveIn li = new LiveIn();
		li.launch();
	}
	
	public void launch() throws MidiUnavailableException, InvalidMidiDataException {
		Transmitter transmitter = MidiSystem.getTransmitter();
		transmitter.setReceiver(new MyReceiver());
	}
	
	public void printDevices() {
		Info[] info = MidiSystem.getMidiDeviceInfo();
		for(Info device: info) {
			System.out.println(device.getVendor() + " " + device.getName() + " " + device.getVersion());
		}
	}
}
