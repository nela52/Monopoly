package me.an.mp.tile.tiles;

import me.an.mp.player.Player;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.TileSide;

public class LuxuryTaxTile extends SpecialTile
{
	public LuxuryTaxTile()
	{
		super("Luxury Tax", TileSide.RIGHT, 7, TileSheet.getSprite(1, 3));
	}

	public void onLandOn(Player player)
	{
		player.subtractMoney(75); //TODO make go to free parking
	}
}
