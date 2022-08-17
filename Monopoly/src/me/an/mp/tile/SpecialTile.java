package me.an.mp.tile;

import java.awt.image.BufferedImage;

import me.an.mp.player.Player;

public abstract class SpecialTile extends Tile
{
	public SpecialTile(String name, TileSide tileSide, int tilePos, BufferedImage image)
	{
		super(name, ColorGroup.NONE, -1, new int[] {}, -1, tileSide, tilePos, image);
	}

	public abstract void onLandOn(Player player);
}
