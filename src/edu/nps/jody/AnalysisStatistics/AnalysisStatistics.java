package edu.nps.jody.AnalysisStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class AnalysisStatistics 
{
	
	//Data Members
	public static final String AUTHOR_TAG 					= "<authors>";
	public static final String CONFUSION_LINE_TAG	 	= "<confusionMatrix>";
	public static final String TRUTH_TAG 						= "<truthUtterances>";
	public static final String TOTAL_TAG 						= "<totalUtteranes>";
	
	public static final int AUTHOR_INDEX						= 0;
	public static final int CONFUSION_LINE_INDEX		= 1;
	public static final int TRUTH_INDEX							= 2;
	public static final int TOTAL_INDEX							= 3;
	public static final int LINE_MAX									= 4;
	
	//Constructors
	
	//Methods
	public static int countAuthors(String line)
	{
		int authors = 0;
		StringTokenizer stringTokenizer;
		
		//Just a check that we have the right line
		if (line.startsWith(AUTHOR_TAG))
		{
			stringTokenizer = new StringTokenizer(line);
			
			//Discard first token, which is he AUTHOR_TAG, thus the - 1
			authors = stringTokenizer.countTokens() -1;
		}
			
		return authors;
	}
	
	public static HashMap<Integer, Integer> makeAuthorMap(String line)
	{
		HashMap<Integer, Integer> authorMap = new HashMap<Integer, Integer>();
		
		StringTokenizer stringTokenizer = new StringTokenizer(line);
		
		SortedSet<Integer> sortedSet = new TreeSet<Integer>();
		
		Iterator<Integer> iterator;
		
		String authorString;
		
		Integer authorInteger;
		
		Integer authorIndex = 0;
		
			//Discard the author tag
		if (stringTokenizer.nextToken().equalsIgnoreCase(AUTHOR_TAG))
		{
			
			//The authors are string sorted in the file.  This codes gets them in integer order
			while ((stringTokenizer.hasMoreTokens()))
			{
				authorString = stringTokenizer.nextToken();
				
				authorInteger = Integer.parseInt(authorString);
				
				sortedSet.add(authorInteger);
			}
			
			iterator = sortedSet.iterator();
			
			while (iterator.hasNext())
			{
				authorMap.put(iterator.next(), authorIndex);
				
				authorIndex++;
			}
		}
		else
		{
			authorMap = null;
		}
		return authorMap;
	}
	
	public static int[][] loadConfusionArray(String confusionLine, HashMap<Integer, Integer> authorMap)
	{
		int[][] confusionArray = new int[authorMap.size()][authorMap.size()];
		
		String token;
		
		String[] elements;
		
		StringTokenizer lineTokenizer = new StringTokenizer(confusionLine);
		
		Integer truthInteger;
		Integer truthIndex;
		
		Integer labelInteger;
		Integer labelIndex;
		
		int count;
		
		//Dummy check that this is the right line
		if (lineTokenizer.nextToken().equalsIgnoreCase(CONFUSION_LINE_TAG))
		{
			//Initialize confusionArray
			for (int i = 0; i < authorMap.size(); i++)
			{
				for (int j = 0; j < authorMap.size(); j++)
				{
					confusionArray[i][j] = 0;
				}
			}
			
			//Read the cofusion matrix line into the confusionArray
			while (lineTokenizer.hasMoreTokens())
			{
				token 				= lineTokenizer.nextToken();
				
				elements		= token.split(":");
				
				truthInteger 	= Integer.parseInt(elements[0]);
				
				labelInteger 	= Integer.parseInt(elements[1]);
				
				count				= Integer.parseInt(elements[2]);
				
				truthIndex 		= authorMap.get(truthInteger);
				
				labelIndex		= authorMap.get(labelInteger);
				
				confusionArray[truthIndex][labelIndex] = count;
			}	
		}
		
		return confusionArray;
	}
	
	public static Integer[] findLargestTruthValue(String truthLine)
	{
		Integer[] largest = {0, 0};
		
		String token;
		
		String[] elements;
		
		StringTokenizer truthLineTokenizer = new StringTokenizer(truthLine);
		
		//Dummy check that this is the correct line to be reading
		if (truthLineTokenizer.nextToken().equalsIgnoreCase(TRUTH_TAG))
		{
			while (truthLineTokenizer.hasMoreTokens())
			{
				token = truthLineTokenizer.nextToken();
				
				elements = token.split(":");
				
				if (Integer.parseInt(elements[1]) > largest[1])
				{
					largest[0] = Integer.parseInt(elements[0]);
					largest[1] = Integer.parseInt(elements[1]);
				}
			}
		}
		
		return largest;
	}
	
	public static Integer findTotalUtterances(String totalLine)
	{
		Integer total = 0;
		StringTokenizer tokenizer = new StringTokenizer(totalLine);
		
		//Dummy check that this is the right line
		if (tokenizer.nextToken().equalsIgnoreCase(TOTAL_TAG) && tokenizer.hasMoreTokens())
		{
			//total = Integer.parseInt(tokenizer.nextToken());
			total = Integer.parseInt(tokenizer.nextToken());
		}
		
		return total;
	}
	
	public static double getPrecision(int[][] confusionArray, int author)
	{
		double precision = 0.0;
		
		//Precision = truePositive / (truePositive + falsePositive )
		
		int truePositive = confusionArray[author][author];
		
		int falsePositiveTruePositive = 0;
		
		for (int i = 0; i < confusionArray[0].length; i++)
		{
			falsePositiveTruePositive += confusionArray[i][author];
		}
		
		precision = ((double) truePositive ) / ( (double) falsePositiveTruePositive );
		
		return precision;
	}
	
	public static double getRecall(int[][] confusionArray, int author)
	{
		double recall = 0.0;
		
		//Recall = truePositive / ( truePositive + falseNegative )
		
		int truePositive = confusionArray[author][author];
		
		int falseNegativeTruePositive = 0;
		
		for (int i = 0; i < confusionArray[0].length; i++)
		{
			falseNegativeTruePositive += confusionArray[author][i];
		}
		
		recall = ((double) truePositive ) / ( (double) falseNegativeTruePositive );
		
		return recall;
	}
	
	public static double getFscore(double precision, double recall)
	{
		double fScore = 2 / ( (1 / precision) + (1 / recall) );
		
		return fScore;
	}
	
	public static double getFscore(int[][] confusionArray, int author)
	{
		double precision = getPrecision(confusionArray, author);
		
		double recall = getRecall(confusionArray, author);
		
		double fScore = getFscore(precision, recall);
		
		return fScore;
	}
	
	public static double getMLE(int maxCount, int totalCount)
	{
		double mle = ( (double) maxCount ) / ( (double) totalCount );
		
		return mle;
	}
	
	public static double getMLE(String truthUtterances, String totalUtterances)
	{
		Integer[] truth = findLargestTruthValue(truthUtterances);
		
		Integer total = findTotalUtterances(totalUtterances);
		
		Double mle = getMLE(truth[1], total);
		
		return mle;
	}
	
	public static double getAccuracy(int[][] confusionArray, String totalUtterances)
	{
		double accuracy = 0.0;
		
		int truePositives = 0;
		
		int total = findTotalUtterances(totalUtterances);
		
		for (int i = 0; i < confusionArray[0].length; i++)
		{
			truePositives += confusionArray[i][i];
		}
		
		accuracy = ( (double) truePositives ) / ((double ) total );
		
		return accuracy;
	}
	
	public static String[] loadStrings(File file) throws FileNotFoundException, IOException
	{
		String [] data = new String[LINE_MAX];
		
		BufferedReader reader = new BufferedReader ( new FileReader (file));
		
		String line;
		
		while ((line = reader.readLine()) != null)
		{
			if (line.startsWith(AUTHOR_TAG))
			{
				data[AUTHOR_INDEX] = line;
			}
			else if (line.startsWith(CONFUSION_LINE_TAG))
			{
				data[CONFUSION_LINE_INDEX] = line;
			}
			else if (line.startsWith(TRUTH_TAG))
			{
				data[TRUTH_INDEX] = line;
			}
			else if (line.startsWith(TOTAL_TAG))
			{
				data[TOTAL_INDEX] = line;
			}
		}
		
		return data;
	}
	
	public static void processFile(File file) throws FileNotFoundException, IOException
	{
		//File			file = null;
		String[] 	strings;
		HashMap<Integer, Integer> authorMap;
		int 			authorIndex = 0;
		int			authorID;
		int[][] 	confusionArray;
		double 	mle;
		double accuracy;
		double 	recall;
		double 	precision;
		double 	fScore;
		
		strings 					= loadStrings(file);
		authorMap 			= makeAuthorMap(strings[AUTHOR_INDEX]);
		confusionArray 	= loadConfusionArray(strings[CONFUSION_LINE_INDEX], authorMap);
		accuracy				= getAccuracy(confusionArray, strings[TOTAL_INDEX]);
		mle							= getMLE(strings[TRUTH_INDEX],strings[TOTAL_INDEX]);


		
		System.out.println(
				"MLE: " 			+ mle 				+ "\n"	+
				"Accuracy: "	+ accuracy	+ "\n");
				
		SortedSet<Integer> authorSet = new TreeSet<Integer>(authorMap.keySet());
		Iterator<Integer> iterator = authorSet.iterator();
		
		while (iterator.hasNext())
		{
			authorID = iterator.next();
			authorIndex =authorMap.get(authorID);
			
			recall						= getRecall(confusionArray, authorIndex);
			precision				= getPrecision(confusionArray, authorIndex);
			fScore 					= getFscore(confusionArray, authorIndex);
			
			System.out.println();
			System.out.println(
					"Author:\t"			+ authorID		+ "\n" +
					"Recall:\t" 			+ recall 			+ "\n"	+
					"Precision:\t" 	+ precision 	+ "\n"	+
					"F-Score:\t" 		+ fScore);
		}
	}
	
	public static void processFiles(File[] fileArray)
	{
		
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		
	}
}
