package me.ranol.capjit.components;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FolderFilter extends FileFilter {
	private static FolderFilter instance;

	public static FolderFilter getFilter() {
		if (instance == null)
			synchronized (FolderFilter.class) {
				instance = new FolderFilter();
			}
		return instance;
	}

	@Override
	public boolean accept(File f) {
		return f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Folder Accept Only.";
	}
}
