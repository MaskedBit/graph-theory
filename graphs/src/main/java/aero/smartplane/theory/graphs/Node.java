package aero.smartplane.theory.graphs;

public interface Node
{
	NodeId getId();
	double getDistance();
	void setDistance(double dist);
	Node getPrevious();
	void setPrevious(Node prev);
	NodeState getState();
	void setState(NodeState state);
}
