package me.an.mp;

import me.an.mp.event.EventHandler;
import me.an.mp.event.MonoEvent;
import me.an.mp.event.MonoListener;
import me.an.mp.event.events.CycleTurnEvent;
import me.an.mp.event.events.auction.CycleBidderEvent;
import me.an.mp.event.events.tile.LandOnTileEvent;
import me.an.mp.player.Player;
import me.an.mp.player.Token;
import me.an.mp.tile.ColorGroup;
import me.an.mp.tile.SpecialTile;
import me.an.mp.tile.Tile;
import me.an.mp.tile.tiles.IncomeTaxTile;
import me.an.mp.util.TileValueSorter;
import me.an.mp.util.UtilMath;

public class AI extends Player implements MonoListener
{
	public AI(Token token)
	{
		super(token);
		EventHandler.Instance.addListener(this);
	}

	private void takeTurn()
	{
		if (inJail())
			Monopoly.Instance.getDice().rollMoveIfDoubles(this);
		else
			Monopoly.Instance.getDice().roll(this);
	}

	private Tile tryGetLeastValuableHouse()
	{
		TileValueSorter sorter = new TileValueSorter(this);

		boolean hasHouses = false;
		for (Tile tile : getProperties())
		{
			if (tile.getNumHouses() > 0)
			{
				hasHouses = true;
				break;
			}
		}

		if (!hasHouses)
			return null;

		ColorGroup leastVal = null;
		for (ColorGroup group : sorter.getRanked())
		{
			if (group == ColorGroup.RAIL || group == ColorGroup.UTILITY)
				continue;

			for (Tile tile : getProperties(group))
			{
				if (tile.getNumHouses() > 0)
				{
					leastVal = group;
					break;
				}
			}
		}

		return sorter.getLastValuable(leastVal);
	}

	private boolean allPropertiesMortgaged()
	{
		for (Tile tile : getProperties())
		{
			if (!tile.isMortgaged())
				return false;
		}
		return true;
	}

	private boolean tryGetMoney(int amount)
	{
		while (getMoney() < amount)
		{
			if (allPropertiesMortgaged())
				return false;

			TileValueSorter sorter = new TileValueSorter(this);

			Tile leastValHouseTile = tryGetLeastValuableHouse();
			if (leastValHouseTile != null)
				leastValHouseTile.removeHouse();
			else
				sorter.getLastValuable(sorter.getRanked().get(0)).setMortgaged(true);
		}

		return true;
	}

	private void endTurn()
	{
		Monopoly.Instance.startNextTurn();
	}

	public void update()
	{
		if (Monopoly.Instance.getTurnPlayer() != this || Monopoly.Instance.awaitingNextTurn())
			return;

		super.update();
	}

	public void onEvent(MonoEvent event)
	{
		if (event instanceof CycleTurnEvent)
		{
			CycleTurnEvent e = (CycleTurnEvent) event;
			if (e.getPlayer() != this)
				return;
			takeTurn();
		}

		if (event instanceof CycleBidderEvent)
		{
			CycleBidderEvent e = (CycleBidderEvent) event;
			if (e.getNewBidder() != this)
				return;

			Auction auction = e.getAuction();
			Tile tile = auction.getTile();

			int ownedProps = getProperties(tile.getColorGroup()).size();
			int scaledDesire = UtilMath.scaleBetween(ownedProps, 20, 80, 0, Monopoly.Instance.getBoard().getTiles(tile.getColorGroup()).size()); //Scale between 20% and 80%
			double buyDesire = scaledDesire / 100.0;

			int maxWilling = tile.getCost() + (int) (getMoney() * buyDesire); //Max amount willing to bid depending on desire to acquire property
			if (auction.getMaxBid() >= maxWilling)
			{
				auction.forfeit(this);
				return;
			}

			int aucMax = auction.getMaxBid();
			int minBid = (int) (aucMax * 0.2);
			int maxBid = (int) (aucMax * 0.8);

			int desiredBid = (int) (aucMax * buyDesire);
			int bidAmount = UtilMath.scaleBetween(desiredBid, 10, 100, minBid, maxBid);
			double bidPercent = bidAmount / 100.0;

			int bid = aucMax + ((int) (aucMax * bidPercent));
			while (bid >= getMoney())
				bid -= bidAmount;

			if (bid < aucMax)
				bid = aucMax + 1;

			auction.placeBid(this, bid);
		}

		if (!(event instanceof LandOnTileEvent))
			return;

		LandOnTileEvent e = (LandOnTileEvent) event;
		if (e.getPlayer() != this)
			return;

		Tile tile = e.getTile();
		if (tile instanceof SpecialTile) //TODO handle card tiles
		{
			if (tile instanceof IncomeTaxTile)
			{
				int tenPercent = (int) (getMoney() * 0.10);
				if (tenPercent < 200)
					subtractMoney(tenPercent);
				else
					subtractMoney(200);
			}

			endTurn();
			return;
		}

		Player owner = tile.getOwner();
		if (owner != null)
		{
			int rent = tile.getRent();

			if (hasMoney(rent))
			{
				giveMoney(rent, owner);
				endTurn();
			} else
			{
				if (tryGetMoney(rent))
				{
					giveMoney(rent, owner);
					endTurn();
				} else
				{
					subtractMoney(rent); //Subtract anyway, causes NegativeBalanceEvent
					endTurn();
				}
			}
		} else
		{
			if (hasMoney(tile.getCost()))
			{
				buyTile(tile);
				endTurn();
			} else
			{
				//TODO tile goes to auction
			}
		}
	}
}
