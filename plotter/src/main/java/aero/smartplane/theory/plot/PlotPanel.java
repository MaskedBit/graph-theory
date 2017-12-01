package aero.smartplane.theory.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import aero.smartplane.theory.visual.VisualGraph;

public class PlotPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private PlotController controller;
	private VisualGraph graph;

	public PlotPanel(PlotController controller)
	{
		this.controller = controller;
	}

	public static PlotPanel create(PlotController controller, int plotWidth, int plotHeight)
	{
		PlotPanel plotPanel = new PlotPanel(controller);
        plotPanel.setOpaque(true);
        plotPanel.setBackground(new Color(248, 213, 131));
        plotPanel.setPreferredSize(new Dimension(plotWidth, plotHeight));
        plotPanel.graph = controller.getGraph();
        
        plotPanel.add(plotPanel.graph, BorderLayout.CENTER);

        return (plotPanel);
	}

	public void newGraph(VisualGraph graph)
	{
		remove(this.graph);
		this.graph = graph;
		add(graph, BorderLayout.CENTER);
	}

}
