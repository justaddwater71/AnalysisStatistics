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
	
	public static int getTagSize()
	{
		RunValues[] runValues = RunValues.values();
		
		int count = 0;
		
		for (int i = 0; i < runValues.length; i++)
		{
			if ( ! runValues[i].tagLine().equalsIgnoreCase(null))
			{
				count++;
			}
		}
		return count;
	}
	
	public static int getColumnLabelSize()
	{
		RunValues[] runValues = RunValues.values();
		
		int count = 0;
		
		for (int i = 0; i < runValues.length; i++)
		{
			if ( ! runValues[i].columnLabel().equalsIgnoreCase(null))
			{
				count++;
			}
		}
		return count;
	}
	
	public static String[] getColumnLabels()
	{
		RunValues[] runValues	= RunValues.values();
		int columnLabelSize	= getColumnLabelSize();
		String[] run				= new String[columnLabelSize];
		int index 						= 0;
		
		for (int i = 0; i < runValues.length; i++)
		{
			if ( ! runValues[i].columnLabel.equalsIgnoreCase(null))
			{
				run[index] = runValues[i].columnLabel;
				index++;
			}
		}
		return run;
	}
}
