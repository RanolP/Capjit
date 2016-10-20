package me.ranol.capjit.capturer;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

public class DisplayManager {
	public static DisplayMode getDisplay() {
		return getDevice().getDisplayMode();
	}

	public static GraphicsDevice getDevice() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
	}

	public static Point getMin(Point p1, Point p2) {
		return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
	}

	public static Point getMax(Point p1, Point p2) {
		return new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
	}

	public static Rectangle getSize(Point p1, Point p2) {
		Rectangle rect = new Rectangle(p1);
		rect.add(p2);
		return rect;
	}
}
