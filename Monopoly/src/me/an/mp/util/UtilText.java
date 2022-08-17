package me.an.mp.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class UtilText
{
	public static Dimension getTextSize(String text, Font font)
	{
		AffineTransform affineTransform = new AffineTransform();
		FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
		int textWidth = (int) font.getStringBounds(text, fontRenderContext).getWidth();
		int textHeight = (int) font.getStringBounds(text, fontRenderContext).getHeight();
		return new Dimension(textWidth, textHeight);
	}

	public static Rectangle2D getStringBounds(String text, Font font)
	{
		AffineTransform affineTransform = new AffineTransform();
		FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
		return font.getStringBounds(text, fontRenderContext);
	}

	public static Rectangle2D getBounds(Graphics g, Font font, String text)
	{
		FontMetrics fontMetrics = g.getFontMetrics(font);
		return fontMetrics.getStringBounds(text, g);
	}

	public static Font getMaxFont(Graphics g, String text, int width, int height, boolean bold)
	{
		Graphics2D graphics = (Graphics2D) g;
		Rectangle2D maxRect = new Rectangle2D.Float(0, 0, width - 7, height - 10);

		//int fontWidth = 0, fontHeight = 0;
		float size = 80f * 4f;
		float diff = size / 2;

		Font newFont = bold ? g.getFont().deriveFont(size).deriveFont(Font.BOLD) : g.getFont().deriveFont(size);
		FontMetrics fontMetrics = graphics.getFontMetrics(newFont);
		Rectangle2D stringBounds = null;

		while (Math.abs(diff) > 1)
		{
			newFont = newFont.deriveFont(size);
			fontMetrics = graphics.getFontMetrics(newFont);
			stringBounds = fontMetrics.getStringBounds(text, graphics);
			stringBounds = new Rectangle2D.Float(0f, 0f, (float) (stringBounds.getX() + stringBounds.getWidth()), (float) (stringBounds.getHeight()));

			if (maxRect.contains(stringBounds))
			{
				if (0 < diff)
					diff = Math.abs(diff);
				else if (diff < 0)
					diff = Math.abs(diff) / 2;
			} else
			{
				if (0 < diff)
					diff = -Math.abs(diff) / 2;
				else if (diff < 0)
				{
					if (size <= Math.abs(diff))
						diff = -Math.abs(diff) / 2;
					else
						diff = -Math.abs(diff);
				}
			}
			size += diff;
		}

		//fontWidth = (int) ((width / 2) - (stringBounds.getWidth() / 2));
		//fontHeight = (int) (height - maxRect.getHeight() + fontMetrics.getAscent());
		return newFont;
	}

	public static Font getMaxFont(Font font, String text, int width, int height, boolean bold)
	{
		Rectangle2D maxRect = new Rectangle2D.Float(0, 0, width - 7, height - 10);

		//int fontWidth = 0, fontHeight = 0;
		float size = 80f * 4f;
		float diff = size / 2;

		Font newFont = bold ? font.deriveFont(size).deriveFont(Font.BOLD) : font.deriveFont(size);

		AffineTransform affineTransform = new AffineTransform();
		FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, true);
		Rectangle2D stringBounds = font.getStringBounds(text, fontRenderContext);

		while (Math.abs(diff) > 1)
		{
			newFont = newFont.deriveFont(size);
			stringBounds = getStringBounds(text, newFont);
			stringBounds = new Rectangle2D.Float(0f, 0f, (float) (stringBounds.getX() + stringBounds.getWidth()), (float) (stringBounds.getHeight()));

			if (maxRect.contains(stringBounds))
			{
				if (0 < diff)
					diff = Math.abs(diff);
				else if (diff < 0)
					diff = Math.abs(diff) / 2;
			} else
			{
				if (0 < diff)
					diff = -Math.abs(diff) / 2;
				else if (diff < 0)
				{
					if (size <= Math.abs(diff))
						diff = -Math.abs(diff) / 2;
					else
						diff = -Math.abs(diff);
				}
			}
			size += diff;
		}

		//fontWidth = (int) ((width / 2) - (stringBounds.getWidth() / 2));
		//fontHeight = (int) (height - maxRect.getHeight() + fontMetrics.getAscent());
		return newFont;
	}

	public static Point getCenter(Graphics g, int width, int height, String text, Font font)
	{
		Rectangle2D stringBounds = UtilText.getBounds(g, font, text);
		int centerX = (int) ((width / 2) - (stringBounds.getWidth() / 2));
		int centerY = (int) ((height / 2) - (stringBounds.getHeight() / 2));
		return new Point(centerX, centerY);
	}

	public static void paintTextWithOutline(Graphics g, String text, int x, int y)
	{
		Color outlineColor = Color.BLACK;
		Color fillColor = Color.WHITE;
		BasicStroke outlineStroke = new BasicStroke(2.0f);

		if (g instanceof Graphics2D)
		{
			Graphics2D g2 = (Graphics2D) g;

			Color originalColor = g2.getColor();
			Stroke originalStroke = g2.getStroke();
			RenderingHints originalHints = g2.getRenderingHints();

			GlyphVector glyphVector = g.getFont().createGlyphVector(g2.getFontRenderContext(), text);
			Shape textShape = glyphVector.getOutline();

			AffineTransform transform = g2.getTransform();
			g2.translate(x, y);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			g2.setColor(outlineColor);
			g2.setStroke(outlineStroke);
			g2.draw(textShape);

			g2.setColor(fillColor);
			g2.fill(textShape);

			g2.setColor(originalColor);
			g2.setStroke(originalStroke);
			g2.setRenderingHints(originalHints);

			g2.setTransform(transform);
		}
	}

	public static int textHeight(Graphics g, String text)
	{
		return (int) g.getFontMetrics(g.getFont()).getStringBounds(text, g).getHeight() / 2 + 10;
	}
}
