package edu.nps.jody.AnalysisStatistics;

public enum ResultValues 
{
	RUN				("run", 					null),
	AUTHOR		("author",				null),
	FSCORE		("fscore", 				null),
	PRECISION	("this_precision",	null),
	RECALL 		("recall", 				null);
	
	private String 	columnLabel;
	private String 	tagLine;
	
	ResultValues(String columnLabel, String tagLine)
	{
		this.columnLabel	= columnLabel;
		this.tagLine 				= tagLine;
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
