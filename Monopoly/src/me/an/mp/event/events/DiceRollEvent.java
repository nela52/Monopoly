package me.an.mp.event.events;

import me.an.mp.Dice;
import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class DiceRollEvent extends MonoEvent
{
	private Player player;
	private Dice dice;

	public DiceRollEvent(Player player, Dice dice)
	{
		this.player = player;
		this.dice = dice;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Dice getDice()
	{
		return dice;
	}
}
