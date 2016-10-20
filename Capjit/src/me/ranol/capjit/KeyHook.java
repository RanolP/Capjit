package me.ranol.capjit;

import me.ranol.capjit.capturer.Capturer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyHook implements NativeKeyListener {
	private static KeyHook instance;
	private final int ALT = 1;
	private final int SHIFT = 3;
	private final int CTRL = 7;
	private int state = 0;

	public static void register() {
		try {
			GlobalScreen.addNativeKeyListener(getInstance());
			GlobalScreen.registerNativeHook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyHook getInstance() {
		if (instance == null)
			synchronized (KeyHook.class) {
				instance = new KeyHook();
			}
		return instance;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		pressCheck(e);
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		switch (e.getKeyCode()) {
		// LControl
		// RControl
		case 29:
		case 3613:
			state &= ~CTRL;
			break;
		case 42:
		case 54:
			state &= ~SHIFT;
			break;
		case 56:
		case 3640:
			state &= ~ALT;
			break;
		default:
			break;
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		pressCheck(e);
	}

	public void pressCheck(NativeKeyEvent e) {
		switch (e.getKeyCode()) {
		// LControl
		// RControl
		case 29:
		case 3613:
			state |= CTRL;
			break;
		// LShift
		// RShift
		case 42:
		case 54:
			state |= SHIFT;
			break;
		case 56:
		case 3640:
			state |= ALT;
			break;
		default:
			break;
		}
		char c = e.getKeyChar();
		KeyData kd = OptionFrame.getSelectCapture();
		if (check(kd) && kd.getKeyChar() == c) {
			Capturer.captureWithSelect();
		}
		kd = OptionFrame.getAllCapture();
		if (check(kd) && kd.getKeyChar() == c) {
			Capturer.captureWithAll();
		}
	}

	boolean check(KeyData kd) {
		return (kd.getAltRequired() ? check(ALT) : true)
				&& (kd.getShiftRequired() ? check(SHIFT) : true)
				&& (kd.getControlRequired() ? check(CTRL) : true);
	}

	boolean check(int i) {
		return (state & i) == i;
	}
}
