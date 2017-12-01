package aero.smartplane.theory.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import aero.smartplane.theory.graphs.Graph;
import aero.smartplane.theory.graphs.Node;
import aero.smartplane.theory.graphs.NodeId;

public class TestGraph1 extends VisualGraph implements Graph
{
	private static final Logger LOGGER = LogManager.getFormatterLogger(TestGraph1.class);

	private static final long serialVersionUID = 1L;

	private static final Rectangle BOUNDARIES = new Rectangle(0, 0, 22, 22);
	private static final Color PATH_COLOR = new Color(50, 168, 52);
	// DEBUG
	private static final Color GRID_COLOR = new Color(191, 191, 191);
	// DEBUG END
	private static final VisualNode start = new VisualNode(2, 20, NodeCondition.START);
	private static final VisualNode goal = new VisualNode(18, 3, NodeCondition.GOAL);

	private final Map<NodeId, VisualNode> knownNodes = new HashMap<NodeId, VisualNode>();

	public TestGraph1()
	{
		super(start, goal);

		this.getBlockages().add(new Blockage(5, 6, 11, 3));
		this.getBlockages().add(new Blockage(13, 9, 3, 6));
		
		knownNodes.put(getStart().getId(), getStart());
		knownNodes.put(getGoal().getId(), getGoal());
	}

	@Override
	public Dimension getSize()
	{
		return (new Dimension(((BOUNDARIES.width + 1) * getScale()), ((BOUNDARIES.height + 1) * getScale())));
	}

	@Override
	public Dimension getPreferredSize()
	{
		return (getSize());
	}

	public double distance(Node node1, Node node2)
	{
		VisualNode vn1 = (VisualNode)node1;
		VisualNode vn2 = (VisualNode)node2;

		double dX = vn2.getX() - vn1.getX();
		double dY = vn2.getY() - vn1.getY();
		double answer = Math.sqrt((dX * dX) + (dY * dY));
		
		return (answer);
	}

	public Iterable<Node> getNeighbors(Node node)
	{
		VisualNode curNode = (VisualNode)node;
		List<Node> answer = new ArrayList<Node>();

		for (int dX = -1; dX <= 1; dX++)
		{
			for (int dY = -1; dY <= 1; dY++)
			{
				// Skip address of curNode
				if ((dX != 0) || (dY != 0))
				{
					int newX = curNode.getX() + dX;
					int newY = curNode.getY() + dY;

					// Stay within bounds of graph
					if ((newX >= BOUNDARIES.x) && (newX < (BOUNDARIES.x + BOUNDARIES.width))
							&& (newY >= BOUNDARIES.y) && (newY < (BOUNDARIES.y + BOUNDARIES.height)))
					{
						boolean blocked = false;

						// Stay out of forbidden areas
						for (Blockage curBlockage : getBlockages())
						{
							if (curBlockage.isInside(newX, newY))
							{
								blocked = true;
							}
						}
					
						if (!blocked)
						{
							NodeId nodeId = new VisualNodeId(newX, newY);
							
							// Is this a new node, or one we've already created?
							if (knownNodes.containsKey(nodeId))
							{
								answer.add(knownNodes.get(nodeId));
							}
							else
							{
								VisualNode newNode = new VisualNode(newX, newY);

								answer.add(newNode);
								knownNodes.put(nodeId, newNode);
							}
						}
					}
				}
			}
		}

		// DEBUG
		LOGGER.trace("getNeighbors() - node=%s", curNode.details());
		for (Node neighbor : answer)
		{
			LOGGER.trace("  neighbor:  %s", ((VisualNode)neighbor).details());
		}
		// DEBUG END

		return (answer);
	}

	@Override
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        int scale = getScale();
        
        // DEBUG
        g2d.setColor(GRID_COLOR);
        for (int col = (BOUNDARIES.x + 1); col <= (BOUNDARIES.x + BOUNDARIES.width); col++)
        {
        	g2d.drawLine((col * scale), (BOUNDARIES.y * scale), (col * scale), ((BOUNDARIES.y + BOUNDARIES.height + 1) * scale));
        }
        for (int row = (BOUNDARIES.y + 1); row <= (BOUNDARIES.y + BOUNDARIES.height); row++)
        {
        	g2d.drawLine((BOUNDARIES.x * scale), (row * scale), ((BOUNDARIES.x + BOUNDARIES.width + 1) * scale), (row * scale));
        }
        // DEBUG END

        for (Blockage curBlock : getBlockages())
        {
        	curBlock.paintBlockage(g2d, scale);
        }

        for (VisualNode curNode : knownNodes.values())
        {
        	curNode.paintNode(g2d, scale);
        }
        
        // If we're done, mark the path from start to goal
        if (goal.getPrevious() != null)
        {
        	paintPath(g2d);
        }
    }

	private void paintPath(Graphics2D g2d)
	{
		int offset = getScale() / 2;
		BasicStroke stroke = new BasicStroke(5.0F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		
		g2d.setColor(PATH_COLOR);
		g2d.setStroke(stroke);

		// Okay, so we're really marking the path from goal to start!
		VisualNode curNode = goal;
		while (curNode != null)
		{
			VisualNode previousNode = (VisualNode)curNode.getPrevious();

			if (previousNode != null)
			{
				g2d.drawLine(((curNode.getX() * getScale()) + offset), ((curNode.getY() * getScale()) + offset),
						((previousNode.getX() * getScale()) + offset), ((previousNode.getY() * getScale()) + offset));
			}

			curNode = previousNode;
		}
	}

	@Override
	public String toString()
	{
		return ("Test Graph 1");
	}

	@Override
	public void reset()
	{
		knownNodes.clear();
	}

}
