package me.an.mp.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class UtilMath
{
	public static Random random = new Random();

	public static int r(int range)
	{
		return random.nextInt(range);
	}

	public static int r(int lower, int upper)
	{
		return ThreadLocalRandom.current().nextInt(lower, upper + 1);
	}

	public static int scaleBetween(int unscaledNum, int minAllowed, int maxAllowed, int min, int max)
	{
		return (maxAllowed - minAllowed) * (unscaledNum - min) / (max - min) + minAllowed;
	}
}
