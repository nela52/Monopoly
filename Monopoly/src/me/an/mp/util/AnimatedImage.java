package me.an.mp.util;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

public class AnimatedImage
{
	private List<BufferedImage> images;
	private int updateInterval, currImage = 0;
	private BufferedImage image;
	private int tick = 0;

	public AnimatedImage(List<BufferedImage> images, int updateInterval)
	{
		this.images = images;
		this.updateInterval = updateInterval;
		this.image = images.get(UtilMath.r(images.size()));
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public void randomize()
	{
		Collections.shuffle(images);
	}

	public boolean update()
	{
		if (tick++ % (5 * updateInterval) != 0)
			return false;

		if (++currImage >= images.size())
			currImage = 0;
		image = images.get(currImage);
		return true;
	}
}
