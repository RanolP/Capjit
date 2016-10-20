package me.ranol.capjit.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class PictureBox extends Canvas {
	private static final long serialVersionUID = 1L;
	BufferedImage buffer = null;

	public PictureBox() {
		setBackground(Color.GRAY);
	}

	public boolean setImage(BufferedImage img) {
		if (img != null) {
			buffer = img;
			repaint();
		} else {
			buffer = null;
			repaint();
		}
		return true;
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		if (buffer != null) {
			g.drawImage(toImage(buffer), 0, 0, getWidth(), getHeight(), this);
		} else {
			g.drawImage(null, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public Image toImage(BufferedImage img) {
		return Toolkit.getDefaultToolkit().createImage(img.getSource());
	}
}