package me.an.mp.event.events.auction;

import me.an.mp.Auction;
import me.an.mp.event.MonoEvent;

public class AuctionEndEvent extends MonoEvent
{
	private Auction auction;

	public AuctionEndEvent(Auction auction)
	{
		this.auction = auction;
	}

	public Auction getAuction()
	{
		return auction;
	}
}
