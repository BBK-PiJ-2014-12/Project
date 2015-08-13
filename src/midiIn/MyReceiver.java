package midiIn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.synthbot.jasiohost.*;

import java.util.List;

import javax.sound.midi.*;
import javax.swing.JComboBox;

public class MyReceiver implements Receiver, AsioDriverListener{
	long prevNote = 0;
	List<Integer> notes = new ArrayList();
	private static final long serialVersionUID = 1L;
	  
	private Set<AsioChannel> activeChannels;
	private int sampleIndex;
	private int bufferSize;
	private double sampleRate;
	private float[] output;
	Object[] drivers = AsioDriver.getDriverNames().toArray();
	AsioDriver asioDriver = AsioDriver.getDriver(drivers[0].toString());
	
	public MyReceiver() {
		asioDriver.addAsioDriverListener(this);
		activeChannels = new HashSet<AsioChannel>();
        activeChannels.add(asioDriver.getChannelOutput(0));
        activeChannels.add(asioDriver.getChannelOutput(1));
        sampleIndex = 0;
        bufferSize = asioDriver.getBufferPreferredSize();
        sampleRate = asioDriver.getSampleRate();
        output = new float[bufferSize];
        asioDriver.createBuffers(activeChannels);
        
	}
	
	@Override
	public void close() {}

	@Override
	public void send(MidiMessage message, long time) {
		if(message instanceof ShortMessage) {
			ShortMessage sm = (ShortMessage) message;
			if(sm.getCommand() == ShortMessage.NOTE_ON) {
				//asioDriver.start();
				if(time/1000 - prevNote > 50) {
					//System.out.print("Lowest note: " + lowest(notes) + " " + sm.getCommand());
					System.out.println();
					//notes.clear();
					//asioDriver.start();
					
				}
				System.out.print(sm.getData1() + "|");	
				//notes.add(sm.getData1());
				prevNote = time/1000;
			}	
			if(sm.getCommand() == ShortMessage.NOTE_OFF) {
				//asioDriver.stop();
			}
		}
	}
	public int lowest(List<Integer> notes) {
		int lowest = Integer.MAX_VALUE;
		for(Integer i: notes) {
			if(i < lowest) lowest = i;
		}
		return lowest;
	}

	@Override
	public void sampleRateDidChange(double sampleRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resyncRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bufferSizeChanged(int bufferSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void latenciesChanged(int inputLatency, int outputLatency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bufferSwitch(long sampleTime, long samplePosition, Set<AsioChannel> activeChannels) {
		
		//System.out.println(System.currentTimeMillis());
	    for (int i = 0; i < bufferSize; i++, sampleIndex++) {
	        output[i] = (float) Math.sin(2 * Math.PI * sampleIndex * 640.0 / sampleRate);
	      }
	      for (AsioChannel channelInfo : activeChannels) {
	        channelInfo.write(output);
	      }
		
	}
}
