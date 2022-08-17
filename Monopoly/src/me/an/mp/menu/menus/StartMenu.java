package me.an.mp.menu.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.an.mp.Defaults;
import me.an.mp.Monopoly;
import me.an.mp.display.DesignManager;
import me.an.mp.event.EventHandler;
import me.an.mp.event.events.player.SelectTokenEvent;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuButton;
import me.an.mp.player.Player;
import me.an.mp.player.Token;
import me.an.mp.sound.Sounds;
import me.an.mp.util.Direction;
import me.an.mp.util.UtilImage;
import me.an.mp.util.UtilMath;
import me.an.mp.util.UtilText;

public class StartMenu extends Menu
{
	public static BufferedImage Logo = UtilImage.loadImage("Logo.png", 3);
	private int logoX = (Monopoly.WIDTH / 2) - (Logo.getWidth() / 2), logoY = Logo.getHeight() / 4;

	public static BufferedImage Background = UtilImage.loadImage("BoardBlurred.png");
	private int x = UtilMath.r(-(Background.getWidth() - Monopoly.WIDTH), 0), y = UtilMath.r(-(Background.getHeight() - Monopoly.HEIGHT), 0);

	private Direction moveX = Direction.EAST;
	private Direction moveY = Direction.SOUTH;

	private Token selectedToken;

	private BufferedImage tokenDisplay;
	private int displayX, displayY;

	public StartMenu(Player player)
	{
		super(0, 0, Monopoly.WIDTH, Monopoly.HEIGHT, Color.BLACK);

		int totalWidth = Token.Tokens[0].getImage().getWidth() * 4;
		int startX = (Monopoly.WIDTH / 2) - (totalWidth / 2);
		int half = Token.Tokens.length / 2;

		List<MenuButton> tokenButtons = new ArrayList<MenuButton>();
		for (int i = 0; i < Token.Tokens.length; i++)
		{
			Token token = Token.Tokens[i];
			int x = startX + token.getImage().getWidth() * i;

			int y = 0;
			if (i < half)
				y = (Monopoly.HEIGHT / 2) - (token.getImage().getHeight() / 2) - (token.getImage().getHeight() / 2) - DesignManager.OFFSET;
			else
			{
				x = startX + token.getImage().getWidth() * (i - half);
				y = (Monopoly.HEIGHT / 2) - (token.getImage().getHeight() / 2) + (token.getImage().getHeight() / 2) + DesignManager.OFFSET;
			}

			MenuButton tokenButton = new MenuButton(x, y, token.getImage(), false, Sounds.TOKEN_SELECT, Sounds.TOKEN_HOVER)
			{
				public void onClick()
				{
					selectedToken = token;
					tokenDisplay = Token.getFullSize(selectedToken);

					displayX = (Monopoly.WIDTH / 2) - (tokenDisplay.getWidth() / 2);
					displayY = (int) (Monopoly.HEIGHT - (tokenDisplay.getHeight() * 1.2));

					for (MenuButton button : tokenButtons)
						button.setHighlight(null);

					setHighlight(DesignManager.SELECT_COLOR);
				}
			};
			addButton(tokenButton);
			tokenButtons.add(tokenButton);
		}

		String text = "Choose token:";
		Font font = DesignManager.FONT.deriveFont(100f);
		Dimension textSize = UtilText.getTextSize(text, font);
		int strX = (Monopoly.WIDTH / 2) - (textSize.width / 2);
		int strY = (Monopoly.HEIGHT / 2) - (textSize.height / 2) - (textSize.height / 2);
		addText(text, strX, strY, font, Color.BLACK);

		BufferedImage doneButtonImg = DesignManager.createButton("PLAY");
		int buttonX = (Monopoly.WIDTH / 2) - (doneButtonImg.getWidth() / 2);
		int buttonY = (Monopoly.HEIGHT / 2) - (doneButtonImg.getHeight() / 2) + (doneButtonImg.getHeight() * 2);

		addButton(new MenuButton(buttonX, buttonY, doneButtonImg, true)
		{
			public void onClick()
			{
				if (selectedToken == null)
					return;

				setDone(true);
				EventHandler.Instance.callEvent(new SelectTokenEvent(player, selectedToken));
			}
		});
	}

	public void onUpdate()
	{
		if (moveX == Direction.EAST)
			x -= Defaults.MENU_SPEED;
		else if (moveX == Direction.WEST)
			x += Defaults.MENU_SPEED;

		if (moveY == Direction.NORTH)
			y += Defaults.MENU_SPEED;
		else if (moveY == Direction.SOUTH)
			y -= Defaults.MENU_SPEED;

		if (x <= -(Background.getWidth() - Monopoly.WIDTH) || x >= 0)
			moveX = moveX.opposite();

		if (y <= -(Background.getHeight() - Monopoly.HEIGHT) || y >= 0)
			moveY = moveY.opposite();
	}

	public void onRender(Graphics g)
	{
		g.drawImage(Background, x, y, Monopoly.Instance);
		g.drawImage(Logo, logoX, logoY, Monopoly.Instance);
		if (tokenDisplay != null)
			g.drawImage(tokenDisplay, displayX, displayY, Monopoly.Instance);
	}

	public void onForceClose()
	{

	}
}
