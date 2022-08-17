package me.an.mp.card;

public enum CardType
{
	CHEST("Community Chest"), CHANCE("Chance");

	private final String name;

	CardType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
