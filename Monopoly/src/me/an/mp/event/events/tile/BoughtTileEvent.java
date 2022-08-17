package me.an.mp.event.events.tile;

import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;
import me.an.mp.tile.Tile;

public class BoughtTileEvent extends MonoEvent
{
	private Player player;
	private Tile tile;

	public BoughtTileEvent(Player player, Tile tile)
	{
		this.player = player;
		this.tile = tile;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Tile getTile()
	{
		return tile;
	}
}
