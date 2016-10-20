package me.ranol.capjit.components;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyTypeListener {
	public abstract void keyTyped(KeyEvent e);
}
