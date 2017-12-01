package aero.smartplane.theory.plot;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static OptionsPanel create(PlotController controller, int plotWidth)
	{
		OptionsPanel options = new OptionsPanel();
		options.setLayout(new GridLayout(2,3));
        options.setOpaque(true);
        options.setBackground(new Color(131, 213, 248));
        options.setPreferredSize(new Dimension(plotWidth, 60));

        JLabel algorithmLabel = new JLabel("Algorithm: ", JLabel.RIGHT);
        algorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.add(algorithmLabel);
        options.add(AlgorithmSelector.create(controller));
        options.add(new JLabel(""));	// Spacer

        JLabel graphLabel = new JLabel("Graph: ", JLabel.RIGHT);
        graphLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        options.add(graphLabel);
        options.add(GraphSelector.create(controller));
        options.add(new JLabel(""));	// Spacer
        
        return (options);
	}

}
