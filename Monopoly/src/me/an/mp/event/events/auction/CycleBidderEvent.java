package me.an.mp.event.events.auction;

import me.an.mp.Auction;
import me.an.mp.event.MonoEvent;
import me.an.mp.player.Player;

public class CycleBidderEvent extends MonoEvent
{
	private Auction auction;
	private Player newBidder;

	public CycleBidderEvent(Auction auction, Player newBidder)
	{
		this.auction = auction;
		this.newBidder = newBidder;
	}

	public Auction getAuction()
	{
		return auction;
	}

	public Player getNewBidder()
	{
		return newBidder;
	}
}
