package aero.smartplane.theory.plot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import aero.smartplane.theory.visual.TestGraph1;
import aero.smartplane.theory.visual.VisualGraph;

public class GraphSelector extends JComboBox<VisualGraph> implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private static final VisualGraph[] graphs = { new TestGraph1(), new DummyGraph() };

	private PlotController controller;

	public GraphSelector(PlotController controller, VisualGraph[] algorithms)
	{
		super(algorithms);
		
		this.controller = controller;
	}

	public static GraphSelector create(PlotController controller)
	{
		GraphSelector selector = new GraphSelector(controller, graphs);
		
		selector.addActionListener(selector);
		
		return (selector);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		GraphSelector selector = (GraphSelector)e.getSource();
		VisualGraph selection = (VisualGraph) selector.getSelectedItem();
		controller.setGraph(selection);
    }

}
