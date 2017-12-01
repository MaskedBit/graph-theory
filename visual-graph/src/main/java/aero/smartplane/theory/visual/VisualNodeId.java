package aero.smartplane.theory.visual;

import aero.smartplane.theory.graphs.NodeId;

public class VisualNodeId implements NodeId
{
	private int x;
	private int y;
	
	public VisualNodeId(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return (true);
		}
		
		if (!(other instanceof VisualNodeId))
		{
			return (false);
		}
		
		VisualNodeId that = (VisualNodeId)other;
		
		return ((this.x == that.x) && (this.y == that.y));
	}

	@Override
	public int hashCode()
	{
		return ((31 * x) + y);
	}

}
