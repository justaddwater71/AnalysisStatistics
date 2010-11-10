package edu.nps.jody.AnalysisStatistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnector 
{
	//Data Members
	public static final String AUTHOR_TABLE = "authors";
	public static final String AUTHOR_NAME_FIELD = "name";
	public static final String AUTHOR_ID_FIELD = "id";
	
	
	//Constructors
	
	
	//Methods
	public static Connection connectToDatabase(String username, String password, String servername, String database) throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:mysql://" + servername + "/" + database;
		Class.forName("com.mysql.jdbc.Driver");
		
		Properties properties = new Properties();
		properties.setProperty("user", username);
		properties.setProperty("password", password);
		properties.setProperty("jdbcCompliantTruncation","false");
		Connection connection = DriverManager.getConnection(url, properties);		
		
		return connection;
	}
	
	public static int addRecord(Connection connection, String table, String[] fields, String[] data) throws SQLException
	{
		Statement statement = connection.createStatement();
		String fieldStatement;
		String dataStatement;
		int result;
		
		//TODO put a fields.length() == data.length() safety check here
		fieldStatement = fields[0];
		dataStatement = data[0];
		
		for (int i=1; i < fields.length; i++)
		{
			fieldStatement +=  ", " +  fields[i];
			dataStatement += ", " + data[i];
		}
		
		
		String sqlStatement = "INSERT INTO " + table +  " (" + fieldStatement + ") " +
		" VALUES (" + dataStatement + ");";
		
		statement.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
		
		ResultSet resultSet = statement.getGeneratedKeys();
		

		
		if (resultSet.next())
		{
			result = resultSet.getInt(1);
		}
		else
		{
			result = 0;
		}
		
		return result;
	}
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException 
	{
		String username 		= System.getProperty("user.name");;
		String password 		= "";
		String servername	= "127.0.0.1";
		String database 		= "";
		String table				= "";
		
		for (int i=0; i < args.length; i++)
		{
			if (args[i].equalsIgnoreCase("--username"))
			{
				username = args[i+1];
				i++;
			}
			else if (args[i].equalsIgnoreCase("--password"))
			{
				password = args[i+1];
				i++;
			}
			else if (args[i].equalsIgnoreCase("--servername"))
			{
				servername = args[i+1];
				i++;
			}
			else if (args[i].equalsIgnoreCase("--database"))
			{
				database = args[i+1];
				i++;
			}
			else if (args[i].equalsIgnoreCase("--table"))
			{
				table = args[i+1];
				i++;
			}
		}
		
		Connection connection = connectToDatabase(username, password, servername, database);
		
	}
}
