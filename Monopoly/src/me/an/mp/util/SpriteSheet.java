package me.an.mp.util;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class SpriteSheet
{
	private BufferedImage image;
	private int spriteWidth, spriteHeight, scaling = -1;

	public SpriteSheet(String path, int spriteWidth, int spriteHeight)
	{
		this.image = UtilImage.loadImage(path);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public SpriteSheet(String path, int spriteWidth, int spriteHeight, int scaling)
	{
		this.image = UtilImage.loadImage(path);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.scaling = scaling;
	}

	public BufferedImage getSprite(int x, int y)
	{
		Image subImage = image.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
		BufferedImage image = UtilImage.toBufferedImage(subImage);
		if (scaling == -1)
			return image;
		return UtilImage.shrinkImage(image, scaling);
	}

	public BufferedImage getSpriteRotated(int x, int y, double degrees)
	{
		BufferedImage sprite = getSprite(x, y);
		return UtilImage.rotateImage(sprite, degrees);
	}
}
