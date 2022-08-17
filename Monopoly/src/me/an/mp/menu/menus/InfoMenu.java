package me.an.mp.menu.menus;

import java.awt.Graphics;

import me.an.mp.Defaults;
import me.an.mp.display.DesignManager;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuPosition;

public class InfoMenu extends Menu
{
	private int startY, endY, tick = 0, viewTime = Defaults.INFO_VIEW_TIME;
	private boolean doneMoving = false;
	public static final int UP_STEP = 3, DOWN_STEP = 5;

	public InfoMenu(String text)
	{
		super(DesignManager.createInfoScroll(text), MenuPosition.CENTER, MenuPosition.RIGHT);
		endY = getY();
		setY(startY = getY() + getHeight());
	}

	public void onUpdate()
	{
		if (!doneMoving)
		{
			if (getY() > endY)
			{
				if (getY() - UP_STEP < endY)
					setY(endY);
				else
					setY(getY() - UP_STEP);
			}

			if (getY() == endY)
				doneMoving = true;
		} else
		{
			if (tick++ % 60 == 0 && viewTime > 0)
				viewTime--;

			if (viewTime == 0)
			{
				if (getY() < startY)
				{
					if (getY() + DOWN_STEP > startY)
						setY(startY);
					else
						setY(getY() + DOWN_STEP);
				}

				if (getY() == startY)
					setDone(true);
			}
		}
	}

	public void onRender(Graphics g)
	{

	}
}
