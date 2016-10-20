package me.ranol.capjit.components;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyPressListener {
	public void keyPressed(KeyEvent e);
}
