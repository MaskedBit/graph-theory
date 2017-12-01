package aero.smartplane.theory.plot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class AlgorithmSelector extends JComboBox<AlgorithmChoice> implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private static final AlgorithmChoice[] algorithms = { AlgorithmChoice.DIJKSTRA, AlgorithmChoice.A_STAR };

	private PlotController plotState;

	public AlgorithmSelector(PlotController plotState, AlgorithmChoice[] algorithms)
	{
		super(algorithms);
		
		this.plotState = plotState;
	}

	public static AlgorithmSelector create(PlotController plotState)
	{
		AlgorithmSelector selector = new AlgorithmSelector(plotState, algorithms);
		
		selector.addActionListener(selector);
		
		return (selector);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		AlgorithmSelector selector = (AlgorithmSelector)e.getSource();
		AlgorithmChoice selection = (AlgorithmChoice) selector.getSelectedItem();
		plotState.setAlgorithmChoice(selection);
    }

}
