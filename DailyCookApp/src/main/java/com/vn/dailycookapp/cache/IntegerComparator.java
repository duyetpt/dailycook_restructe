package com.vn.dailycookapp.cache;

import java.util.Comparator;
import java.util.Map.Entry;

public class IntegerComparator implements Comparator<Entry<String, Integer>> {
	
	@Override
	public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
		return entry2.getValue() - entry1.getValue();
	}
}
