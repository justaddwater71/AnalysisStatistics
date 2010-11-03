package edu.nps.jody.AnalysisStatistics;

public enum FileValues 
{
	CORPUS							("corpus", 				null),
	FEATURE_TYPE				("feature_type", 	null),
	MODEL							("model", 				null),
	GROUP_TYPE					("group_type", 		null),
	GROUP_SIZE					("group_size", 		null),
	CROSSVAL						("crossval", 			null),
	FILENAME						("filename", 			"<mergeFileName>"),
	FILESIZE							("size", 					"<originalDataSize>"),
	UTTERANCES				("utterances", 		"<totalUtterances>"),
	CONFUSION_MATRIX		(null, 						"<confusionMatrix>"),
	AUTHOR_MATRIX			(null,						"<authors>"),
	TRUTH_MATRIX				(null, 						"<truthUtterances");
	
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
}


