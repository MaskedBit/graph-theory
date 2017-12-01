package aero.smartplane.theory.plot;

public enum GraphChoice
{
	DIJKSTRA("Dijkstra"),
	A_STAR("A*");

	private final String label;

	GraphChoice(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return (label);
	}

}
