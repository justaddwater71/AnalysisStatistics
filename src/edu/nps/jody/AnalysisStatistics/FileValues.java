package edu.nps.jody.AnalysisStatistics;

public enum FileValues 
{
	CORPUS							("corpus", 				"<corpus>"),
	FEATURE_TYPE				("feature_type", 	"<featureType>"),
	MODEL							("model", 				"<model>"),
	METHOD							("method",			"<method>"),
	GROUP_TYPE					("group_type", 		"<groupType>"),
	GROUP_SIZE					("group_size", 		"<groupSize>"),
	CROSSVAL						("crossval", 			"<crossval>"),
	FILENAME						("filename", 			"<mergeFileName>"),
	FILESIZE							("size", 					"<originalDataSize>"),
	UTTERANCES				("utterances", 		"<totalUtterances>"),
	CONFUSION_MATRIX		(null, 						"<confusionMatrix>"),
	AUTHOR_MATRIX			(null,						"<authors>"),
	TRUTH_MATRIX				(null, 						"<truthUtterances"),
	PATH								(null,						"<mergeFileNameAndPath");
	
	private String 		columnLabel;
	private String 		tagLine;
	
	FileValues(String columnLabel, String tagLine)
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
	
	public static int getSize()
	{
		FileValues[] fileValues = FileValues.values();
		
		return fileValues.length;
	}
	
	public static int getTagSize()
	{
		FileValues[] fileValues = FileValues.values();
		
		int count = 0;
		
		for (int i = 0; i < fileValues.length; i++)
		{
			if ( ! fileValues[i].tagLine().equalsIgnoreCase(null))
			{
				count++;
			}
		}
		return count;
	}
	
	public static int getColumnLabelSize()
	{
		FileValues[] fileValues = FileValues.values();
		
		int count = 0;
		
		for (int i = 0; i < fileValues.length; i++)
		{
			if ( ! fileValues[i].columnLabel().equalsIgnoreCase(null))
			{
				count++;
			}
		}
		return count;
	}
	
	public static String[] getColumnLabels()
	{
		FileValues[] fileValues	= FileValues.values();
		int columnLabelSize	= getColumnLabelSize();
		String[] result				= new String[columnLabelSize];
		int index 						= 0;
		
		for (int i = 0; i < fileValues.length; i++)
		{
			if ( ! fileValues[i].columnLabel.equalsIgnoreCase(null))
			{
				result[index] = fileValues[i].columnLabel;
				index++;
			}
		}
		return result;
	}
	
}


