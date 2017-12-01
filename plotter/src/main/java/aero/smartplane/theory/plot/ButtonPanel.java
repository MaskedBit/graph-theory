package aero.smartplane.theory.plot;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.glass.events.KeyEvent;

public class ButtonPanel extends JPanel implements ActionListener
{
	private static final Logger LOGGER = LogManager.getFormatterLogger(ButtonPanel.class);

	private static final long serialVersionUID = 1L;

	private static final String START_COMMAND = "start";
	private static final String STOP_COMMAND = "stop";
	private static final String STEP_COMMAND = "step";
	private static final String RESTART_COMMAND = "restart";

	private PlotController controller;
	
	private JButton startButton;
	private JButton stopButton;
	private JButton stepButton;
	private JButton restartButton;

	public ButtonPanel(PlotController controller)
	{
		this.controller = controller;
	}

	public static ButtonPanel create(PlotController controller) throws IOException
	{
		ImageIcon startIcon = createIcon("/images/Play24.gif");
		ImageIcon stopIcon = createIcon("/images/Pause24.gif");
		ImageIcon stepIcon = createIcon("/images/StepForward24.gif");
		ImageIcon restartIcon = createIcon("/images/Rewind24.gif");

		ButtonPanel controls = new ButtonPanel(controller);
		controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

		// Start
		controls.startButton = new JButton(startIcon);
		controls.startButton.setMnemonic(KeyEvent.VK_R);
		controls.startButton.setActionCommand(START_COMMAND);
		controls.startButton.setToolTipText("Run");
		controls.startButton.addActionListener(controls);
		
		controls.add(controls.startButton);

		// Stop
		controls.stopButton = new JButton(stopIcon);
		controls.stopButton.setMnemonic(KeyEvent.VK_P);
		controls.stopButton.setActionCommand(STOP_COMMAND);
		controls.stopButton.setToolTipText("Pause");
		controls.stopButton.addActionListener(controls);
		
		controls.add(controls.stopButton);

		// Step
		controls.stepButton = new JButton(stepIcon);
		controls.stepButton.setMnemonic(KeyEvent.VK_S);
		controls.stepButton.setActionCommand(STEP_COMMAND);
		controls.stepButton.setToolTipText("Step");
		controls.stepButton.addActionListener(controls);
		
		controls.add(controls.stepButton);

		// Restart
		controls.restartButton = new JButton(restartIcon);
		controls.restartButton.setMnemonic(KeyEvent.VK_W);
		controls.restartButton.setActionCommand(RESTART_COMMAND);
		controls.restartButton.setToolTipText("Restart");
		controls.restartButton.addActionListener(controls);
		
		controls.add(controls.restartButton);
		
        return (controls);
	}

	private static ImageIcon createIcon(String path) throws IOException
	{
		InputStream imageStream = ButtonPanel.class.getResourceAsStream(path);
        if (imageStream != null)
        {
            Image image = ImageIO.read(imageStream);
            
            return (new ImageIcon(image));
        }
        else
        {
            LOGGER.fatal("Couldn't find file: \"%s\"", path);

            return null;
        }
	}

	public void actionPerformed(ActionEvent event)
	{
		LOGGER.debug("ButtonPanel.actionPerformed() - action=\"%s\"", event.getActionCommand());

		switch (event.getActionCommand())
		{
		case START_COMMAND:
			controller.run();
			break;

		case STOP_COMMAND:
			controller.stop();
			break;

		case STEP_COMMAND:
			controller.step();
			break;

		case RESTART_COMMAND:
			controller.restart();
			break;
		}
	}

}
