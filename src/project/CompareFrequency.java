package project;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class CompareFrequency implements Comparator<Map.Entry<String, Integer>>{

	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		return o2.getValue() - o1.getValue();
	}
}
