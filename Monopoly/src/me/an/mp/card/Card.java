package me.an.mp.card;

import java.awt.image.BufferedImage;

import me.an.mp.Board;
import me.an.mp.player.Player;
import me.an.mp.util.UtilMath;

public abstract class Card
{
	private CardType type;
	private BufferedImage image; //TODO add back image for instructions

	public Card(CardType type)
	{
		this.type = type;
		if (type == CardType.CHEST)
			image = Board.TileSheet.getSprite(1, 0);
		else if (type == CardType.CHANCE)
			image = Board.ChanceTileSheet.getSprite(UtilMath.r(2, 3), UtilMath.r(0, 1));
	}

	public CardType getType()
	{
		return type;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public abstract void apply(Player pickedUp, Player[] targets);
}
