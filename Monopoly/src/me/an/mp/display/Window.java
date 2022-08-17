package me.an.mp.display;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import me.an.mp.Monopoly;

@SuppressWarnings("serial")
public class Window extends JFrame
{
	public Window(int width, int height, String title, Monopoly monopoly)
	{
		super(title);

		monopoly.setPreferredSize(new Dimension(width, height));
		monopoly.setMinimumSize(new Dimension(width, height));
		monopoly.setMaximumSize(new Dimension(width, height));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//setUndecorated(true);

		JPanel panel = new JPanel();
		panel.add(monopoly);

		add(panel);
		pack();
		setLocationRelativeTo(null);

		monopoly.start();
		setVisible(true);
	}
}
