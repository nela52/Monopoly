package me.an.mp.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.an.mp.Monopoly;
import me.an.mp.display.DesignManager;

public class UtilImage
{
	public static BufferedImage loadImage(String path)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(UtilImage.class.getResourceAsStream("/" + path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return image;
	}

	public static BufferedImage shrinkImage(BufferedImage image, int scale)
	{
		Image scaledImage = image.getScaledInstance(image.getWidth() / scale, image.getHeight() / scale, Image.SCALE_DEFAULT);
		return toBufferedImage(scaledImage);
	}

	public static BufferedImage loadImage(String path, int scale)
	{
		try
		{
			BufferedImage image = ImageIO.read(UtilImage.class.getResourceAsStream("/" + path));
			return shrinkImage(image, scale);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage toBufferedImage(Image image)
	{
		if (image instanceof BufferedImage)
			return (BufferedImage) image;

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		return bufferedImage;
	}

	public static BufferedImage rotateImage(BufferedImage image, double degrees)
	{
		double sin = Math.abs(Math.sin(Math.toRadians(degrees)));
		double cos = Math.abs(Math.cos(Math.toRadians(degrees)));

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		int newWidth = (int) Math.floor(width * cos + height * sin);
		int newHeight = (int) Math.floor(height * cos + width * sin);

		BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		g2d.translate((newWidth - width) / 2, (newHeight - height) / 2);
		g2d.rotate(Math.toRadians(degrees), width / 2, height / 2);
		g2d.drawRenderedImage(toBufferedImage(image), null);
		g2d.dispose();

		return bufferedImage;
	}

	public static BufferedImage deepCopy(BufferedImage image)
	{
		ColorModel colorModel = image.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage recolorImage(BufferedImage image, Color color)
	{
		BufferedImage imageCopy = deepCopy(image);
		Graphics g = imageCopy.getGraphics();
		for (int x = 0; x < imageCopy.getWidth(); x++)
		{
			for (int y = 0; y < imageCopy.getHeight(); y++)
			{
				int pixel = imageCopy.getRGB(x, y);
				if ((pixel >> 24) == 0x00) // Pixel is transparent
					continue;

				g.setColor(color);
				g.fillRect(x, y, 1, 1);
			}
		}
		return imageCopy;
	}

	public static BufferedImage darkenImage(BufferedImage image, boolean max)
	{
		return recolorImage(image, max ? DesignManager.DARKEN_MAX : DesignManager.DARKEN_MIN);
	}

	public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight)
	{
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, newWidth, newHeight, Monopoly.Instance);
		return newImage;
	}

	public static BufferedImage scaleImage(BufferedImage image, double scale)
	{
		int newWidth = (int) (image.getWidth() * scale);
		int newHeight = (int) (image.getHeight() * scale);
		return resizeImage(image, newWidth, newHeight);
	}

	public static BufferedImage changeOpacity(BufferedImage image, float opacity)
	{
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = newImage.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.drawImage(image, null, 0, 0);
		g2d.dispose();
		return newImage;
	}
}
