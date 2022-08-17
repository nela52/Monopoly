package me.an.mp.menu.menus;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.an.mp.Board;
import me.an.mp.Monopoly;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuButton;
import me.an.mp.menu.MenuPosition;
import me.an.mp.player.Player;
import me.an.mp.tile.PropertyCards;
import me.an.mp.tile.Tile;
import me.an.mp.util.UtilMath;

public class PropertyMenu extends Menu
{
	public static final int OFFSET = 4;

	public PropertyMenu(Player player)
	{
		super(DEFAULT_BACKGROUND, MenuPosition.CENTER, MenuPosition.CENTER);

		/* Add tiles for testing menu layout
		for (Tile tile : Monopoly.Instance.getBoard().getTiles())
		{
			if (tile instanceof SpecialTile || tile instanceof RailroadTile || tile instanceof UtilityTile || player.ownsTile(tile))
				continue;
		
			player.getProperties().add(tile);
		
			if (player.getProperties().size() == 3)
				break;
		}
		*/

		final int numPerRow = UtilMath.scaleBetween(player.getProperties().size(), 3, 6, 1, 28); //Scale between, 3-6 per row, 1 min ownable, 28 max ownable
		final int cardWidth = PropertyCards.getPropertyCard(Board.Atlantic, numPerRow).getWidth();

		int totalWidth = OFFSET;
		for (int i = 0; i < numPerRow; i++)
			totalWidth += cardWidth + OFFSET;

		int startX = getX() + (getWidth() / 2) - (totalWidth / 2);
		int startY = getY() + OFFSET;

		int x = startX;
		int y = startY;

		for (int i = 0; i < player.getPropertiesSorted().size(); i++)
		{
			Tile tile = player.getPropertiesSorted().get(i);
			if (PropertyCards.getPropertyCard(tile, numPerRow) == null)
				continue;

			BufferedImage propCard = PropertyCards.getPropertyCard(tile, numPerRow);
			addButton(new MenuButton(x, y, propCard, false)
			{
				public void onClick()
				{
					Monopoly.Instance.openMenu(new SinglePropertyMenu(tile));
				}
			});

			x += propCard.getWidth() + OFFSET;
			if ((i + 1) % numPerRow == 0)
			{
				x = startX;
				y += propCard.getHeight() + OFFSET;
			}
		}
	}

	public void onUpdate()
	{

	}

	public void onRender(Graphics g)
	{

	}
}
