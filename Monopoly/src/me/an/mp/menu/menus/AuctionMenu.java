package me.an.mp.menu.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import me.an.mp.Auction;
import me.an.mp.Defaults;
import me.an.mp.Monopoly;
import me.an.mp.display.DesignManager;
import me.an.mp.event.EventHandler;
import me.an.mp.event.MonoEvent;
import me.an.mp.event.MonoListener;
import me.an.mp.event.events.auction.AuctionEndEvent;
import me.an.mp.event.events.auction.CycleBidderEvent;
import me.an.mp.event.events.auction.PlaceBidEvent;
import me.an.mp.event.events.auction.PlayerForfeitEvent;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuBar;
import me.an.mp.menu.MenuBox;
import me.an.mp.menu.MenuButton;
import me.an.mp.menu.MenuPosition;
import me.an.mp.player.Player;
import me.an.mp.tile.PropertyCards;
import me.an.mp.util.UtilImage;
import me.an.mp.util.UtilText;

public class AuctionMenu extends Menu implements MonoListener
{
	public static final int END_TIME = 3;

	private Auction auction;
	private Player player;

	private Map<Player, Integer> sortedBidders;
	private Player currentBidder;
	private int endTime = END_TIME, tick = 0;
	private boolean auctionOver = false;

	private Map<Player, BufferedImage> playerImages;

	private MenuBar timeBar;
	private MenuBox auctionBox;
	private MenuButton bidButton;
	private BufferedImage tileImage;

	public AuctionMenu(Auction auction, Player player)
	{
		super(DEFAULT_BACKGROUND, MenuPosition.CENTER, MenuPosition.CENTER);
		this.auction = auction;
		this.player = player;
		this.sortedBidders = auction.getSortedBidders();
		this.currentBidder = auction.getCurrentBidder();
		this.playerImages = new HashMap<Player, BufferedImage>();

		for (Player bidder : sortedBidders.keySet())
			playerImages.put(bidder, bidder.getToken().getImage());

		timeBar = addBar(new MenuBar(getX(), getY() - DesignManager.BAR_HEIGHT, getWidth(), DesignManager.BAR_HEIGHT, Defaults.AUCTION_TIME, DesignManager.BAR_COLOR, "Time:")
		{
			public void onDone()
			{

			}
		});

		tileImage = PropertyCards.getPropertyCard(auction.getTile(), 3);
		int centerX = getX() + (getWidth() / 2) - (tileImage.getWidth() / 2);
		int tileBtnY = getY() + DesignManager.OFFSET;

		addButton(new MenuButton(centerX, tileBtnY, tileImage, false)
		{
			public void onClick()
			{

			}
		});

		final int boxWidth = 200, boxHeight = 50;
		int boxX = getX() + (getWidth() / 2) - (boxWidth / 2);
		int boxY = getY() + getHeight() - boxHeight * 3;

		auctionBox = addTextBox(new MenuBox(boxX, boxY, boxWidth, boxHeight, "Bid:", true)
		{
			public boolean onDone()
			{
				String text = getText();
				if (text.isEmpty())
					return false;

				int bid = Integer.parseInt(text);
				if (bid <= auction.getMaxBid() || bid > player.getMoney())
					return false;

				auction.placeBid(player, bid);
				setVisible(false);
				bidButton.setVisible(false);
				return true;
			}
		});
		auctionBox.setVisible(player == auction.getCurrentBidder() ? true : false);

		BufferedImage bidButtonImg = DesignManager.createButton("Bid");
		int btnX = getX() + (getWidth() / 2) - (bidButtonImg.getWidth() / 2);
		int btnY = boxY + bidButtonImg.getHeight();

		bidButton = addButton(new MenuButton(btnX, btnY, bidButtonImg, true)
		{
			public void onClick()
			{
				if (auctionBox.onDone())
					setVisible(false);
			}
		});
		bidButton.setVisible(player == auction.getCurrentBidder() ? true : false);

		EventHandler.Instance.addListener(this);
	}

	public void onUpdate()
	{
		double percent = auction.getTime() / (Defaults.AUCTION_TIME * 60.0);
		timeBar.setPercent(percent);

		if (auctionOver)
		{
			if (tick++ % 60 == 0)
				endTime--;

			if (endTime == 0)
				setDone(true);
		}
	}

	final int Y_OFFSET = 20;

	public void onRender(Graphics g)
	{
		if (!auctionOver)
		{
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(g.getFont().deriveFont(60f));

			int yPos = getY() + tileImage.getHeight() + DesignManager.OFFSET;
			for (Player p : sortedBidders.keySet())
			{
				int xPos = getX() + DesignManager.OFFSET;

				BufferedImage tokenImage = playerImages.get(p);

				String text = player == p ? "You" : p.getColor().getName();
				int textHeight = UtilText.textHeight(g, text);

				yPos += textHeight + Y_OFFSET;
				g.drawImage(tokenImage, xPos, yPos - textHeight, Monopoly.Instance);
				xPos += tokenImage.getWidth();

				g.setColor(p.getColor().getColor());
				g.drawString(text, xPos, yPos);

				String bidText = "$" + sortedBidders.get(p);
				int textWidth = g.getFontMetrics(g.getFont()).stringWidth(bidText);
				g.drawString(bidText, getX() + getWidth() - textWidth - DesignManager.OFFSET, yPos);

				if (p == currentBidder)
				{
					g.setColor(Color.BLACK);
					g.drawRect(xPos - tokenImage.getWidth(), yPos - textHeight, getWidth() - (DesignManager.OFFSET * 2), tokenImage.getHeight());
				}
			}
		} else
		{
			String text = (player == auction.getWinner() ? "You" : auction.getWinner().getColor().getName()) + " won the auction!";
			Color color = player == auction.getWinner() ? Color.YELLOW : Color.GRAY;

			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setColor(color);

			g.setFont(g.getFont().deriveFont(84f));
			Rectangle2D stringBounds = g.getFontMetrics(g.getFont()).getStringBounds(text, g);

			int textX = (int) ((Monopoly.WIDTH / 2) - (stringBounds.getWidth() / 2));
			int textY = (int) ((Monopoly.HEIGHT / 2) - (stringBounds.getHeight() / 2));
			g.drawString(text, textX, textY);
		}
	}

	public void onEvent(MonoEvent event)
	{
		if (event instanceof PlaceBidEvent)
			sortedBidders = auction.getSortedBidders();

		if (event instanceof CycleBidderEvent)
		{
			currentBidder = auction.getCurrentBidder();
			if (currentBidder == player)
			{
				auctionBox.setVisible(true);
				bidButton.setVisible(true);
			}
		}

		if (event instanceof PlayerForfeitEvent)
		{
			PlayerForfeitEvent e = (PlayerForfeitEvent) event;
			Player forfeited = e.getPlayer();
			playerImages.put(forfeited, UtilImage.recolorImage(forfeited.getToken().getImage(), new Color(255, 0, 0, 123)));
		}

		if (event instanceof AuctionEndEvent)
		{
			auctionOver = true;
			auctionBox.setVisible(false);
			bidButton.setVisible(false);
		}
	}

	public void onForceClose() //Dont allow force close
	{

	}
}
