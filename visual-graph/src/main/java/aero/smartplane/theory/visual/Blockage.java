package aero.smartplane.theory.visual;

import java.awt.Color;
import java.awt.Graphics2D;

public class Blockage
{
	private static final Color BLOCK_COLOR = new Color(63, 63, 63);

	private final int x;
	private final int y;
	private final int width;
	private final int height;
	
	public Blockage(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isInside(int x, int y)
	{
		if ((x >= this.x) && (x < (this.x + this.width)) && (y >= this.y) && (y < (this.y + this.height)))
		{
			return (true);
		}
		else
		{
			return (false);
		}
	}
	
	public void paintBlockage(Graphics2D g, int scale)
	{
		g.setColor(BLOCK_COLOR);
		g.fillRect((x * scale), (y * scale), (width * scale), (height * scale));
	}

}
