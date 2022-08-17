package me.an.mp.event.events;

import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class CycleTurnEvent extends MonoEvent
{
	private Player player;

	public CycleTurnEvent(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}
}
