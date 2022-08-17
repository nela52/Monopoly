package me.an.mp.tile.tiles;

import me.an.mp.player.Player;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.TileSide;

public class GoTile extends SpecialTile
{
	public GoTile()
	{
		super("GO", TileSide.BOTTOM, 10, CornerTileSheet.getSprite(0, 0));
	}

	public void onLandOn(Player player)
	{

	}
}
