package me.an.mp.event.events.auction;

import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class PlayerForfeitEvent extends MonoEvent
{
	private Player player;

	public PlayerForfeitEvent(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}
}
