package me.an.mp.tile.tiles;

import me.an.mp.player.Player;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.TileSide;

public class FreeParkingTile extends SpecialTile
{
	public FreeParkingTile()
	{
		super("Free Parking", TileSide.TOP, 0, CornerTileSheet.getSprite(0, 1));
	}

	public void onLandOn(Player player)
	{
		//TODO implement
	}
}
