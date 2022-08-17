package me.an.mp.menu.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.an.mp.display.DesignManager;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuButton;
import me.an.mp.menu.MenuPosition;
import me.an.mp.player.Player;
import me.an.mp.util.UtilText;

public class TaxMenu extends Menu
{
	public TaxMenu(Player player)
	{
		super(DEFAULT_BACKGROUND, MenuPosition.CENTER, MenuPosition.CENTER);

		BufferedImage twoButtonImg = DesignManager.createButton("$200");
		int twoButtonX = getX() + (getWidth() / 2) - (twoButtonImg.getWidth() / 2) - (twoButtonImg.getWidth() / 2);
		int twoButtonY = getY() + (getHeight() / 2) - (twoButtonImg.getHeight() / 2);
		addButton(new MenuButton(twoButtonX, twoButtonY, twoButtonImg, true)
		{
			public void onClick()
			{
				player.subtractMoney(200);
				setDone(true);
			}
		});

		BufferedImage tenPercentImg = DesignManager.createButton("10%");
		int tenButtonX = getX() + (getWidth() / 2) - (tenPercentImg.getWidth() / 2) + (tenPercentImg.getWidth() / 2);
		int tenButtonY = getY() + (getHeight() / 2) - (tenPercentImg.getHeight() / 2);
		addButton(new MenuButton(tenButtonX, tenButtonY, tenPercentImg, true)
		{
			public void onClick()
			{
				int tenPercent = (int) (player.getMoney() * 0.10);
				player.subtractMoney(tenPercent);
				setDone(true);
			}
		});

		String text = "Income Tax:";
		Font font = new Font("Helvetica", Font.BOLD, 72);
		Dimension textSize = UtilText.getTextSize(text, font);
		int textX = getX() + (getWidth() / 2) - (textSize.width / 2);
		int textY = getY() + (getHeight() / 2) - (textSize.height / 2) - textSize.height;
		addText(text, textX, textY, font, Color.WHITE);
	}

	public void onUpdate()
	{

	}

	public void onRender(Graphics g)
	{

	}
}
