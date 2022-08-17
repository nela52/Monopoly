package me.an.mp.tile.tiles;

import me.an.mp.player.Player;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.TileSide;

public class GoJailTile extends SpecialTile
{
	public GoJailTile()
	{
		super("Go To Jail", TileSide.TOP, 10, CornerTileSheet.getSprite(1, 1));
	}

	public void onLandOn(Player player)
	{
		player.goToJail();
	}
}
