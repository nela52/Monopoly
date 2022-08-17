package me.an.mp.menu;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import me.an.mp.display.DesignManager;
import me.an.mp.util.UtilText;

public abstract class MenuBox
{
	public static final int PADDING = 4;

	private int x, y, width, height;
	private Rectangle rect;
	private String text, defaultText;
	private boolean neverSelected = true, selected = false, textOnly = true, numbersOnly = false;
	private boolean mouseOver = false, cursorVis = false, visible = true;
	private int tick = 0;

	public static final Color INSIDE = new Color(0x89A1AA);
	public static final Color OUTSIDE = new Color(0x74909B);

	public MenuBox(int x, int y, int width, int height, String defaultText, boolean numbersOnly)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rect = new Rectangle(x, y, width, height);
		this.defaultText = defaultText;
		this.text = defaultText;
		this.numbersOnly = numbersOnly;
		if (numbersOnly)
			textOnly = false;
	}

	public Rectangle getRectangle()
	{
		return rect;
	}

	public String getText()
	{
		return text;
	}

	public void addCharacter(char c)
	{
		if (textOnly)
		{
			if (!Character.isAlphabetic(c))
				return;
		} else if (numbersOnly)
		{
			if (!Character.isDigit(c))
				return;
		}

		cursorVis = false;
		text += Character.toString(c);
	}

	public void removeCharacter()
	{
		if (text.isEmpty())
			return;

		text = text.length() > 1 ? text.substring(0, text.length() - 1) : "";
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
		if (selected)
		{
			if (neverSelected)
			{
				text = ""; //Clear text from default text
				neverSelected = false;
			}
		} else
		{
			cursorVis = false;

			if (text.isEmpty()) //Didnt input anything
			{
				text = defaultText;
				neverSelected = true;
			}
		}
	}

	public void setMouseOver(boolean mouseOver)
	{
		this.mouseOver = mouseOver;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
		if (visible)
			text = defaultText;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void update()
	{
		if (!visible || !selected)
			return;

		if (tick++ % 30 == 0) //Every half second
		{
			if (text.length() == 0)
				cursorVis = !cursorVis;
			else
				cursorVis = false;
		}
	}

	public void render(Graphics g)
	{
		if (!visible)
			return;

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (mouseOver)
			g.setColor(INSIDE.brighter().darker());
		else if (selected)
			g.setColor(INSIDE.darker());
		else
			g.setColor(INSIDE);

		g.fillRect(x, y, width, height);

		g.setColor(mouseOver ? OUTSIDE.brighter() : OUTSIDE);
		g.drawRect(x, y, width, height);

		if (cursorVis)
		{
			g.setColor(Color.WHITE);
			g.fillRect(x + PADDING, y + PADDING, 2, height - (PADDING * 2));
		}

		g.setColor(Color.WHITE);
		g.setFont(UtilText.getMaxFont(DesignManager.DEFAULT_FONT, text, width - PADDING, height - PADDING, false));

		FontMetrics fm = g.getFontMetrics(g.getFont());
		Rectangle2D stringBounds = fm.getStringBounds(text, g);

		int centerX = (int) (x + (width / 2) - (stringBounds.getWidth() / 2));
		int centerY = (int) (y + (height / 2) - (stringBounds.getHeight() / 2) + stringBounds.getHeight());

		g.drawString(text, centerX, centerY);
	}

	public abstract boolean onDone();
}
