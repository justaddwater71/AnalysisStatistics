package edu.nps.jody.AnalysisStatistics;

import java.util.HashMap;

public class Statistics 
{
	//Data Members
	public String		corpus;
	public String		feature;
	public String		model;
	public String		groupType;
	public int				groupSize;
	public int				crossVal;
	public HashMap<Integer, Integer> authorMap;
	public double 	mle;
	public double 	accuracy;
	public double[] 	recall;
	public double[] 	precision;
	public double[] 	fScore;
	
	//Constructors
	public Statistics()
	{
		
	}
	
	public Statistics(int authorCount)
	{
		recall = new double[authorCount];
		precision = new double[authorCount];
		fScore = new double[authorCount];
	}
	
	//Methods
	
	
	
}
