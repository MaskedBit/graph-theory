package aero.smartplane.theory.plot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import aero.smartplane.theory.visual.VisualGraph;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Plotter extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getFormatterLogger(Plotter.class);

	public static final int PLOT_WIDTH = 400;
	public static final int PLOT_HEIGHT = 400;

	private PlotController controller = new PlotController(this);
	private PlotPanel plotPanel;

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
            public void run()
            {
                createAndShowGUI();
            }
        });
	}

	public Plotter(String label)
	{
		super(label);
	}

	private static void createAndShowGUI()
	{
		try
		{
	        //Create and set up the window.
			Plotter plotter = new Plotter("Route Plotter");
	        plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	        //Set the menu bar and add the label to the content pane.
	        plotter.setJMenuBar(MenuBar.create(plotter.controller, PLOT_WIDTH));
	        plotter.getContentPane().add(OptionsPanel.create(plotter.controller, PLOT_WIDTH), BorderLayout.NORTH);
	        plotter.plotPanel = PlotPanel.create(plotter.controller, PLOT_WIDTH, PLOT_HEIGHT);
	        plotter.getContentPane().add(plotter.plotPanel, BorderLayout.CENTER);
	        plotter.getContentPane().add(ControlsPanel.create(plotter.controller, PLOT_WIDTH), BorderLayout.SOUTH);
	
	        //Display the window.
	        plotter.pack();
	        plotter.setVisible(true);
		}
		catch (IOException e)
		{
			LOGGER.fatal("*** Cannot create user interface - error is \"%s\"", e.getMessage());
		}
    }

	public void newGraph(VisualGraph graph)
	{
		plotPanel.newGraph(graph);
		revalidate();
		repaint();
	}

}
