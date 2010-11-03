package edu.nps.jody.AnalysisStatistics;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics 
{
	//Data Members
	public String		filename;
	public String		corpus;
	public String		feature;
	public String		model;
	public String		groupType;
	public int				groupSize;
	public int				crossVal;
	public HashMap<Integer, Integer> authorMap;
	public double 	mle;
	public double 	accuracy;
	public int				size;
	public int				utterances;
	public String		method;
	public ArrayList<Double> recall;
	public ArrayList<Double> precision;
	public ArrayList<Double> fScore;
	
	//Constructors
	public Statistics()
	{
		
	}
	
	public Statistics(int authorCount)
	{
		recall 			= new ArrayList<Double>(authorCount);
		precision 	= new ArrayList<Double>(authorCount);
		fScore 		= new ArrayList<Double>(authorCount);
	}
	
	//Methods
	
	
	
}
