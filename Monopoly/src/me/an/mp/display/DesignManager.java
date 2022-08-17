package me.an.mp.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import me.an.mp.tile.Tile;
import me.an.mp.util.UtilImage;
import me.an.mp.util.UtilText;

public class DesignManager
{
	public static final int OFFSET = 10;
	public static final int TAB = 8;
	public static final Font FONT = loadFont("MONOPOLY_INLINE.ttf");
	public static final Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, 48);
	public static final Color BUTTON_TEXT = new Color(0xB2C5CF);
	public static final Color HOVER_COLOR = new Color(255, 255, 255, 123);
	public static final Color DARKEN_MIN = new Color(0, 0, 0, 50);
	public static final Color DARKEN_MAX = new Color(0, 0, 0, 100);
	public static final Color SELECT_COLOR = new Color(255, 215, 0, 123);
	public static final Color BAR_COLOR = new Color(0xADD8E6);
	public static final int BAR_HEIGHT = 50;

	public static BufferedImage Button = UtilImage.loadImage("ButtonGeneric.png", 5);
	public static BufferedImage INFO_SCROLL = UtilImage.loadImage("InfoScroll.png");

	private static Font loadFont(String name)
	{
		try
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font font = Font.createFont(Font.TRUETYPE_FONT, DesignManager.class.getResourceAsStream("/" + name));
			ge.registerFont(font);
			return font;
		} catch (FontFormatException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage createButton(String text)
	{
		BufferedImage buttonImage = UtilImage.deepCopy(Button);
		Graphics g = buttonImage.getGraphics();
		Font maxFont = UtilText.getMaxFont(g, text, buttonImage.getWidth(), buttonImage.getHeight(), true);
		FontMetrics fontMetrics = g.getFontMetrics(maxFont);

		Rectangle2D stringBounds = UtilText.getBounds(g, maxFont, text);
		int centerX = (int) ((buttonImage.getWidth() / 2) - (stringBounds.getWidth() / 2));
		int centerY = (int) ((buttonImage.getHeight() / 2) + ((fontMetrics.getHeight() - fontMetrics.getDescent()) / 2));

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(BUTTON_TEXT);
		g.setFont(maxFont);
		g.drawString(text, centerX, centerY);

		return buttonImage;
	}

	public static BufferedImage createButton(String text, int scale)
	{
		BufferedImage buttonImage = UtilImage.loadImage("ButtonGeneric.png", scale);
		Graphics g = buttonImage.getGraphics();
		Font maxFont = UtilText.getMaxFont(g, text, buttonImage.getWidth(), buttonImage.getHeight(), true);
		FontMetrics fontMetrics = g.getFontMetrics(maxFont);

		Rectangle2D stringBounds = UtilText.getBounds(g, maxFont, text);
		int centerX = (int) ((buttonImage.getWidth() / 2) - (stringBounds.getWidth() / 2));
		int centerY = (int) ((buttonImage.getHeight() / 2) + ((fontMetrics.getHeight() - fontMetrics.getDescent()) / 2));

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(BUTTON_TEXT);
		g.setFont(maxFont);
		g.drawString(text, centerX, centerY);

		return buttonImage;
	}

	public static BufferedImage createTileImage(Tile tile)
	{
		int width = 600, height = 682;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());

		int topHeight = (int) (image.getHeight() * 0.1524926686217009);
		Rectangle topBounds = new Rectangle(1, 1, image.getWidth() - 2, topHeight);

		g.setColor(tile.getColorGroup().getColor());
		g.fillRect(topBounds.x, topBounds.y, topBounds.width, topBounds.height);

		String deedText = "TITLE  DEED";
		Font deedFont = g.getFont().deriveFont(16f);
		Point dtCenter = UtilText.getCenter(g, topBounds.width, topBounds.height, deedText, deedFont);
		int dtX = topBounds.x + dtCenter.x;
		int dtY = topBounds.y + dtCenter.y;

		g.setFont(deedFont);
		g.setColor(Color.WHITE);
		g.drawString(deedText, dtX, dtY);

		String tileName = tile.getName();
		Font nameFont = g.getFont().deriveFont(34f).deriveFont(Font.BOLD);
		Point tnCenter = UtilText.getCenter(g, topBounds.width, topBounds.height, tileName, nameFont);
		int tnX = topBounds.x + tnCenter.x;
		int tnY = topBounds.y + (tnCenter.y * 3);

		g.setFont(nameFont);
		g.drawString(tileName, tnX, tnY);

		String rentText = "Rent $" + tile.getRent();
		Font costFont = g.getFont().deriveFont(25f).deriveFont(Font.PLAIN);
		Point rtCenter = UtilText.getCenter(g, width, height, rentText, costFont);
		int rtY = topBounds.y + topBounds.height + ((int) (UtilText.getBounds(g, costFont, rentText).getHeight()));

		g.setFont(costFont);
		g.setColor(Color.BLACK);
		g.drawString(rentText, rtCenter.x, rtY);

		String house1Text = "With 1 House" + " ".repeat(TAB * 5) + "$" + tile.getRents(1);
		Point h1tCenter = UtilText.getCenter(g, width, height, house1Text, costFont);
		int h1Y = rtY + (int) (UtilText.getBounds(g, costFont, house1Text).getHeight() * 1.5);
		g.drawString(house1Text, h1tCenter.x, h1Y);

		String house2Text = "With 2 Houses" + " ".repeat(TAB * 5) + "$" + tile.getRents(2);
		Point h2tCenter = UtilText.getCenter(g, width, height, house2Text, costFont);
		int h2Y = h1Y + (int) (UtilText.getBounds(g, costFont, house2Text).getHeight() * 1.5);
		g.drawString(house2Text, h2tCenter.x, h2Y);

		String house3Text = "With 3 Houses" + " ".repeat(TAB * 5) + "$" + tile.getRents(3);
		Point h3tCenter = UtilText.getCenter(g, width, height, house3Text, costFont);
		int h3Y = h2Y + (int) (UtilText.getBounds(g, costFont, house3Text).getHeight() * 1.5);
		g.drawString(house3Text, h3tCenter.x, h3Y);

		String house4Text = "With 4 Houses" + " ".repeat(TAB * 5) + "$" + tile.getRents(4);
		Point h4tCenter = UtilText.getCenter(g, width, height, house4Text, costFont);
		int h4Y = h3Y + (int) (UtilText.getBounds(g, costFont, house4Text).getHeight() * 1.5);
		g.drawString(house4Text, h4tCenter.x, h4Y);

		String hotelText = "With HOTEL $" + tile.getRents(5);
		Point htCenter = UtilText.getCenter(g, width, height, hotelText, costFont);
		int htY = h4Y + (int) (UtilText.getBounds(g, costFont, hotelText).getHeight() * 1.5);
		g.drawString(hotelText, htCenter.x, htY);

		String mortgageText = "Mortgage Value $" + tile.getMortgage();
		Point mtCenter = UtilText.getCenter(g, width, height, mortgageText, costFont);
		int mtY = htY + (int) (UtilText.getBounds(g, costFont, mortgageText).getHeight() * 4.5);
		g.drawString(mortgageText, mtCenter.x, mtY);

		String houseCostText = "Houses cost $" + tile.getHouseCost() + " Each";
		Point hctCenter = UtilText.getCenter(g, width, height, houseCostText, costFont);
		int hctY = mtY + (int) (UtilText.getBounds(g, costFont, houseCostText).getHeight() * 1.5);
		g.drawString(houseCostText, hctCenter.x, hctY);

		String hotelCostText = "Hotels, $" + tile.getHouseCost() + " Plus 4 Houses";
		Point hoctCenter = UtilText.getCenter(g, width, height, hotelCostText, costFont);
		int hoctY = hctY + (int) (UtilText.getBounds(g, costFont, hotelCostText).getHeight() * 1.5);
		g.drawString(hotelCostText, hoctCenter.x, hoctY);

		Font infoFont = g.getFont().deriveFont(16f);
		g.setFont(infoFont);

		String infoLine1 = "If a player owns ALL the lots of any Color-Group, the";
		Point il1Center = UtilText.getCenter(g, width, height, infoLine1, infoFont);
		int il1Y = hoctY + (int) (UtilText.getBounds(g, infoFont, infoLine1).getHeight() * 1.5);
		g.drawString(infoLine1, il1Center.x, il1Y);

		String infoLine2 = "rent is Doubled on Unimproved Lots in that group.";
		Point il2Center = UtilText.getCenter(g, width, height, infoLine2, infoFont);
		int il2Y = il1Y + (int) (UtilText.getBounds(g, infoFont, infoLine2).getHeight() * 1.5);
		g.drawString(infoLine2, il2Center.x, il2Y);

		return image;
	}

	public static BufferedImage createInfoScroll(String text)
	{
		final int padding = 100;

		Font font = new Font("Helvetica", Font.PLAIN, 48);
		Dimension stringSize = UtilText.getTextSize(text, font);

		BufferedImage image = UtilImage.resizeImage(INFO_SCROLL, stringSize.width + padding, stringSize.height + padding);
		Graphics g = image.getGraphics();

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(font);
		g.setColor(Color.BLACK);

		int centerX = (image.getWidth() / 2) - (stringSize.width / 2);
		int centerY = (image.getHeight() / 2) + (stringSize.height / 4);
		g.drawString(text, centerX, centerY);

		return image;
	}
}
