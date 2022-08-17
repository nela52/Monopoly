package me.an.mp.tile;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import me.an.mp.AI;
import me.an.mp.Board;
import me.an.mp.Monopoly;
import me.an.mp.menu.menus.BuyMenu;
import me.an.mp.player.Player;
import me.an.mp.util.SpriteSheet;
import me.an.mp.util.UtilImage;

public class Tile
{
	private String name;
	private ColorGroup color;
	private int cost;
	private int[] rents;
	private int numHouses, houseCost;
	private boolean mortgaged;
	private BufferedImage image;

	private TileSide tileSide;
	private Point pos;

	private Player owner;
	public Tile previous, next;

	public static SpriteSheet TileSheet = Board.TileSheet;
	public static SpriteSheet CornerTileSheet = Board.CornerTileSheet;

	public Tile(String name, ColorGroup color, int cost, int[] rents, int houseCost, TileSide tileSide, int tilePos, BufferedImage image)
	{
		this.name = name;
		this.color = color;
		this.cost = cost;
		this.rents = rents;
		this.houseCost = houseCost;
		this.tileSide = tileSide;
		this.pos = new Point();

		switch (tileSide)
		{
		case BOTTOM:
			this.image = image;
			break;
		case LEFT:
			this.image = UtilImage.rotateImage(image, 90);
			break;
		case TOP:
			this.image = UtilImage.rotateImage(image, 180);
			break;
		case RIGHT:
			this.image = UtilImage.rotateImage(image, -90);
			break;
		}

		if (tileSide == TileSide.BOTTOM)
		{
			pos.x = (Monopoly.TILE_WIDTH * tilePos) + (tilePos != 0 ? Monopoly.TILE_HEIGHT - Monopoly.TILE_WIDTH : 0) + (3 * tilePos);
			pos.y = Monopoly.HEIGHT - Monopoly.TILE_HEIGHT;
		} else if (tileSide == TileSide.LEFT)
		{
			pos.x = 0;
			pos.y = (Monopoly.TILE_WIDTH * tilePos) + (3 * tilePos) + Monopoly.TILE_HEIGHT + 3;
		} else if (tileSide == TileSide.TOP)
		{
			pos.x = (Monopoly.TILE_WIDTH * tilePos) + (tilePos != 0 ? Monopoly.TILE_HEIGHT - Monopoly.TILE_WIDTH : 0) + (3 * tilePos);
			pos.y = 0;
		} else if (tileSide == TileSide.RIGHT)
		{
			pos.x = Monopoly.WIDTH - Monopoly.TILE_HEIGHT;
			pos.y = (Monopoly.TILE_WIDTH * tilePos) + (3 * tilePos) + Monopoly.TILE_HEIGHT + 3;
		}
	}

	public String getName()
	{
		return name;
	}

	public ColorGroup getColorGroup()
	{
		return color;
	}

	public int getCost()
	{
		return cost;
	}

	public int getRent()
	{
		if (numHouses == 0)
			return rents[0];
		return rents[numHouses];
	}

	public int getRents(int houses)
	{
		return rents[houses];
	}

	public int getNumHouses()
	{
		return numHouses;
	}

	public void addHouse()
	{
		//TODO implement, don't allow if dont have all of color group or one mortgaged
		numHouses++;
		owner.subtractMoney(houseCost);
	}

	public void removeHouse()
	{
		//TODO implement
		numHouses--;
		owner.addMoney(houseCost);
	}

	public int getHouseCost()
	{
		return houseCost;
	}

	public int getMortgage()
	{
		return cost / 2;
	}

	public boolean isMortgaged()
	{
		return mortgaged;
	}

	public void setMortgaged(boolean mortgaged)
	{
		//TODO switch to mortgaged image
		this.mortgaged = mortgaged;
		if (mortgaged)
			owner.addMoney(getMortgage());
		else
			owner.subtractMoney(getMortgage() + ((int) (getMortgage() * 0.1))); //Mortgaged + 10% to unmortgage
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public TileSide getTileSide()
	{
		return tileSide;
	}

	public Point getPosition()
	{
		return pos;
	}

	public Player getOwner()
	{
		return owner;
	}

	public void setOwner(Player owner)
	{
		this.owner = owner;
	}

	public void onLandOn(Player player)
	{
		if (player instanceof AI)
			return;

		if (owner == null) //TODO fix
		{
			if (!(this instanceof SpecialTile) && !(this instanceof RailroadTile)) //TODO handle railroad tiles
				Monopoly.Instance.openMenu(new BuyMenu(player, this));
			return;
		}

		int rent = getRent();
		if (player.hasMoney(rent))
			player.giveMoney(rent, owner); //TODO handle player not having enough money
	}

	public void render(Graphics g)
	{
		g.drawImage(image, pos.x, pos.y, Monopoly.Instance);
	}
}
