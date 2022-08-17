package me.an.mp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.an.mp.event.EventHandler;
import me.an.mp.event.events.auction.AuctionEndEvent;
import me.an.mp.event.events.auction.CycleBidderEvent;
import me.an.mp.event.events.auction.PlaceBidEvent;
import me.an.mp.event.events.auction.PlayerForfeitEvent;
import me.an.mp.player.Player;
import me.an.mp.tile.Tile;

public class Auction
{
	private Tile tile;
	private Map<Player, Integer> bidders;
	private Player currentBidder;
	private int auctionTime = Defaults.AUCTION_TIME * 60, btwnBidTime = Defaults.TIME_BETWEEN_BIDS, tick = 0;
	private boolean awaitingNextBidder = false, done = false;
	private List<Player> forfeited;
	private Player winner;

	public Auction(Tile tile, Player startedAuc)
	{
		this.tile = tile;
		this.bidders = new LinkedHashMap<Player, Integer>();
		this.forfeited = new ArrayList<Player>();
		bidders.put(startedAuc, 0);
		for (Player player : Monopoly.Instance.getPlayers())
			bidders.put(player, 0);
		currentBidder = startedAuc;
	}

	public Tile getTile()
	{
		return tile;
	}

	private int indexOf(Player bidder)
	{
		for (int i = 0; i < bidders.size(); i++)
		{
			if (bidders.keySet().toArray(new Player[0])[i] == bidder)
				return i;
		}
		return -1;
	}

	private Player getBidder(int bid, List<Player> exclude)
	{
		for (Player bidder : bidders.keySet())
		{
			if (exclude.contains(bidder))
				continue;

			if (bidders.get(bidder) == bid)
				return bidder;
		}
		return null;
	}

	public Map<Player, Integer> getSortedBidders()
	{
		boolean allZero = true;
		for (int bid : bidders.values())
		{
			if (bid > 0)
			{
				allZero = false;
				break;
			}
		}

		if (allZero)
			return bidders;

		List<Integer> bids = new ArrayList<Integer>(bidders.values());
		Collections.sort(bids);
		Collections.reverse(bids);

		List<Player> exclude = new ArrayList<Player>();

		Map<Player, Integer> sortedBidders = new LinkedHashMap<Player, Integer>();
		for (int bid : bids)
		{
			Player bidder = getBidder(bid, exclude);
			sortedBidders.put(bidder, bid);
			exclude.add(bidder);
		}

		return sortedBidders;
	}

	public Player getCurrentBidder()
	{
		return currentBidder;
	}

	public void placeBid(Player player, int bid)
	{
		bidders.put(player, bid);
		EventHandler.Instance.callEvent(new PlaceBidEvent(this, player, bid));
		awaitingNextBidder = true;
	}

	public void cycleBidder()
	{
		Player[] biddersArr = bidders.keySet().toArray(new Player[0]);

		if (indexOf(currentBidder) == bidders.size() - 1)
			currentBidder = biddersArr[0];
		else
			currentBidder = biddersArr[indexOf(currentBidder) + 1];

		List<Player> toRemove = new ArrayList<Player>(forfeited);
		for (Player player : toRemove)
		{
			bidders.remove(player);
			forfeited.remove(player);
			EventHandler.Instance.callEvent(new PlayerForfeitEvent(player));
		}

		if (bidders.size() == 1)
		{
			finish();
			return;
		}

		EventHandler.Instance.callEvent(new CycleBidderEvent(this, currentBidder));
	}

	public void forfeit(Player player)
	{
		forfeited.add(player);
		awaitingNextBidder = true;
	}

	public Player getWinner()
	{
		return winner;
	}

	public int getMaxBid()
	{
		return getSortedBidders().values().toArray(new Integer[0])[0];
	}

	private void finish()
	{
		done = true;
		winner = getSortedBidders().keySet().toArray(new Player[0])[0];
		EventHandler.Instance.callEvent(new AuctionEndEvent(this));
	}

	public int getTime()
	{
		return auctionTime;
	}

	public void update()
	{
		if (awaitingNextBidder)
		{
			if (tick++ % 60 == 0)
				btwnBidTime--;

			if (btwnBidTime == 0)
			{
				awaitingNextBidder = false;
				btwnBidTime = Defaults.TIME_BETWEEN_BIDS;
				cycleBidder();
			}
		}

		if (--auctionTime == 0 && !done)
			finish();
	}
}
