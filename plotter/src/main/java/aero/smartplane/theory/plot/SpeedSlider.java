package aero.smartplane.theory.plot;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpeedSlider extends JSlider implements ChangeListener
{
	private static final Logger LOGGER = LogManager.getFormatterLogger(SpeedSlider.class);

	private static final long serialVersionUID = 1L;

	private PlotController controller;

	public SpeedSlider(PlotController controller)
	{
		super(JSlider.HORIZONTAL, PlotController.MIN_FPS, PlotController.MAX_FPS, PlotController.INITIAL_FPS);
		this.controller = controller;
	}

	public static SpeedSlider create(PlotController controller)
	{
        SpeedSlider framesPerSecond = new SpeedSlider(controller);

        framesPerSecond.addChangeListener(framesPerSecond);

        //Turn on labels at major tick marks.
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setMinorTickSpacing(1);
        framesPerSecond.setPaintTicks(true);
        framesPerSecond.setPaintLabels(true);
        framesPerSecond.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        Font font = new Font("Serif", Font.ITALIC, 15);
        framesPerSecond.setFont(font);

        return (framesPerSecond);
	}

	@Override
	public void stateChanged(ChangeEvent event)
	{
		JSlider source = (JSlider)event.getSource();
        if (!source.getValueIsAdjusting())
        {
            int fps = (int)source.getValue();
            controller.setFramesPerSecond(fps);
        }
	}
}
