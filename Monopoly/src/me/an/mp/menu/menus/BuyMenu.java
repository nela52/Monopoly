package me.an.mp.menu.menus;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.an.mp.Defaults;
import me.an.mp.Monopoly;
import me.an.mp.display.DesignManager;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuBar;
import me.an.mp.menu.MenuButton;
import me.an.mp.menu.MenuPosition;
import me.an.mp.player.Player;
import me.an.mp.sound.Sounds;
import me.an.mp.tile.PropertyCards;
import me.an.mp.tile.Tile;

public class BuyMenu extends Menu
{
	public BuyMenu(Player player, Tile tile)
	{
		super(PropertyCards.getPropertyCard(tile), MenuPosition.CENTER, MenuPosition.CENTER);

		int menuX = getX();
		int menuY = getY();
		int menuWidth = getWidth();
		int menuHeight = getHeight();

		BufferedImage buyButtonImg = DesignManager.createButton("Buy Tile", 6);
		int buyButtonX = menuX + ((menuWidth / 2) - (buyButtonImg.getWidth() / 2)) - (buyButtonImg.getWidth() / 2) - DesignManager.OFFSET;
		int buyButtonY = menuY + ((menuHeight / 2) - (buyButtonImg.getHeight() / 2)) + ((int) (buyButtonImg.getHeight() * 1.5));
		MenuButton buyButton = new MenuButton(buyButtonX, buyButtonY, buyButtonImg, true)
		{
			public void onClick()
			{
				player.buyTile(tile);
				Sounds.PURCHASE_TILE.play();
				setDone(true);

				if (Monopoly.Instance.getDice().isDoubles() && Monopoly.Instance.getDice().hasRolled())
					Monopoly.Instance.resetTurnTimer();
			}
		};
		buyButton.setDisabled(player.hasMoney(tile.getCost()) ? false : true);
		addButton(buyButton);

		BufferedImage auctionButtonImg = DesignManager.createButton("Auction Tile", 6);
		int auctionButtonX = menuX + ((menuWidth / 2) - (auctionButtonImg.getWidth() / 2)) + (auctionButtonImg.getWidth() / 2) + DesignManager.OFFSET;
		int auctionButtonY = menuY + ((menuHeight / 2) - (auctionButtonImg.getHeight() / 2)) + ((int) (auctionButtonImg.getHeight() * 1.5));
		addButton(new MenuButton(auctionButtonX, auctionButtonY, auctionButtonImg, true)
		{
			public void onClick()
			{
				setDone(true);
				Monopoly.Instance.startAuction(tile, player);
			}
		});

		addBar(new MenuBar(menuX, menuY - DesignManager.BAR_HEIGHT, menuWidth, DesignManager.BAR_HEIGHT, Defaults.VIEW_TILE_TIME, DesignManager.BAR_COLOR, "TIME:")
		{
			public void onDone()
			{
				Sounds.OUT_OF_TIME.play();
				setDone(true); //TODO handle auctioning
			}
		});
	}

	public void onUpdate()
	{

	}

	public void onRender(Graphics g)
	{

	}

	public void onForceClose()
	{
		//TODO make go to auction automatically
		setDone(true);
	}
}
