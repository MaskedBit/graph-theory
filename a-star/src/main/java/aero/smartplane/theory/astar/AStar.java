package aero.smartplane.theory.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import aero.smartplane.theory.graphs.Algorithm;
import aero.smartplane.theory.graphs.AlgorithmState;
import aero.smartplane.theory.graphs.CostFunction;
import aero.smartplane.theory.graphs.Graph;
import aero.smartplane.theory.graphs.Heuristic;
import aero.smartplane.theory.graphs.Node;
import aero.smartplane.theory.graphs.NodeState;

/**
 * Object-oriented implementation of A* algorithm.
 * 
 * Implementation notes:
 * 1)  closedSet is implemented as Node.state value VISITED
 * 2)  openSet is implemented with a Java PriorityQueue
 * 
 * @author Steve
 *
 */
public class AStar implements Algorithm
{
	private static final int INITIAL_SIZE = 101;

	private final PriorityQueue<Node> openSet = new PriorityQueue<Node>(INITIAL_SIZE, new Comparator<Node>()
			{

				@Override
				public int compare(Node node1, Node node2)
				{
					return (Double.compare(node1.getScore(), node2.getScore()));
				}
				
			});
	private final Graph graph;
	private final Node start;
	private final Node destination;
	private final CostFunction cost;
	private final Heuristic heuristic;

	public AStar(Graph graph, Node start, Node destination, CostFunction cost, Heuristic heuristic)
	{
		this.graph = graph;
		this.openSet.add(start);
		this.start = start;
		this.destination = destination;
		this.cost = cost;
		this.heuristic = heuristic;

		start.setScore(heuristic.estimate(start, destination));
		start.setEstimate(0.0);
		start.setPrevious(null);
	}

	@Override
	public AlgorithmState step()
	{
		Node current = openSet.poll();

		if (current == null)
		{
			return (AlgorithmState.FAIL);
		}

		current.setState(NodeState.VISITED);

		if (current.equals(destination))
		{
			return (AlgorithmState.SUCCESS);
		}

		for (Node next : graph.getNeighbors(current))
		{
			if (next.getState() != NodeState.VISITED)
			{
				next.setState(NodeState.FRONTIER);

				double estimate = current.getEstimate() + cost.value(current, next);
				
				if (estimate < next.getEstimate())
				{
					next.setScore(estimate + heuristic.estimate(next, destination));
					next.setEstimate(estimate);
					next.setPrevious(current);

					if (!openSet.contains(next))
					{
						openSet.add(next);
					}
				}
			}
		}
		
		return (AlgorithmState.CONTINUE);

	}

	@Override
	public String toString()
	{
		return ("A* Algorithm");
	}

	@Override
	public void reset()
	{
		openSet.clear();
		openSet.add(start);

		start.setScore(heuristic.estimate(start, destination));
		start.setEstimate(0.0);
		start.setPrevious(null);	
	}

	@Override
	public Iterable<Node> path()
	{
		List<Node> solution = new ArrayList<Node>();
		Node curNode = destination;

		while (curNode != null)
		{
			solution.add(curNode);
			curNode = curNode.getPrevious();
		}

		Collections.reverse(solution);

		return (solution);
	}

}
