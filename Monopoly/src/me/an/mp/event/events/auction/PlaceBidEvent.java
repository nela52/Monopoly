package me.an.mp.event.events.auction;

import me.an.mp.Auction;
import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class PlaceBidEvent extends MonoEvent
{
	private Auction auction;
	private Player player;
	private int bid;

	public PlaceBidEvent(Auction auction, Player player, int bid)
	{
		this.player = player;
		this.bid = bid;
		this.auction = auction;
	}

	public Auction getAuction()
	{
		return auction;
	}

	public Player getPlayer()
	{
		return player;
	}

	public int getBid()
	{
		return bid;
	}
}
