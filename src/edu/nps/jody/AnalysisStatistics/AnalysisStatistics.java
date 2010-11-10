package edu.nps.jody.AnalysisStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

public class AnalysisStatistics 
{
	//Data Members
	
	
	//Constructors
	
	//Methods
	public static int countAuthors(String line)
	{
		int authors = 0;
		StringTokenizer stringTokenizer;
		
		//Just a check that we have the right line
		if (line.startsWith(FileValues.AUTHOR_MATRIX.tagLine()))
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
		
		return confusionArray;
	}
	
	public static Integer[] findLargestTruthValue(String truthLine)
	{
		Integer[] largest = {0, 0};
		
		String token;
		
		String[] elements;
		
		StringTokenizer truthLineTokenizer = new StringTokenizer(truthLine);
		
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
		
		return largest;
	}
	
	public static double getPrecision(int[][] confusionArray, int author)
	{
		double precision = 0.0;
		
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
		
		Integer total = Integer.parseInt(totalUtterances);
		
		Double mle = getMLE(truth[1], total);
		
		return mle;
	}
	
	public static double getAccuracy(int[][] confusionArray, String totalUtterances)
	{
		double accuracy = 0.0;
		
		int truePositives = 0;
		
		int total = Integer.parseInt(totalUtterances);
		
		for (int i = 0; i < confusionArray[0].length; i++)
		{
			truePositives += confusionArray[i][i];
		}
		
		accuracy = ( (double) truePositives ) / ((double ) total );
		
		return accuracy;
	}
	
	public static int getSize(String sizeString)
	{
		String[] stringArray = sizeString.split(" ");
		
		return Integer.parseInt(stringArray[1]);
	}
	
	public static String[] loadStrings(File file) throws FileNotFoundException, IOException
	{
		String [] data = new String[FileValues.getSize()];
		
		BufferedReader reader = new BufferedReader ( new FileReader (file));
		
		String line;
		
		FileValues[] fileValues = FileValues.values();
		
		while ((line = reader.readLine()) != null)
		{
				int beginIndex;
				
				for (int i=0;i<FileValues.getTagSize(); i++)
				{
					if (line.startsWith(fileValues[i].tagLine()))
					{
						beginIndex = fileValues[i].tagLine().length(); 
						data[fileValues[i].ordinal()] = line.substring(beginIndex).trim();//Strip the tagLine, leading spaces, and trailing spaces.
						i++;
						break;
					}
				}
		}
		
		return data;
	}

	public static void processFiles(File[] fileArray, Connection connection) throws FileNotFoundException, IOException, SQLException
	{
		for (int i = 0; i < fileArray.length; i++)
		{
			processFile(fileArray[i], connection);
		}
	}
	
	public static void processFile(File file, Connection connection) throws FileNotFoundException, IOException, SQLException
	{
		String[] strings = loadStrings(file);
		
		String[] labels = FileValues.getColumnLabels();
		String[] data = new String[ labels.length ];
		FileValues[] fileValues = FileValues.values();
		
		for (int i=0; i < labels.length; i++)
		{
			if (! fileValues[i].columnLabel().isEmpty())
			{
				data[i] = "'" + strings[i] + "'";
			}
		}
		
		int fileNumber = DatabaseConnector.addRecord(connection, "files", labels, data);
		
		HashMap<Integer, Integer> authorMap = makeAuthorMap(strings[FileValues.AUTHOR_MATRIX.ordinal()]);
		
		int[][] confusionArray = loadConfusionArray(strings[FileValues.CONFUSION_MATRIX.ordinal()], authorMap);
		
		int runNumber	= processRunData(strings, connection, confusionArray, fileNumber);
		
		processResultData(strings, connection, confusionArray, authorMap, runNumber); 
	}
	
	public static int processRunData(String[] strings, Connection connection, int[][] confusionArray,int  fileNumber) throws SQLException
	{
		String[] labels = RunValues.getColumnLabels();
		String[] data = new String[ labels.length ];
		
		data[RunValues.FILE.ordinal()] = "'" + Integer.toString(fileNumber) + "'";
		data[RunValues.METHOD.ordinal()] = "'" + strings[FileValues.MODEL.ordinal()] + "'";
		data[RunValues.ACCURACY.ordinal()] = "'" + Double.toString(getAccuracy(confusionArray, strings[FileValues.UTTERANCES.ordinal()])) + "'";
		data[RunValues.MLE.ordinal()]				= "'" + Double.toString(getMLE(strings[FileValues.TRUTH_MATRIX.ordinal()], strings[FileValues.UTTERANCES.ordinal()])) + "'";
		
		int runID = DatabaseConnector.addRecord(connection, "runs", labels, data);
		
		return runID;
	}
	
	public static void processResultData(String[] strings, Connection connection, int[][] confusionArray, HashMap<Integer, Integer> authorMap, int runNumber) throws SQLException
	{
		String[] labels = ResultValues.getColumnLabels();
		String[] data = new String[ labels.length ];
		
		data[ResultValues.RUN.ordinal()] = Integer.toString(runNumber);
		
		Integer author;
		
		SortedSet<Integer> authorSet = new TreeSet<Integer>(authorMap.keySet());
		
		Iterator<Integer> iterator = authorSet.iterator();
		
		Double precision;
		Double recall;
		
		while (iterator.hasNext())
		{
			author = iterator.next();
			data[ResultValues.AUTHOR.ordinal()] 		= "'" + author.toString() 															+ "'";
			precision														= getPrecision(confusionArray, authorMap.get(author));
			data[ResultValues.PRECISION.ordinal()] 	= "'" + Double.toString(precision) 											+ "'";
			recall																= getRecall(confusionArray, authorMap.get(author));
			data[ResultValues.RECALL.ordinal()] 		= "'" + Double.toString(recall) 													+ "'";
			
			if (precision > 0 && recall > 0)
			{			
				data[ResultValues.FSCORE.ordinal()] 	= "'" + Double.toString(getFscore(precision, recall)) 			+ "'";
			}
			else
			{
				data[ResultValues.FSCORE.ordinal()] 	= "'" + 0.0 																					+ "'";
			}
			
			DatabaseConnector.addRecord(connection, "results", labels, data);
		}
		
	}

	public static Vector<File> getFiles (File topDirectory, String targetName, Vector<File> fileVector)
	{
		if (fileVector == null)
		{
			fileVector = new Vector<File>();
		}
		
		File[] fileArray;
		
		if (topDirectory.getName().equalsIgnoreCase(targetName))
		{
			fileArray = new File[1];
			fileArray[0] = topDirectory;
		}
		else
		{
			fileArray =topDirectory.listFiles();
		}
		
		File[] targetArray;
		
		for (int i = 0; i < fileArray.length; i++)
		{
			if (fileArray[i].isDirectory())
			{
				if (fileArray[i].getName().equalsIgnoreCase(targetName))
				{
					targetArray = fileArray[i].listFiles();
					for (int j = 0; j < targetArray.length; j++)
					{
						fileVector.add(targetArray[j]);
					}
				}
				else
				{
					fileVector = getFiles(fileArray[i], targetName, fileVector);
				}
			}
					
		}
		
		return fileVector;
		
	}

	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException 
	{
		Vector<File> 	fileVector		 = new Vector<File>();
		String 					targetName = "";
		File 						topDirectory = new File(System.getProperty("user.dir"));
		File[]					fileArray;
		String					username 		= "root";
		String					password 		= "";
		String					servername 	= "localhost";
		String					database		= "stats";
		
		//Statistics[]			stats;
		//String					method = "libLinear";
		
		for (int i = 0; i < args.length; i++)
		{
			if(args[i].equalsIgnoreCase("--targetName"))
			{
				targetName = args[i+1];
				i++;
			}
			else if (args[i].equalsIgnoreCase("--topDirectory"))
			{
				topDirectory = new File(args[i+1]);
				i++;
			}
			else if (args[i].equals("--username"))
			{
				username = args[i+1];
				i++;
			}
			else if (args[i].equals("--password"))
			{
				password = args[i+1];
				i++;
			}			
			else if (args[i].equals("--servername"))
			{
				servername = args[i+1];
				i++;
			}
			else if (args[i].equals("--database"))
			{
				database = args[i+1];
				i++;
			}
		}
		
		fileVector = getFiles(topDirectory, targetName, fileVector);
		
		System.out.println("Size: " + fileVector.size());
		
		Iterator<File> iterator = fileVector.iterator();
		
		while (iterator.hasNext())
		{
			System.out.println(iterator.next().getName());
		}
		
		fileArray = new File[fileVector.size()];
		
		fileVector.toArray(fileArray);
		
		for (int i = 0; i < fileArray.length; i++)
		{
			System.out.println(fileArray[i]);
		}
		
		Connection connection = DatabaseConnector.connectToDatabase(username, password, servername, database);
		
		processFiles(fileArray, connection);
		
	}
}
