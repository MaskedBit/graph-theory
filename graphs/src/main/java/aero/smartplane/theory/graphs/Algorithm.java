package aero.smartplane.theory.graphs;

public interface Algorithm
{
	AlgorithmState step();
	Iterable<Node> getFrontier();
	Iterable<Node> getVisited();
}
