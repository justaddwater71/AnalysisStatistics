package edu.nps.jody.AnalysisStatistics;

public enum RunValues 
{
	FILE					("file", 			null),
	METHOD			("method", 	null),
	ACCURACY		("accuracy", 	null),
	MLE					("mle", 			null);
	
	private String columnLabel;
	private String tagLine;
	
	RunValues(String columnLabel, String tagLine)
	{
		this.columnLabel 	= columnLabel;
		this.tagLine				= tagLine;
	}
	
	public String columnLabel()
	{
		return columnLabel;
	}
	
	public String tagLine()
	{
		return tagLine;
	}
}
