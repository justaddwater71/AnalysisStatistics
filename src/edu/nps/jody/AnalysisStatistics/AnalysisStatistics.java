package edu.nps.jody.AnalysisStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

public class AnalysisStatistics 
{
	//Data Members
	/*public static final String AUTHOR_TAG 					= "<authors>";
	public static final String CONFUSION_LINE_TAG	 	= "<confusionMatrix>";
	public static final String TRUTH_TAG 						= "<truthUtterances>";
	public static final String TOTAL_TAG 						= "<totalUtteranes>";
	public static final String SIZE_TAG 							= "<originalDataSize>";
	public static final String PATH_TAG							= "<mergeFileNameAndPath>";
	public static final String FILE_TAG 							= "<mergeFileName>";
	
	//FIXME, the indices are all jacked up and need serious cleanup
	public static final int AUTHOR_INDEX						= 0;
	public static final int CONFUSION_LINE_INDEX		= 1;
	public static final int TRUTH_INDEX							= 2;
	public static final int TOTAL_INDEX							= 8;
	public static final int SIZE_INDEX 							= 7;
	public static final int CROSSVAL_INDEX 					= 5;
	public static final int GROUP_SIZE_INDEX 				= 4;
	public static final int GROUP_TYPE_INDEX				= 3;
	public static final int MODEL_INDEX 						= 2;
	public static final int FEATURE_TYPE_INDEX			= 1;
	public static final int CORPUS_INDEX						= 0;
	public static final int FILE_INDEX 								= 9;
	public static final int NAME_INDEX							= 6;
	public static final int LINE_MAX									= 12;
	
	public static final int CORPUS 									= 0;
	public static final int FEATURE_TYPE 						= 1;
	public static final int MODEL 									= 2;
	public static final int GROUP_TYPE							= 3;
	public static final int GROUP_SIZE							= 4;
	public static final int CROSSVAL 								= 5;
	public static final int NAME 										= 6;
	public static final int SIZE											= 7;
	public static final int UTTERANCES 							= 8;
	public static final int FILE											= 9;
	
	public static final int METHOD 									= 0;
	public static final int ACCURACY 								= 1;
	public static final int MLE 											= 2;
	
	public static final int RUN 											= 0;
	public static final int AUTHOR 									= 1;
	public static final int FSCORE 									= 2;
	public static final int PRECISION								= 3;
	public static final int RECALL 									= 4;*/
	
	//Constructors
	
	//Methods
	public static int countAuthors(String line)
	{
		int authors = 0;
		StringTokenizer stringTokenizer;
		
		//Just a check that we have the right line
		if (line.startsWith(FileValues.AUTHOR_MATRIX.columnLabel()))
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
	
	public static int getSize(String sizeString)
	{
		String[] stringArray = sizeString.split(" ");
		
		return Integer.parseInt(stringArray[1]);
	}
	
	public static String[] loadStrings(File file) throws FileNotFoundException, IOException
	{
		String [] data = new String[LINE_MAX];
		
		BufferedReader reader = new BufferedReader ( new FileReader (file));
		
		String line;
		
		String[] pathLine;
		
		data[NAME_INDEX] = null;
		
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
			else if (line.startsWith(SIZE_TAG))
			{
				data[SIZE_INDEX] = line;
			}
			else if (line.startsWith(PATH_TAG))
			{
				pathLine = processPathLine(line);
				
				data[CROSSVAL_INDEX]			= pathLine[0];
				data[GROUP_SIZE_INDEX] 		= pathLine[1];
				data[GROUP_TYPE_INDEX] 		= pathLine[2];
				data[MODEL_INDEX] 				= pathLine[3];
				data[FEATURE_TYPE_INDEX] = pathLine[4];
				data[CORPUS_INDEX]				= pathLine[5];
			}
			else if (line.startsWith(FILE_TAG))
			{
				data[FILE_INDEX] = line.split(" ")[1];
			}
		}
		
		return data;
	}
	
	private static String[] processPathLine(String line) 
	{
		String[] split = line.split("/");
		
		String[] result = new String[6];
		
		for (int i=0; i < 6; i++)
		{
			result[i] = split[ split.length - 3 - i];
		}
		return result;
	}

	public static Statistics processFile(File file, String method) throws FileNotFoundException, IOException
	{
		//File			file = null;
		Statistics statistics;
		String[] 	strings;
		HashMap<Integer, Integer> authorMap;
		int 			authorIndex = 0;
		int			authorID;
		int[][] 	confusionArray;
		int 			utterances;
		String 		filename;
		double 	recall;
		double 	precision;
		double 	fScore;
		
		strings 							= loadStrings(file);
		authorMap 					= makeAuthorMap(strings[AUTHOR_INDEX]);
		statistics						= new Statistics(authorMap.size());
		statistics.authorMap	= authorMap;
		confusionArray 			= loadConfusionArray(strings[CONFUSION_LINE_INDEX], authorMap);
		statistics.accuracy		= getAccuracy(confusionArray, strings[TOTAL_INDEX]);
		statistics.mle				= getMLE(strings[TRUTH_INDEX],strings[TOTAL_INDEX]);
		statistics.size				= getSize(strings[SIZE_INDEX]);
		statistics.utterances	= findTotalUtterances(strings[TOTAL_INDEX]);
		statistics.corpus			= strings[CORPUS_INDEX];
		statistics.feature			= strings[FEATURE_TYPE_INDEX];
		statistics.model			= strings[MODEL_INDEX];
		statistics.groupType	= strings[GROUP_TYPE_INDEX];
		statistics.groupSize		= Integer.parseInt(strings[GROUP_SIZE_INDEX]);
		statistics.crossVal		= Integer.parseInt(strings[CROSSVAL_INDEX]);
		statistics.method			= method;
		statistics.filename		= strings[FILE_INDEX];
				
		SortedSet<Integer> authorSet = new TreeSet<Integer>(authorMap.keySet());
		Iterator<Integer> iterator = authorSet.iterator();
		
		
		while (iterator.hasNext())
		{
			authorID = iterator.next();
			authorIndex =authorMap.get(authorID);
			
			recall = getRecall(confusionArray, authorIndex);
			precision = getPrecision(confusionArray, authorIndex);
			fScore = getFscore(confusionArray, authorIndex);
			
			
			statistics.recall.add(authorIndex, recall);
			statistics.precision.add(authorIndex,  precision);
			statistics.fScore.add(authorIndex, fScore);
			
		}
		
		return statistics;
	}
	
	public static Statistics[] processFiles(File[] fileArray, String method) throws FileNotFoundException, IOException
	{
		Statistics[] stats = new Statistics[fileArray.length];
		for (int i=0; i < fileArray.length; i++)
		{
			stats[i] = processFile(fileArray[i], method);
		}
		
		return stats;
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
		Statistics[]			stats;
		String					method = "libLinear";
		
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
			else if (args[i].equals("--method"))
			{
				method = args[i+1];
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
		
		//fileArray = (File[])fileVector.toArray();
		fileVector.toArray(fileArray);
		
		for (int i = 0; i < fileArray.length; i++)
		{
			System.out.println(fileArray[i]);
		}
		
		stats = processFiles(fileArray, method);
		
		Connection connection = DatabaseConnector.connectToDatabase("jhgrady", "", "localhost", "thesis_stats");
		
		String [] fileFields	= {"corpus", "feature_type", "model", "group_type", "group_size", "crossval", "name", "size", "utterances", "file"};
		String [] fileData	= new String[fileFields.length];
		
		String[] runFields = {"file", "method", "accuracy", "mle"};
		String [] runData = new String[runFields.length];
		
		String[] resultFields ={"run", "author", "fscore", "precision", "recall"};
		String[] resultData  = new String[resultFields.length];
		
		Iterator<Integer> authorIterator;
		
		Integer localAuthor;
		Integer localFile;
		Integer localRun;
		
		for (int i = 0; i < stats.length; i++)
		{
			fileData[CORPUS]					= "'" + stats[i].corpus +"'";
			fileData[FEATURE_TYPE]		= "'" + stats[i].feature +"'";
			fileData[MODEL]					= "'" + stats[i].model +"'";
			fileData[GROUP_TYPE]		= "'" + stats[i].groupType +"'";
			fileData[GROUP_SIZE]			="'" +  Integer.toString(stats[i].groupSize) +"'";
			fileData[CROSSVAL]				="'" +  Integer.toString(stats[i].crossVal) +"'";
			fileData[NAME]						= "'" + stats[i].filename +"'";
			fileData[SIZE]						= "'" + Integer.toString(stats[i].size) +"'";
			fileData[UTTERANCES]		= "'" + Integer.toString(stats[i].utterances) +"'";
			
			localFile = DatabaseConnector.addRecord(connection, "files", fileFields, fileData);
			
			runData[FILE]						= localFile.toString();
			runData[METHOD]				= stats[i].method;
			runData[ACCURACY]			= Double.toString(stats[i].accuracy);
			runData[MLE]						= Double.toString(stats[i].mle);
			
			localRun = DatabaseConnector.addRecord(connection, "runs", runFields, runData);
			
			authorIterator = stats[i].authorMap.values().iterator();
			
			while (authorIterator.hasNext())
			{
				localAuthor = authorIterator.next();
				
				resultData[RUN]					= localRun.toString();
				resultData[AUTHOR]			= localAuthor.toString();
				resultData[FSCORE]				= stats[i].fScore.get(localAuthor).toString();
				resultData[PRECISION]		= stats[i].precision.get(localAuthor).toString();
				resultData[RECALL]				= stats[i].recall.get(localAuthor).toString();
			}
		}
	}
}
