package me.an.mp.menu.menus;

import java.awt.Graphics;

import me.an.mp.Monopoly;
import me.an.mp.display.DesignManager;
import me.an.mp.menu.Menu;
import me.an.mp.menu.MenuBar;
import me.an.mp.menu.MenuButton;

public class GameMenu extends Menu
{
	private MenuButton doneButton;
	private MenuBar turnTimeBar;

	public GameMenu()
	{
		super(0, 0, Monopoly.WIDTH, Monopoly.HEIGHT);

		doneButton = new MenuButton(DesignManager.createButton("Done"), -1, 0.6, true)
		{
			public void onClick()
			{
				setVisible(false);
				showTurnTimer(false);
				Monopoly.Instance.startNextTurn();
			}
		};
		doneButton.setVisible(false);
		addButton(doneButton);

		turnTimeBar = new MenuBar(-1.0, 0.7, 500, 50, -1, DesignManager.BAR_COLOR, "Time:", false)
		{
			public void onDone()
			{
				setVisible(false);
			}
		};
		turnTimeBar.setVisible(false);
		addBar(turnTimeBar);
	}

	public void showDoneButton(boolean show)
	{
		doneButton.setVisible(show);
	}

	public void showTurnTimer(boolean show)
	{
		turnTimeBar.setVisible(show);
	}

	public MenuBar getTurnTimeBar()
	{
		return turnTimeBar;
	}

	public boolean locContainsButton(int x, int y)
	{
		for (MenuButton button : getButtons())
		{
			if (button.getRectangle().contains(x, y))
				return true;
		}
		return false;
	}

	public void onUpdate()
	{

	}

	public void onRender(Graphics g)
	{

	}
}
