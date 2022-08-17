package me.an.mp.event.events.player;

import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class BalanceChangeEvent extends MonoEvent
{
	private Player player;
	private int amount;

	public BalanceChangeEvent(Player player, int amount)
	{
		this.player = player;
		this.amount = amount;
	}

	public Player getPlayer()
	{
		return player;
	}

	public int getAmount()
	{
		return amount;
	}
}
