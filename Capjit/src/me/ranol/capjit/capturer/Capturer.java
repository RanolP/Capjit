package me.ranol.capjit.capturer;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import me.ranol.capjit.ImageViewer;
import me.ranol.capjit.OptionFrame;
import me.ranol.capjit.SizeScreen;

public class Capturer {
	public static BufferedImage capture(Point p1, Point p2) {
		return capture(DisplayManager.getSize(p1, p2));
	}

	public static BufferedImage capture(int x, int y, int w, int h) {
		return capture(new Rectangle(x, y, w, h));
	}

	public static BufferedImage capture(Rectangle rect) {
		try {
			Robot r = new Robot(DisplayManager.getDevice());
			return r.createScreenCapture(rect);
		} catch (Exception e) {
			return null;
		}
	}

	public static BufferedImage captureFull() {
		DisplayMode dm = DisplayManager.getDisplay();
		return capture(0, 0, dm.getWidth(), dm.getHeight());
	}

	public static Dimension getSize() {
		DisplayMode dm = DisplayManager.getDisplay();
		return new Dimension(dm.getWidth(), dm.getHeight());
	}

	public static void captureWithSelect() {
		SizeScreen ss = new SizeScreen(null);
		ss.getEnd().add((s, e) -> {
			BufferedImage bi = Capturer.capture(s, e);
			if (OptionFrame.save())
				write(bi);
			if (OptionFrame.image())
				viewer(bi);
		});
	}

	public static void captureWithAll() {
		BufferedImage bi = Capturer.captureFull();
		if (OptionFrame.save())
			write(bi);
		if (OptionFrame.image())
			viewer(bi);
	}

	static void viewer(BufferedImage img) {
		ImageViewer iv = new ImageViewer(img);
		iv.setVisible(true);
		iv.setResizable(false);
	}

	static void write(BufferedImage bi) {
		try {
			File folder = new File(OptionFrame.getDir());
			if (!folder.exists())
				folder.mkdirs();
			String name = OptionFrame.formatNow();
			int i = 1;
			File target = new File(folder, name + " (" + i + ").png");
			while (target.exists())
				target = new File(folder, name.trim() + " (" + i++ + ").png");
			target.createNewFile();
			ImageIO.write(bi, "png", target);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
