package me.ranol.capjit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SimpleOptionFile {
	HashMap<String, String> map = new HashMap<>();
	List<String> values = new ArrayList<>();
	File file;

	public SimpleOptionFile(File file) {
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		this.file = file;
		readToEnd(file);
	}

	public SimpleOptionFile(File folder, String str) {
		this(new File(folder, str));
	}

	public String getValue(String key) {
		if (map.containsKey(key))
			return map.get(key);
		return "";
	}

	public void setValue(String key, String value) {
		map.put(key, value);
		save();
	}

	public void save() {
		LinkedList<String> data = new LinkedList<>();
		LinkedList<String> uncheckedKey = new LinkedList<>(map.keySet());
		for (String s : values) {
			if (s.contains("=") && !s.startsWith("#") && !s.startsWith("//")) {
				String key = s.substring(0, s.indexOf("="));
				if (map.containsKey(key)) {
					data.add(key + "=" + map.get(key));
					uncheckedKey.remove(key);
				}
			} else
				data.add(s);
		}
		for (String s : uncheckedKey)
			data.add(s + "=" + map.get(s));
		Collections.sort(data);
		FileUtils.saveAll(file, data);
	}

	private void readToEnd(File file) {
		values = FileUtils.readAll(file);
		for (String s : values) {
			if (s.startsWith("#") || s.startsWith("//"))
				continue;
			int index = s.indexOf("=");
			if (index == -1)
				continue;
			String v = s.substring(index);
			if (index != -1)
				map.put(s.substring(0, index),
						v.startsWith("=") ? v.substring(1) : v);
		}
	}
}
