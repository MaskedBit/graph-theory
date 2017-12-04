package aero.smartplane.theory.plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import aero.smartplane.theory.graphs.Node;
import aero.smartplane.theory.visual.VisualGraph;
import aero.smartplane.theory.visual.VisualNode;

public class DummyGraph extends VisualGraph
{
	private static final long serialVersionUID = 1L;

	private static final Rectangle BOUNDARIES = new Rectangle(0, 0, 230, 230);
	private static final Color GRID_COLOR = new Color(191, 191, 191);
	private static final VisualNode theNode = new VisualNode(10, 10);

	public DummyGraph()
	{
		super(theNode, theNode);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return (getSize());
	}

	@Override
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(GRID_COLOR);
        for (int col = (BOUNDARIES.x + 10); col < (BOUNDARIES.x + BOUNDARIES.width); col += 10)
        {
        	g2d.drawLine(col, BOUNDARIES.y, col, (BOUNDARIES.y + BOUNDARIES.height));
        }
        for (int row = (BOUNDARIES.y + 10); row < (BOUNDARIES.y + BOUNDARIES.height); row += 10)
        {
        	g2d.drawLine(BOUNDARIES.x, row, (BOUNDARIES.x + BOUNDARIES.width), row);
        }
        
        theNode.paintNode(g2d, 10);
    }

	@Override
	public String toString()
	{
		return ("Dummy plot");
	}

	@Override
	public double distance(Node node1, Node node2)
	{
		return (1.0);
	}

	@Override
	public double value(Node from, Node to)
	{
		return (distance(from, to));
	}

	@Override
	public Iterable<Node> getNeighbors(Node node)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds()
	{
		return (BOUNDARIES);
	}

}
