package me.ranol.capjit.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class RgbColorPicker extends JPanel {
	private static final long serialVersionUID = 2558125984568023774L;
	private JSlider red = new JSlider(0, 255);
	private JSlider green = new JSlider(0, 255);
	private JSlider blue = new JSlider(0, 255);
	Color result = Color.BLACK;
	JButton confirm = new JButton("설정");
	JButton deny = new JButton("초기화");

	public RgbColorPicker(Color def) {
		result = def;
		red.setValue(def.getRed());
		green.setValue(def.getGreen());
		blue.setValue(def.getBlue());
		confirm.addActionListener((a) -> updateColor());
		red.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR
				| Cursor.E_RESIZE_CURSOR));
		green.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR
				| Cursor.E_RESIZE_CURSOR));
		blue.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR
				| Cursor.E_RESIZE_CURSOR));
		setLayout(null);
		add(red);
		add(green);
		add(blue);
		refresh();
		red.addChangeListener((l) -> repaint());
		green.addChangeListener((l) -> repaint());
		blue.addChangeListener((l) -> repaint());
	}

	public void update(Component comp, int x, int y) {
		comp.setBounds(x, y, comp.getWidth(), comp.getHeight());
	}

	public void updateColor() {
		result = new Color(red.getValue(), green.getValue(), blue.getValue());
	}

	public RgbColorPicker(int r, int g, int b) {
		this(new Color(r, g, b));
	}

	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		refresh();
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		refresh();
	}

	private void refresh() {
		// BAR
		int bw = getWidth();
		int bh = (int) (getHeight() * 0.2);
		red.setSize(bw, bh);
		blue.setSize(bw, bh);
		green.setSize(bw, bh);
		int bl = 0;
		int bbh = (int) (getHeight() * 0.2);
		update(red, bl, getHeight() - bbh * 3);
		update(blue, bl, getHeight() - bbh * 2);
		update(green, bl, getHeight() - bbh);
	}

	public RgbColorPicker() {
		this(Color.WHITE);
	}

	public int getRed() {
		return red.getValue();
	}

	public void setRed(int r) {
		red.setValue(r);
	}

	public int getGreen() {
		return green.getValue();
	}

	public void setGreen(int g) {
		green.setValue(g);
	}

	public int getBlue() {
		return blue.getValue();
	}

	public void setBlue(int b) {
		blue.setValue(b);
	}

	public Color getColor() {
		return result;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		updateColor();
		g.setColor(result);
		g.fillRect(0, 0, getWidth() / 2, (int) (getHeight() * 0.3));
		g.setColor(Color.GRAY);
		g.drawString("#"
				+ Integer.toHexString(getColor().getRGB()).substring(2),
				(int) (getWidth() * 0.6), (int) (getHeight() * 0.05));
	}
}
