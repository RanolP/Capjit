package me.ranol.capjit;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CapjitFrame extends JFrame {
	private static final long serialVersionUID = 2638737716270816794L;
	JButton option = new JButton("옵션 설정");

	public CapjitFrame() {
		setTitle("Capjit");
		setResizable(false);
		setSize(200, 130);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(option, BorderLayout.CENTER);
		option.addActionListener((a) -> option());
	}

	private void option() {
		OptionFrame.getInstance().setVisible(
				!OptionFrame.getInstance().isVisible());
	}
}
