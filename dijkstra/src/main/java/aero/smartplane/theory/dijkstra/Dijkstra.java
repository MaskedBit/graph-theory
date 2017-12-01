package aero.smartplane.theory.dijkstra;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import aero.smartplane.theory.graphs.Algorithm;
import aero.smartplane.theory.graphs.AlgorithmState;
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
					return (Double.compare(node1.getDistance(), node2.getDistance()));
				}
				
			});
	private final Set<Node> visited = new HashSet<Node>();
	private final Graph graph;
	private final Node destination;

	public Dijkstra(Graph graph, Node start, Node destination)
	{
		start.setDistance(0.0);
		start.setPrevious(null);

		this.graph = graph;
		this.queue.add(start);
		this.destination = destination;
	}

	@Override
	public AlgorithmState step()
	{
		Node cur = queue.poll();
		
		if (cur == null)
		{
			return (AlgorithmState.FAIL);
		}
		else
		{
			cur.setState(NodeState.VISITED);
			visited.add(cur);
			
			if (cur.equals(destination))
			{
				return (AlgorithmState.SUCCESS);
			}
			else for (Node next : graph.getNeighbors(cur))
			{
				if (next.getState() != NodeState.VISITED)
				{
					next.setState(NodeState.FRONTIER);
				}

				double measure = cur.getDistance() + graph.distance(cur, next);
				
				if (measure < next.getDistance())
				{
					next.setDistance(measure);
					next.setPrevious(cur);
					
					if (queue.contains(next))
					{
						queue.remove(next);		// In effect, decrease priority of node
					}

					queue.add(next);
				}
			}
		}
		
		return (AlgorithmState.CONTINUE);
	}

	@Override
	public Iterable<Node> getFrontier()
	{
		return (queue);
	}

	@Override
	public Iterable<Node> getVisited()
	{
		return (visited);
	}

	@Override
	public String toString()
	{
		return ("Dijkstra's Algorithm");
	}

}
