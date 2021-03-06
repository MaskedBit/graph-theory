package aero.smartplane.theory.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import aero.smartplane.theory.graphs.Algorithm;
import aero.smartplane.theory.graphs.AlgorithmState;
import aero.smartplane.theory.graphs.CostFunction;
import aero.smartplane.theory.graphs.Graph;
import aero.smartplane.theory.graphs.Node;
import aero.smartplane.theory.graphs.NodeState;

public class Dijkstra implements Algorithm
{
	private static final int INITIAL_SIZE = 101;

	private final PriorityQueue<Node> queue = new PriorityQueue<Node>(INITIAL_SIZE, new Comparator<Node>()
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

	public Dijkstra(Graph graph, Node start, Node destination, CostFunction cost)
	{
		this.graph = graph;
		this.queue.add(start);
		this.start = start;
		this.destination = destination;
		this.cost = cost;

		start.setScore(0.0);
		start.setPrevious(null);
	}

	/**
	 * Effectively Uniform Cost Search
	 */
	@Override
	public AlgorithmState step()
	{
		Node cur = queue.poll();

		if (cur == null)
		{
			return (AlgorithmState.FAIL);
		}

		cur.setState(NodeState.VISITED);
		
		if (cur.equals(destination))
		{
			return (AlgorithmState.SUCCESS);
		}

		for (Node next : graph.getNeighbors(cur))
		{
			if (next.getState() != NodeState.VISITED)
			{
				next.setState(NodeState.FRONTIER);
			}

			double measure = cur.getScore() + cost.value(cur, next);
			
			if (measure < next.getScore())
			{
				next.setScore(measure);
				next.setPrevious(cur);
				
				if (queue.contains(next))
				{
					queue.remove(next);		// In effect, decrease priority of node
				}

				queue.add(next);
			}
		}
		
		return (AlgorithmState.CONTINUE);
	}

	@Override
	public String toString()
	{
		return ("Dijkstra's Algorithm");
	}

	@Override
	public void reset()
	{
		queue.clear();
		queue.add(start);
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
