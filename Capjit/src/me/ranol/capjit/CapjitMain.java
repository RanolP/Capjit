package me.ranol.capjit;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;

public class CapjitMain {
	public static void main(String[] args) {
		new CapjitFrame();
		OptionFrame.construct();
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage()
				.getName());
		logger.setLevel(Level.OFF);
		KeyHook.register();
	}
}
