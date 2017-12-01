package aero.smartplane.theory.plot;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	private PlotController controller;

	private MenuBar(PlotController controller)
	{
		this.controller = controller;
	}

	public static MenuBar create(PlotController controller, int plotWidth)
	{
		MenuBar menuBar = new MenuBar(controller);
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(154, 165, 127));
        menuBar.setPreferredSize(new Dimension(plotWidth, 20));
        
        return (menuBar);
	}

}
