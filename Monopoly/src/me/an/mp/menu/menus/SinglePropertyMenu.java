package me.an.mp.menu.menus;

import java.awt.Graphics;

import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuPosition;
import me.an.mp.tile.PropertyCards;
import me.an.mp.tile.Tile;

public class SinglePropertyMenu extends Menu
{
	public SinglePropertyMenu(Tile tile)
	{
		super(PropertyCards.getPropertyCard(tile), MenuPosition.CENTER, MenuPosition.CENTER);
	}

	public void onUpdate()
	{

	}

	public void onRender(Graphics g)
	{

	}
}
