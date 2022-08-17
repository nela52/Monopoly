package me.an.mp.tile;

import me.an.mp.Board;
import me.an.mp.card.CardType;
import me.an.mp.player.Player;

public class CardTile extends SpecialTile
{
	private CardType type;

	public CardTile(CardType type, TileSide tileSide, int tilePos, int imgNum)
	{
		super(type.getName(), tileSide, tilePos, type == CardType.CHEST ? TileSheet.getSprite(1, 0) : Board.ChanceTileSheet.getSprite(imgNum > 1 ? imgNum - 2 : imgNum, imgNum > 1 ? 1 : 0));
		this.type = type;
	}

	public void onLandOn(Player player)
	{
		player.giveCard(type);
	}
}
