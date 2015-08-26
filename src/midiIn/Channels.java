package suffix;

import java.util.HashMap;
import java.util.Map;

public class Channels {
	int last;
	static Map<Integer, String> list;
	
	public Channels() {
		this.last = 0;
		Channels.list = new HashMap();
		list.put(0, "" + '*');
	}
	
	public int addChannel(String channel) {
		last++;
		list.put(last, channel);
		return last;
	}
	
}
