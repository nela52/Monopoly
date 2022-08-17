package me.an.mp.event;

import java.util.HashSet;
import java.util.Set;

public class EventHandler
{
	private Set<MonoListener> listeners;

	public static EventHandler Instance;

	public static void initialize()
	{
		Instance = new EventHandler();
	}

	private EventHandler()
	{
		listeners = new HashSet<MonoListener>();
	}

	public void addListener(MonoListener listener)
	{
		listeners.add(listener);
	}

	public void callEvent(MonoEvent event)
	{
		for (MonoListener listener : listeners)
			listener.onEvent(event);
	}
}
