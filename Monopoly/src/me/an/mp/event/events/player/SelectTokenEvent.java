package me.an.mp.event.events.player;

import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;
import me.an.mp.player.Token;

public class SelectTokenEvent extends MonoEvent
{
	private Player player;
	private Token token;

	public SelectTokenEvent(Player player, Token token)
	{
		this.player = player;
		this.token = token;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Token getToken()
	{
		return token;
	}
}
