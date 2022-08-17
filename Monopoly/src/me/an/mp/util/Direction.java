package me.an.mp.util;

public enum Direction
{
	NORTH, SOUTH, EAST, WEST;

	public Direction opposite()
	{
		switch (this)
		{
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		}
		return null;
	}
}
