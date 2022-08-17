package me.an.mp.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MenuText
{
	private String text;
	private int x, y;
	private Font font;
	private Color color;

	public MenuText(String text, int x, int y, Font font, Color color)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.font = font;
		this.color = color;
	}

	public void render(Graphics g)
	{
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, x, y);
	}
}
