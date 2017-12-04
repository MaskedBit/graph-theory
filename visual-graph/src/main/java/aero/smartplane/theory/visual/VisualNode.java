package aero.smartplane.theory.visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import aero.smartplane.theory.graphs.Node;
import aero.smartplane.theory.graphs.NodeId;
import aero.smartplane.theory.graphs.NodeState;

public class VisualNode implements Node
{
	private static final Logger LOGGER = LogManager.getFormatterLogger(TestGraph1.class);

	private static final Color UNKNOWN_COLOR = new Color(179, 195, 221);
	private static final Color FRONTIER_COLOR = new Color(255, 165, 0);
	private static final Color VISITED_COLOR = new Color(22, 83, 226);
	private static final Color START_COLOR = new Color(10, 173, 44);
	private static final Color GOAL_COLOR = new Color(216, 26, 8);

	private NodeState state = NodeState.UNKNOWN;
	private NodeCondition condition = NodeCondition.NORMAL;
	private double score = Double.MAX_VALUE;
	private double estimate = Double.MAX_VALUE;
	private VisualNode previous = null;
	private int x = 0;
	private int y = 0;

	public VisualNode()
	{
		;
	}

	public VisualNode(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public VisualNode(int x, int y, NodeCondition condition)
	{
		this.x = x;
		this.y = y;
		this.condition = condition;
	}

	public NodeId getId()
	{
		return (new VisualNodeId(x, y));
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

	public double getScore()
	{
		return (score);
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public double getEstimate()
	{
		return (estimate);
	}

	public void setEstimate(double estimate)
	{
		this.estimate = estimate;
	}

	public Node getPrevious()
	{
		return (previous);
	}

	public void setPrevious(Node prev)
	{
		this.previous = (VisualNode)prev;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return (true);
		}
		
		if (!(other instanceof VisualNode))
		{
			return (false);
		}
		
		VisualNode that = (VisualNode)other;
		
		return ((this.x == that.x) && (this.y == that.y));
	}

	@Override
	public int hashCode()
	{
		return ((31 * x) + y);
	}
	
	public void paintNode(Graphics2D g, int scale)
	{
		LOGGER.trace("paintNode() - %s", details());

		int x = this.x * scale;
		int y = this.y * scale;
//		int offset = scale / 2;
//		
//		if (previous != null)
//		{
//			int prevX = previous.x * scale;
//			int prevY = previous.y * scale;
//			
//			g.drawLine(prevX + offset, prevY + offset, x + offset, y + offset);
//		}
		
		Ellipse2D.Float shape = new Ellipse2D.Float(x + 2, y + 2, 6, 6);
		if (condition == NodeCondition.START)
		{
			g.setColor(START_COLOR);
		}
		else if (condition == NodeCondition.GOAL)
		{
			g.setColor(GOAL_COLOR);
		}
		else switch(state)
		{
		case FRONTIER:
			g.setColor(FRONTIER_COLOR);
			break;

		case VISITED:
			g.setColor(VISITED_COLOR);
			break;

		case UNKNOWN:
		default:
			g.setColor(UNKNOWN_COLOR);
			break;
		}
		g.fill(shape);
		g.setColor(new Color(31, 31, 31));
		g.draw(shape);
	}

	@Override
	public String toString()
	{
		return (String.format("[%d, %d]", x, y));
	}

	public String details()
	{
		return (String.format("%s - state=%s, condition=%s, score=%g, previous=%s",
				toString(), state.toString(), condition.toString(), score, ((previous != null) ? previous.toString() : "- none -")));
	}

}
