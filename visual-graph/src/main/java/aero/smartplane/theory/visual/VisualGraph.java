package aero.smartplane.theory.visual;

import java.util.List;

import javax.swing.JPanel;

import aero.smartplane.theory.graphs.CostFunction;
import aero.smartplane.theory.graphs.Graph;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class VisualGraph extends JPanel implements Graph, CostFunction
{
	private static final long serialVersionUID = 1L;

	private final List<Blockage> blockages = new ArrayList<Blockage>();
	private final VisualNode start;
	private final VisualNode goal;
	private int scale = 10;

	public VisualGraph(VisualNode start, VisualNode goal)
	{
		this.start = start;
		this.goal = goal;
	}

	public List<Blockage> getBlockages() {
		return blockages;
	}

	public VisualNode getStart() {
		return start;
	}

	public VisualNode getGoal() {
		return goal;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public abstract Rectangle getBounds();

	public abstract void reset();

}
