package me.an.mp.player;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import me.an.mp.util.UtilMath;

public class PlayerColor
{
	public static PlayerColor Red = new PlayerColor("Red", 0xFF0000);
	public static PlayerColor Cyan = new PlayerColor("Cyan", 0x00FFFF);
	public static PlayerColor Blue = new PlayerColor("Blue", 0x0000FF);
	public static PlayerColor DarkBlue = new PlayerColor("Dark Blue", 0x00008B);
	public static PlayerColor LightBlue = new PlayerColor("Light Blue", 0xADD8E6);
	public static PlayerColor Purple = new PlayerColor("Purple", 0x800080);
	public static PlayerColor Yellow = new PlayerColor("Yellow", 0xFFFF00);
	public static PlayerColor Lime = new PlayerColor("Lime", 0x00FF00);
	public static PlayerColor Magenta = new PlayerColor("Magenta", 0xFF00FF);
	public static PlayerColor Pink = new PlayerColor("Pink", 0xFFC0CB);
	public static PlayerColor White = new PlayerColor("White", 0xFFFFFF);
	public static PlayerColor Silver = new PlayerColor("Silver", 0xC0C0C0);
	public static PlayerColor Gray = new PlayerColor("Gray", 0x808080);
	public static PlayerColor Black = new PlayerColor("Black", 0x000000);
	public static PlayerColor Orange = new PlayerColor("Orange", 0xFFA500);
	public static PlayerColor Brown = new PlayerColor("Brown", 0xA52A2A);
	public static PlayerColor Maroon = new PlayerColor("Maroon", 0x800000);
	public static PlayerColor Green = new PlayerColor("Green", 0x008000);
	public static PlayerColor Olive = new PlayerColor("Olive", 0x808000);
	public static PlayerColor Aquamarine = new PlayerColor("Aquamarine", 0x7FFD4);
	public static PlayerColor[] Colors = new PlayerColor[] { Red, Cyan, Blue, DarkBlue, LightBlue, Purple, Yellow, Lime, Magenta, Pink, White, Silver, Gray, Black, Orange, Brown, Maroon, Green, Olive, Aquamarine };

	private static Set<PlayerColor> UsedColors = new HashSet<PlayerColor>();

	private String name;
	private Color color;

	public PlayerColor(String name, int color)
	{
		this.name = name;
		this.color = new Color(color);
	}

	public String getName()
	{
		return name;
	}

	public Color getColor()
	{
		return color;
	}

	public static PlayerColor random()
	{
		PlayerColor color = Colors[UtilMath.r(Colors.length)];
		while (!UsedColors.add(color))
			color = Colors[UtilMath.r(Colors.length)];
		return color;
	}
}
