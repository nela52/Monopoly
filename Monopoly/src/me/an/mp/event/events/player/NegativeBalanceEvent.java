package me.an.mp.event.events.player;

import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class NegativeBalanceEvent extends MonoEvent
{
	private Player player;
	private int balance;

	public NegativeBalanceEvent(Player player, int balance)
	{
		this.player = player;
		this.balance = balance;
	}

	public Player getPlayer()
	{
		return player;
	}

	public int getBalance()
	{
		return balance;
	}
}
