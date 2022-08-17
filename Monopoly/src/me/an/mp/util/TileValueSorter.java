package me.an.mp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.an.mp.player.Player;
import me.an.mp.tile.ColorGroup;
import me.an.mp.tile.Tile;

public class TileValueSorter
{
	private Player player;

	public TileValueSorter(Player player)
	{
		this.player = player;
	}

	public List<ColorGroup> getRanked() //All tile groups owned ranked from least valuable (0) to most valuable (list size)
	{
		List<ColorGroup> ranked = new ArrayList<ColorGroup>();

		int numRail = player.getProperties(ColorGroup.RAIL).size(), numUtil = player.getProperties(ColorGroup.UTILITY).size();
		if (numRail > numUtil)
		{
			ranked.add(ColorGroup.UTILITY);
			ranked.add(ColorGroup.RAIL);
		} else if (numUtil >= numRail) //Prioritize utilities if equal num
		{
			ranked.add(ColorGroup.RAIL);
			ranked.add(ColorGroup.UTILITY);
		}

		List<GroupValue> groupValues = new ArrayList<GroupValue>();
		for (ColorGroup group : ColorGroup.values())
		{
			if (group == ColorGroup.RAIL || group == ColorGroup.UTILITY)
				continue;

			groupValues.add(new GroupValue(group, group.ordinal() + player.getProperties(group).size()));
		}

		Collections.sort(groupValues, new Comparator<GroupValue>()
		{
			public int compare(GroupValue a, GroupValue b)
			{
				if (a.value < b.value)
					return -1;
				else if (a.value > b.value)
					return 1;
				else
					return 0;
			}
		});

		for (GroupValue value : groupValues)
			ranked.add(value.group);

		return ranked;
	}

	public Tile getLastValuable(ColorGroup group)
	{
		Tile leastVal = null;
		for (Tile tile : player.getProperties(group))
		{
			if (leastVal == null || tile.getRent() < leastVal.getRent())
				leastVal = tile;
		}
		return leastVal;
	}
}

class GroupValue
{
	public ColorGroup group;
	public int value;

	public GroupValue(ColorGroup group, int value)
	{
		this.group = group;
		this.value = value;
	}
}
