package aero.smartplane.theory.plot;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class ControlsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private PlotController controller;

	public ControlsPanel(PlotController controller)
	{
		this.controller = controller;
	}

	public static ControlsPanel create(PlotController controller, int plotWidth) throws IOException
	{
		ControlsPanel controls = new ControlsPanel(controller);
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
        controls.setOpaque(true);
//        controls.setBackground(new Color(248, 131, 131));
//        controls.setPreferredSize(new Dimension(plotWidth, 110));
        
        controls.add(Box.createRigidArea(new Dimension(0, 8)));

        controls.add(ButtonPanel.create(controller));

        controls.add(Box.createRigidArea(new Dimension(0, 10)));
        controls.add(new JSeparator());

        JLabel sliderLabel = new JLabel("Frames Per Second", JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controls.add(sliderLabel);

        controls.add(SpeedSlider.create(controller));

        return (controls);
	}

}
