package aero.smartplane.theory.graphs;

public interface Node
{
	NodeId getId();
	double getScore();
	void setScore(double score);
	double getEstimate();
	void setEstimate(double estimate);
	Node getPrevious();
	void setPrevious(Node prev);
	NodeState getState();
	void setState(NodeState state);
}
