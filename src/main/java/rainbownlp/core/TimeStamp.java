package rainbownlp.core;

import java.util.Date;
import java.util.HashMap;

public class TimeStamp {
	static HashMap<String, Date> blockTimes = new HashMap<String, Date>();
	public static void setStart(String blockName) {
		blockTimes.put(blockName, new Date());
	}

	public static void