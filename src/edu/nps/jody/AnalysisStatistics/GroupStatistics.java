package edu.nps.jody.AnalysisStatistics;

import java.util.ArrayList;
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
	
	public ArrayList <Statistics>			stats;
	public ArrayList <Double>				fScores;
	public ArrayList<Double>				precisions;
	public ArrayList<Double>				recalls;
	public ArrayList<Double>				accuracies;
	
	//Constructors
	public GroupStatistics( String corpus, String feature,  String model, 
			String groupType, int groupSize, int crossVal, ArrayList<Statistics> stats)
	{
		this.corpus = corpus;
		this.feature = feature;
		this.model = model;
		this.groupType = groupType;
		this.groupSize = groupSize;
		this.crossVal = crossVal;
		
		checkStats(stats);
	}
	
	//Methods
	public void checkStats(ArrayList<Statistics> rawStats)
	{
		Vector<Statistics> statsVector 	= new Vector<Statistics>();
		Vector<Double> fScoreVector 		= new Vector<Double>();
		Vector<Double> precisionVector	= new Vector<Double>();
		Vector<Double> recallVector 		= new Vector<Double>();
		Vector<Double> accuracyVector	= new Vector<Double>();
		
		
		for (int i=0; i < rawStats.size(); i++)
		{
			if (rawStats.get(i).corpus.equalsIgnoreCase(this.corpus) 					&&
					rawStats.get(i).feature.equalsIgnoreCase(this.feature)					&&
					rawStats.get(i).model.equalsIgnoreCase(this.model)						&&
					rawStats.get(i).groupType.equalsIgnoreCase(this.groupType) 	&&
					rawStats.get(i).groupSize == this.groupSize									&&
					rawStats.get(i).crossVal == this.crossVal)
					{
						statsVector.add(rawStats.get(i));
						fScoreVector.addAll(Collections.(rawStats.get(i).fScore))
						precisionVector.add(rawStats[i].precision);
						recallVector.add(rawStats[i].recall);
						accuracyVector.add(rawStats[i].accuracy);
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
