package me.an.mp.tile.tiles;

import me.an.mp.player.Player;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.TileSide;

public class JailTile extends SpecialTile
{
	public JailTile()
	{
		super("Jail", TileSide.BOTTOM, 0, CornerTileSheet.getSprite(1, 0));
	}

	public void onLandOn(Player player)
	{
		//TODO implement
	}
}
