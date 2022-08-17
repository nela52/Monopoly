package me.an.mp.tile;

import java.awt.Color;

import org.apache.commons.text.WordUtils;

public enum ColorGroup
{
	//@formatter:off
	PURPLE(0x590C38), 
	LIGHT_BLUE(0x87A5D6), 
	PINK(0xEE3878), 
	ORANGE(0xF57F23), 
	RED(0xEF3A24), 
	YELLOW(0xFDE703), 
	GREEN(0x13A55B), 
	BLUE(0x274EA1),
	RAIL(0x000000),
	UTILITY(0x7A93A0),
	NONE(0xFF00FF);
	//@formatter:on

	private final Color color;

	ColorGroup(int color)
	{
		this.color = new Color(color);
	}

	public Color getColor()
	{
		return color;
	}

	public String getName()
	{
		return WordUtils.capitalizeFully(name().replace("_", " ").toLowerCase());
	}
}
