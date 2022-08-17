package me.an.mp.player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.an.mp.util.SpriteSheet;
import me.an.mp.util.UtilMath;

public class Token
{
	private static SpriteSheet TokenSheet = new SpriteSheet("TokenSheet.png", 64, 64);
	public static Token Thimble = new Token("Thimble", TokenSheet.getSprite(0, 0));
	public static Token Wheelbarrow = new Token("Wheelbarrow", TokenSheet.getSprite(1, 0));
	public static Token Boot = new Token("Boot", TokenSheet.getSprite(2, 0));
	public static Token Dog = new Token("Dog", TokenSheet.getSprite(3, 0));
	public static Token Car = new Token("Car", TokenSheet.getSprite(4, 0));
	public static Token Iron = new Token("Iron", TokenSheet.getSprite(5, 0));
	public static Token Hat = new Token("Hat", TokenSheet.getSprite(6, 0));
	public static Token Ship = new Token("Battleship", TokenSheet.getSprite(7, 0));
	public static Token[] Tokens = { Thimble, Wheelbarrow, Boot, Dog, Car, Iron, Hat, Ship };

	public static SpriteSheet TokenSheetFull = new SpriteSheet("TokenSheetFull.png", 834, 792, 3);
	public static BufferedImage ShipFull = TokenSheetFull.getSprite(0, 0);
	public static BufferedImage BootFull = TokenSheetFull.getSprite(1, 0);
	public static BufferedImage CarFull = TokenSheetFull.getSprite(2, 0);
	public static BufferedImage DogFull = TokenSheetFull.getSprite(3, 0);
	public static BufferedImage HatFull = TokenSheetFull.getSprite(0, 1);
	public static BufferedImage IronFull = TokenSheetFull.getSprite(1, 1);
	public static BufferedImage ThimbleFull = TokenSheetFull.getSprite(2, 1);
	public static BufferedImage WheelbarrowFull = TokenSheetFull.getSprite(3, 1);

	private static List<Token> UsedTokens = new ArrayList<Token>();

	private String name;
	private BufferedImage image;

	public Token(String name, BufferedImage image)
	{
		this.name = name;
		this.image = image;
	}

	public String getName()
	{
		return name;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public static void addUsed(Token token)
	{
		UsedTokens.add(token);
	}

	public static Token random()
	{
		Token token = Tokens[UtilMath.r(Tokens.length)];
		while (UsedTokens.contains(token))
			token = Tokens[UtilMath.r(Tokens.length)];
		UsedTokens.add(token);
		return token;
	}

	public static BufferedImage getFullSize(Token token)
	{
		if (token == Thimble)
			return ThimbleFull;
		else if (token == Wheelbarrow)
			return WheelbarrowFull;
		else if (token == Boot)
			return BootFull;
		else if (token == Dog)
			return DogFull;
		else if (token == Car)
			return CarFull;
		else if (token == Iron)
			return IronFull;
		else if (token == Hat)
			return HatFull;
		else if (token == Ship)
			return ShipFull;
		return null;
	}
}
