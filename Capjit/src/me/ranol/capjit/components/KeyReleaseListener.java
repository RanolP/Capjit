package me.ranol.capjit.components;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyReleaseListener {
	public void keyReleased(KeyEvent e);
}
