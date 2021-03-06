package aero.smartplane.theory.plot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import aero.smartplane.theory.astar.AStar;
import aero.smartplane.theory.dijkstra.Dijkstra;
import aero.smartplane.theory.graphs.Algorithm;
import aero.smartplane.theory.graphs.AlgorithmState;
import aero.smartplane.theory.graphs.Heuristic;
import aero.smartplane.theory.graphs.Node;
import aero.smartplane.theory.visual.TestGraph1;
import aero.smartplane.theory.visual.VisualGraph;

public class PlotController implements ActionListener
{
	public static final int MIN_FPS = 0;
	public static final int MAX_FPS = 30;
	public static final int INITIAL_FPS = 15;

	private static final Logger LOGGER = LogManager.getFormatterLogger(PlotController.class);

	private Plotter plotter;
	private AlgorithmChoice algorithmChoice = AlgorithmChoice.DIJKSTRA;
	private Algorithm algorithm;
	private AlgorithmState state = AlgorithmState.CONTINUE;
	private VisualGraph graph = new TestGraph1();
	private int framesPerSecond = INITIAL_FPS;
	private int delay = 0;
	private Timer timer;

	public PlotController(Plotter plotter)
	{
		this.plotter = plotter;
		delay = 1000 / INITIAL_FPS;
		timer = new Timer(delay, this);
		algorithm = new Dijkstra(graph, graph.getStart(), graph.getGoal(), graph);
	}

	public AlgorithmChoice getAlgorithmChoice() {
		return algorithmChoice;
	}

	public void setAlgorithmChoice(AlgorithmChoice algorithmChoice)
	{
		LOGGER.trace("setAlgorithmChoice(%s)", algorithmChoice.getLabel());

		if (algorithmChoice != this.algorithmChoice)
		{
			stop();
			this.algorithmChoice = algorithmChoice;
			setAlgorithm(algorithmChoice);
		}
	}

	private void setAlgorithm(AlgorithmChoice algorithmChoice)
	{
		LOGGER.trace("setAlgorithm(%s)", algorithm.toString());

		switch (algorithmChoice)
		{
		case A_STAR:
			algorithm = new AStar(graph, graph.getStart(), graph.getGoal(), graph, new Heuristic()
			{

				@Override
				public double estimate(Node from, Node to)
				{
					return (graph.distance(from,  to));
				}

			});
			break;

		case DIJKSTRA:
		default:
			algorithm = new Dijkstra(graph, graph.getStart(), graph.getGoal(), graph);
			break;
		}

		graph.reset();
		plotter.newGraph(graph);
		graph.repaint();
	}

	public VisualGraph getGraph() {
		return graph;
	}

	public void setGraph(VisualGraph graph)
	{
		LOGGER.trace("setGraph(%s)", graph.toString());

		if (graph != this.graph)
		{
			stop();
			this.graph = graph;
			setAlgorithm(algorithmChoice);
			plotter.newGraph(graph);
			graph.repaint();
		}
	}

	public int getFramesPerSecond()
	{
		return (framesPerSecond);
	}

	public void setFramesPerSecond(int framesPerSecond)
	{
		LOGGER.trace("setFramesPerSecond(%d)", framesPerSecond);

		this.framesPerSecond = framesPerSecond;

		timer.stop();

		if (framesPerSecond != 0)
		{
			delay = 1000 / framesPerSecond;
			timer.setDelay(delay);
			timer.start();
		}
	}

	public void stop()
	{
		LOGGER.trace("stop()");

		timer.stop();
	}

	public void step()
	{
		LOGGER.trace("step()");

		AlgorithmState state = algorithm.step();
		graph.repaint();
		
		switch (state)
		{
		case SUCCESS:
			stop();
			graph.repaint();
			break;

		case FAIL:
			stop();
			break;

		case CONTINUE:
		default:
			break;
		}
	}

	public void run()
	{
		LOGGER.trace("run()");

		if (state == AlgorithmState.CONTINUE)
		{
			timer.start();
		}
	}

	public void restart()
	{
		LOGGER.trace("restart()");

		stop();
		graph.reset();
		algorithm.reset();
	}

	public void debugPrint()
	{
		LOGGER.debug("---- State:");
		LOGGER.debug("  algorithmChoice=%s", algorithmChoice.getLabel());
		// LOGGER.debug("  algorithm: %s", algorithm.toString());
		LOGGER.debug("  graph=%s", graph.toString());
		LOGGER.debug("  setFramesPerSecond= %d", framesPerSecond);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		step();
	}

}
