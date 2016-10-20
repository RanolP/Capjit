package me.ranol.capjit;

import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import me.ranol.capjit.capturer.Capturer;
import me.ranol.capjit.capturer.DisplayManager;

public class SizeScreen extends Window {
	boolean line = true;
	Image bg;
	int px = 4;
	private Font f = new Font("¸¼Àº °íµñ", Font.BOLD, px * 3);
	Point start, stay;
	List<EndListener> el = new ArrayList<>();

	public List<EndListener> getEnd() {
		return el;
	}

	private static final long serialVersionUID = 2213957016996716923L;

	public SizeScreen(Frame owner) {
		super(owner);
		bg = Toolkit.getDefaultToolkit().createImage(
				Capturer.captureFull().getSource());
		setBounds(getGraphicsConfiguration().getBounds());
		setVisible(true);
		paint(getGraphics());
		MouseManager mm = new MouseManager(this);
		addMouseListener(mm);
		addMouseMotionListener(mm);
		setAlwaysOnTop(true);
	}

	protected void nd() {
		line = false;
		update(this.getGraphics());
		repaint();
	}

	void drawRect(Graphics g, Point p1, Point p2) {
		Point min = DisplayManager.getMin(p1, p2);
		Point max = DisplayManager.getMax(p1, p2);
		g.fillRect(min.x, min.y, px / 2, Math.abs(min.y - max.y));
		g.fillRect(max.x, min.y, px / 2, Math.abs(min.y - max.y));
		g.fillRect(min.x, min.y, Math.abs(min.x - max.x) + px / 2, px / 2);
		g.fillRect(min.x, max.y, Math.abs(min.x - max.x) + px / 2, px / 2);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(f);
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		g.setColor(OptionFrame.getColor());
		if (line && start != null && stay != null) {
			int s = 15;
			int[] x = new int[2];
			int[] y = new int[2];
			x[0] = Math.min(start.x, stay.x);
			y[0] = Math.min(start.y, stay.y);
			x[1] = Math.max(start.x, stay.x);
			y[1] = Math.max(start.y, stay.y);
			String temp = x[1] + ", " + y[1];
			x[1] -= temp.length() * 6;
			g.drawString(x[0] + ", " + y[0], x[0] + 5, y[0] + s);
			g.drawString(temp, x[1] - 5, y[1] - s + 5);
			drawRect(g, start, stay);
		} else if (line) {
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice().getDisplayMode();
			String dat = mouse.x + ", " + mouse.y;
			int y = mouse.y;
			if (mouse.y < dm.getHeight() / 2)
				y += 15;
			else
				y -= 15;
			int x = mouse.x;
			if (mouse.x < dm.getWidth() / 2)
				x += 20;
			else
				x -= (dat.length() * 7);
			g.drawString(dat, x, y);
			g.fillRect(0, mouse.y, dm.getWidth(), px / 2);
			g.fillRect(mouse.x, 0, px / 2, dm.getHeight());
		}
	}

	@FunctionalInterface
	public interface EndListener {
		public void end(Point start, Point end);
	}

	class MouseManager implements MouseListener, MouseMotionListener {
		SizeScreen ss;

		public MouseManager(SizeScreen ss) {
			this.ss = ss;
			ss.line = true;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			ss.stay = e.getLocationOnScreen();
			ss.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			ss.repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			ss.start = e.getLocationOnScreen();
			ss.line = true;
			ss.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			ss.nd();
			ss.el.forEach((el) -> el.end(start, stay));
			setVisible(false);
			setEnabled(false);
		}
	}
}
