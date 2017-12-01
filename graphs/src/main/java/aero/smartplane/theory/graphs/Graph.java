package aero.smartplane.theory.graphs;

public interface Graph
{
	double distance(Node node1, Node node2);
	Iterable<Node> getNeighbors(Node node);
}
