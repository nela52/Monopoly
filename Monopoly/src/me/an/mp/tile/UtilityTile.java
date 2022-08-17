package me.an.mp.tile;

import java.awt.image.BufferedImage;

import me.an.mp.player.Player;

public class UtilityTile extends Tile
{
	public UtilityTile(String name, TileSide tileSide, int tilePos, BufferedImage image)
	{
		super(name, ColorGroup.UTILITY, -1, new int[] {}, -1, tileSide, tilePos, image);
	}

	public void onLandOn(Player player)
	{
		//TODO implement
	}
}
