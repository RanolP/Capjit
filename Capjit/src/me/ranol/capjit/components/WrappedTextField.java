package me.ranol.capjit.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

public class WrappedTextField extends JTextField {
	List<KeyTypeListener> type = new ArrayList<>();
	List<KeyPressListener> press = new ArrayList<>();
	List<KeyReleaseListener> release = new ArrayList<>();
	private static final long serialVersionUID = -6639397724576510244L;
	
	{
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				type.forEach(t -> t.keyTyped(e));
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				release.forEach(t -> t.keyReleased(e));
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				press.forEach(t -> t.keyPressed(e));
			}
		});
	}
	
	public void addPressListener(KeyPressListener kpl){
		press.add(kpl);
	}
	public void addReleaseListener(KeyReleaseListener kpl){
		release.add(kpl);
	}
	public void addTypeListener(KeyTypeListener kpl){
		type.add(kpl);
	}
}
