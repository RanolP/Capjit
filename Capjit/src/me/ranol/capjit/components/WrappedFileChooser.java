package me.ranol.capjit.components;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class WrappedFileChooser extends JFileChooser {
	private static final long serialVersionUID = -5195967800963954660L;

	public WrappedFileChooser filter(FileFilter filter) {
		setFileFilter(filter);
		return this;
	}

	public WrappedFileChooser title(String title) {
		setDialogTitle(title);
		return this;
	}

	public WrappedFileChooser visible(Component parent, String title) {
		showDialog(parent, title);
		return this;
	}

	public WrappedFileChooser select(File file) {
		setSelectedFile(file);
		return this;
	}

	public WrappedFileChooser select(String file) {
		return select(new File(file));
	}
}
