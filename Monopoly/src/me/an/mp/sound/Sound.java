package me.an.mp.sound;

import java.io.IOException;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sound
{
	private String path;
	private Player player;

	public Sound(String name)
	{
		this.path = Sound.class.getResource("/sounds/" + name).toString();
	}

	public String getPath()
	{
		return path;
	}

	public void play()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					player = new Player(new URL(path).openStream());
					player.play();

					while (!player.isComplete())
						Thread.sleep(1);

					player.close();
				} catch (JavaLayerException | IOException | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void stop()
	{
		player.close();
	}
}
