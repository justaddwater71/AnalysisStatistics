package edu.nps.jody.AnalysisStatistics;

public enum ResultValues 
{
	RUN				("run", 							null),
	AUTHOR		("author",						null),
	FSCORE		("author_fscore", 			null),
	PRECISION	("author_precision",	null),
	RECALL 		("author_recall", 			null);
	
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
	
	public static int getTagSize()
	{
		ResultValues[] resultValues = ResultValues.values();
		
		int count = 0;
		
		for (int i = 0; i < resultValues.length; i++)
		{
			if ( ! resultValues[i].tagLine().equalsIgnoreCase(null))
			{
				count++;
			}
		}
		return count;
	}
	
	public static int getColumnLabelSize()
	{
		ResultValues[] resultValues = ResultValues.values();
		
		int count = 0;
		
		for (int i = 0; i < resultValues.length; i++)
		{
			if ( ! resultValues[i].columnLabel().equalsIgnoreCase(null))
			{
				count++;
			}
		}
		return count;
	}
	
	public static String[] getColumnLabels()
	{
		ResultValues[] resultValues	= ResultValues.values();
		int columnLabelSize	= getColumnLabelSize();
		String[] result				= new String[columnLabelSize];
		int index 						= 0;
		
		for (int i = 0; i < resultValues.length; i++)
		{
			if ( ! resultValues[i].columnLabel.equalsIgnoreCase(null))
			{
				result[index] = resultValues[i].columnLabel;
				index++;
			}
		}
		return result;
	}
}
