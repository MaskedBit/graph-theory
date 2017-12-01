package aero.smartplane.theory.plot;

public enum AlgorithmChoice
{
	DIJKSTRA("Dijkstra"),
	A_STAR("A*");

	private final String label;

	AlgorithmChoice(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return (label);
	}

}
