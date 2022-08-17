package me.an.mp.tile;

import java.awt.image.BufferedImage;

public class RailroadTile extends Tile
{
	public RailroadTile(String name, TileSide tileSide, int tilePos, BufferedImage image)
	{
		super(name, ColorGroup.RAIL, 200, new int[] {}, -1, tileSide, tilePos, image);
	}

	public int getRent()
	{
		int rentOwed = 0;
		for (Tile tile : getOwner().getProperties())
		{
			if (!(tile instanceof RailroadTile))
				continue;

			if (rentOwed == 0)
				rentOwed = 25;
			else
				rentOwed *= 2;
		}
		return rentOwed;
	}
}
