package me.an.mp.tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.an.mp.Monopoly;
import me.an.mp.display.DesignManager;
import me.an.mp.util.UtilImage;

public class PropertyCards
{
	private static Map<Tile, List<BufferedImage>> propCards = new HashMap<Tile, List<BufferedImage>>();

	public static void initialize()
	{
		for (Tile tile : Monopoly.Instance.getBoard().getTiles())
		{
			if (tile instanceof SpecialTile || tile instanceof RailroadTile || tile instanceof UtilityTile)
				continue;

			BufferedImage propImage = DesignManager.createTileImage(tile);

			List<BufferedImage> scaledImages = new ArrayList<BufferedImage>();
			for (int i = 1; i <= 10; i++)
			{
				if (i == 1)
					scaledImages.add(propImage);
				else
					scaledImages.add(UtilImage.shrinkImage(propImage, i));
			}

			propCards.put(tile, scaledImages);
		}
	}

	public static BufferedImage getPropertyCard(Tile tile)
	{
		return propCards.get(tile).get(0);
	}

	public static BufferedImage getPropertyCard(Tile tile, int scale)
	{
		return propCards.get(tile).get(scale - 1);
	}
}
