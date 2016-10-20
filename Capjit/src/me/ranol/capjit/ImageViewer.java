package me.ranol.capjit;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import me.ranol.capjit.components.PictureBox;

public class ImageViewer extends JFrame {
	private static final long serialVersionUID = -5112800797024186848L;

	public ImageViewer(BufferedImage img) {
		setTitle("이미지 뷰어");
		if (img == null || img.getWidth() == 0 || img.getHeight() == 0)
			setVisible(false);
		else {
			PictureBox pb = new PictureBox();
			pb.setImage(img);
			add(pb);
			setSize(img.getWidth() + 20, img.getHeight() + 40);
		}
	}
}
