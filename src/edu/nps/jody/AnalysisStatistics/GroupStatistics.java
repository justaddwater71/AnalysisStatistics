package edu.nps.jody.AnalysisStatistics;

import java.util.Vector;

public class GroupStatistics 
{
	//Data Members
	public String		corpus;
	public String		feature;
	public String		model;
	public String		groupType;
	public int				groupSize;
	public int				crossVal;
	public double 	medianFscore;
	public double		medianPrecision;
	public double		medianRecall;
	public double		medianAccuracy;
	public double 	maximumFscore;
	public double		maximumPrecision;
	public double		maximumRecall;
	public double		maximumAccuracy;
	public double 	minimumFscore;
	public double		minimumPrecision;
	public double		minimumRecall;
	public double		minimumAccuracy;
	public double 	meanFscore;
	public double		meanPrecision;
	public double		meanRecall;
	public double		meanAccuracy;
	public double		variationFscore;
	public double		variationPrecision;
	public double		variationRecall;
	public double		variationAccuracy;
	
	Statistics[]				stats;
	double[]				fScores;
	double[]				precisions;
	double[]				recalls;
	double[]				accuracies;
	
	//Constructors
	public GroupStatistics( String corpus, String feature,  String model, 
			String groupType, int groupSize, int crossVal, Statistics[] stats)
	{
		this.corpus = corpus;
		this.feature = feature;
		this.model = model;
		this.groupType = groupType;
		this.groupSize = groupSize;
		this.crossVal = crossVal;
		
		this.stats = checkStats(stats);
	}
	
	//Methods
	public void checkStats(Statistics[] rawStats)
	{
		Vector<Statistics> statsVector 	= new Vector<Statistics>();
		Vector<Double> fScoreVector 		= new Vector<Double>();
		Vector<Double> precisionVector	= new Vector<Double>();
		Vector<Double> recallVector 		= new Vector<Double>();
		Vector<Double> accuracyVector	= new Vector<Double>();
		Statistics[] checkedStats;
		
		for (int i=0; i < rawStats.length; i++)
		{
			if (rawStats[i].corpus.equalsIgnoreCase(this.corpus) 					&&
					rawStats[i].feature.equalsIgnoreCase(this.feature)				&&
					rawStats[i].model.equalsIgnoreCase(this.model)					&&
					rawStats[i].groupType.equalsIgnoreCase(this.groupType) 	&&
					rawStats[i].groupSize == this.groupSize									&&
					rawStats[i].crossVal == this.crossVal)
					{
						statsVector.add(rawStats[i]);
					}
		}
		
		checkedStats = new Statistics[ statsVector.size()];
		statsVector.toArray(checkedStats);
		
		return checkedStats;
	}
	
	public double computeMedian()
	{
		for (int i=0; i < stats.length; i++)
		{
			
		}
	}
	
	public double computeMaximum()
	{
		for (int i=0; i < stats.length; i++)
		{
			
		}
	}
	
	public double computeMinimum()
	{
		
	}
	
	public double computeMean()
	{
		
	}
}
