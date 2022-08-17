package me.an.mp.tile.tiles;

import me.an.mp.AI;
import me.an.mp.Monopoly;
import me.an.mp.menu.menus.TaxMenu;
import me.an.mp.player.Player;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.TileSide;

public class IncomeTaxTile extends SpecialTile
{
	public IncomeTaxTile()
	{
		super("Income Tax", TileSide.BOTTOM, 6, TileSheet.getSprite(3, 0));
	}

	public void onLandOn(Player player)
	{
		if (player instanceof AI || player != Monopoly.Instance.getPlayer())
			return;
		Monopoly.Instance.openMenu(new TaxMenu(player));
	}
}
