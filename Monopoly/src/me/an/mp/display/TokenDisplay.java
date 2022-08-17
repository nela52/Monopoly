package me.an.mp.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.an.mp.Monopoly;
import me.an.mp.event.EventHandler;
import me.an.mp.event.MonoEvent;
import me.an.mp.event.MonoListener;
import me.an.mp.event.events.CycleTurnEvent;
import me.an.mp.event.events.player.BalanceChangeEvent;
import me.an.mp.menu.menus.PropertyMenu;
import me.an.mp.player.Player;
import me.an.mp.player.Token;
import me.an.mp.sound.Sounds;
import me.an.mp.util.UtilImage;
import me.an.mp.util.UtilText;

public class TokenDisplay implements MouseListener, MouseMotionListener, MonoListener
{
	public static final int INFO_DISPLAY_TIME = 2;

	private List<Player> players, playerTurns;

	private Map<Player, BufferedImage> origImages;
	private Map<Player, BufferedImage> displayImages;
	private Map<Player, BufferedImage> highlightImages;
	private Map<Player, BufferedImage> hoverImages;
	private Map<Player, Rectangle> displayPos;
	private Map<DisplayInfo, Integer> displayInfos;

	private Map<Player, Point> mouseOver;
	private int centerX, centerY;

	private int tick = 0;

	public TokenDisplay()
	{
		Monopoly.Instance.addMouseListener(this);
		Monopoly.Instance.addMouseMotionListener(this);
		EventHandler.Instance.addListener(this);

		players = Monopoly.Instance.getPlayers();
		playerTurns = Monopoly.Instance.getTurnOrder();

		origImages = new HashMap<Player, BufferedImage>();
		displayImages = new HashMap<Player, BufferedImage>();
		highlightImages = new HashMap<Player, BufferedImage>();
		hoverImages = new HashMap<Player, BufferedImage>();
		displayPos = new HashMap<Player, Rectangle>();
		displayInfos = new HashMap<DisplayInfo, Integer>();
		mouseOver = new HashMap<Player, Point>();

		for (Player player : players)
		{
			BufferedImage displayImage = UtilImage.shrinkImage(Token.getFullSize(player.getToken()), players.size() / 2);
			origImages.put(player, displayImage);
			displayImages.put(player, displayImage);
			highlightImages.put(player, UtilImage.recolorImage(displayImage, DesignManager.SELECT_COLOR));
			hoverImages.put(player, UtilImage.recolorImage(displayImage, DesignManager.HOVER_COLOR));

			if (player == Monopoly.Instance.getTurnPlayer())
				displayImages.put(player, highlightImages.get(player));
		}

		int totalWidth = 0, height = displayImages.get(players.get(0)).getHeight();
		for (Player player : playerTurns)
			totalWidth += displayImages.get(player).getWidth();

		centerX = (Monopoly.WIDTH / 2) - (totalWidth / 2);
		for (int i = 0; i < playerTurns.size() / 2; i++)
			centerX -= displayImages.get(playerTurns.get(i)).getWidth() / 2;

		centerY = (Monopoly.HEIGHT / 2) - ((int) (height * 1.5));

		for (Player player : playerTurns)
			displayPos.put(player, new Rectangle(centerX += displayImages.get(player).getWidth(), centerY, displayImages.get(player).getWidth(), displayImages.get(player).getHeight()));
	}

	public void update()
	{
		if (tick++ % 60 == 0)
		{
			Set<DisplayInfo> infos = new HashSet<DisplayInfo>(displayInfos.keySet());
			for (DisplayInfo info : infos)
			{
				displayInfos.put(info, displayInfos.get(info) - 1);
				if (displayInfos.get(info) == 0)
					displayInfos.remove(info);
			}
		}
	}

	public void render(Graphics g)
	{
		for (Player player : displayPos.keySet())
		{
			Rectangle rect = displayPos.get(player);
			g.drawImage(displayImages.get(player), rect.x, rect.y, Monopoly.Instance);
			g.setColor(player.getColor().getColor());

			Rectangle colorRect = new Rectangle(rect.x + (rect.width / 2) - (100 / 2), rect.y - 10, 100, 10);
			g.fillRect(colorRect.x, colorRect.y, colorRect.width, colorRect.height);

			for (DisplayInfo info : displayInfos.keySet())
			{
				if (info.player != player)
					continue;

				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				String text = info.info;
				if (text.startsWith("+"))
					g.setColor(Color.GREEN);
				else if (text.startsWith("-"))
					g.setColor(Color.RED);
				else
					g.setColor(Color.WHITE);

				int textWidth = g.getFontMetrics(g.getFont()).stringWidth(text);
				int offset = (int) (textWidth * 0.1);

				g.setFont(g.getFont().deriveFont(72f));
				g.drawString(info.info, colorRect.x - offset, colorRect.y - offset);
			}
		}

		//Have to loop twice or rendering issue (text goes behind token images)
		for (Player player : displayPos.keySet())
		{
			Point mousePoint = mouseOver.get(player);
			if (mousePoint != null)
			{
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				g.setColor(player.getColor().getColor());
				g.setFont(g.getFont().deriveFont(36f));
				String text = player != Monopoly.Instance.getPlayer() ? player.getColor().getName() : player.getColor().getName() + " (You)";
				g.drawString(text, mousePoint.x, mousePoint.y);

				g.setFont(g.getFont().deriveFont(24f));

				int xPos = mousePoint.x + g.getFontMetrics(g.getFont()).stringWidth("10");
				int yPos = mousePoint.y + UtilText.textHeight(g, text);

				String moneyText = "$" + player.getMoney();
				g.setColor(Color.GREEN);
				g.drawString(moneyText, xPos, yPos);

				Map<String, Color> propertyText = player.getPropertiesOverview();
				if (propertyText == null)
				{
					String noPropText = "No properties";
					g.setColor(Color.GRAY);
					g.drawString(noPropText, xPos, yPos += UtilText.textHeight(g, noPropText));
					return;
				}

				for (String property : propertyText.keySet())
				{
					g.setColor(propertyText.get(property));
					g.drawString(property, xPos, yPos += UtilText.textHeight(g, property));
				}
			}
		}
	}

	public void mouseDragged(MouseEvent e)
	{

	}

	public void mouseMoved(MouseEvent e)
	{
		if (Monopoly.Instance.isMenuOpen() || Monopoly.Instance.getDice().onDice(e.getX(), e.getY()) || Monopoly.Instance.getGameMenu().locContainsButton(e.getX(), e.getY()))
			return;

		for (Player player : displayPos.keySet())
		{
			Rectangle rect = displayPos.get(player);
			if (rect.contains(e.getX(), e.getY()))
			{
				if (displayImages.get(player) != highlightImages.get(player))
					displayImages.put(player, hoverImages.get(player));
				mouseOver.put(player, new Point(e.getX(), e.getY()));

				for (Player other : displayImages.keySet())
				{
					if (other != player)
					{
						if (displayImages.get(other) != highlightImages.get(other))
							displayImages.put(other, origImages.get(other));
						mouseOver.put(other, null);
					}
				}

				break;
			} else
			{
				if (displayImages.get(player) != highlightImages.get(player))
					displayImages.put(player, origImages.get(player));
				mouseOver.put(player, null);
			}
		}
	}

	public void mouseClicked(MouseEvent e)
	{
		if (Monopoly.Instance.isMenuOpen() || Monopoly.Instance.getDice().onDice(e.getX(), e.getY()))
			return;

		for (Player player : displayPos.keySet())
		{
			Rectangle rect = displayPos.get(player);
			if (rect.contains(e.getX(), e.getY()))
			{
				if (player.getProperties().size() == 0)
					return;

				Monopoly.Instance.openMenu(new PropertyMenu(player));
				Sounds.BUTTON_CLICK.play();
				break;
			}
		}
	}

	public void mousePressed(MouseEvent e)
	{

	}

	public void mouseReleased(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void onEvent(MonoEvent event)
	{
		if (event instanceof CycleTurnEvent)
		{
			CycleTurnEvent e = (CycleTurnEvent) event;
			Player player = e.getPlayer();

			for (Player other : displayImages.keySet())
				displayImages.put(other, origImages.get(other));

			displayImages.put(player, highlightImages.get(player));
		}

		if (event instanceof BalanceChangeEvent)
		{
			BalanceChangeEvent e = (BalanceChangeEvent) event;
			int amount = e.getAmount();
			displayInfos.put(new DisplayInfo(e.getPlayer(), amount > 0 ? "+" + amount : "" + amount), INFO_DISPLAY_TIME);
		}
	}
}

class DisplayInfo
{
	public Player player;
	public String info;

	public DisplayInfo(Player player, String info)
	{
		this.player = player;
		this.info = info;
	}
}
