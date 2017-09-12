import java.util.ArrayList;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.CreateIndexQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.CreateTableQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.InsertQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SQLParser;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SelectQuery;
import net.sf.jsqlparser.JSQLParserException;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.*;

public class DBTesting {
	static SQLParser sp = new SQLParser();
	public static void main(String[] args) throws Throwable
	{
		String query = "CREATE TABLE YCStudent"
			+ "("
			+ " BannerID int,"
			+ " SSNUM int ,"
			+ " FirstName varchar(255),"
			+ " LastName varchar(255) NOT NULL,"
			+ " GPA decimal(1,2) DEFAULT 3.0,"
			+ " CurrentStudent boolean DEFAULT true,"
			+ " PRIMARY KEY (SSNUM)"
			+ ");";
		insertToTables(query);
	}


	public static String parse(String s) throws Exception
	{
	if(s.contains("CREATE TABLE")){
		Tables tbl = new Tables((CreateTableQuery) sp.parse(s));
		tbl.createTable((CreateTableQuery) sp.parse(s));
		System.out.println("Hard Work Pays Off");
		System.out.println(tbl.toString());
	if(s.contains("INSERT INTO")){
		tbl.insert((InsertQuery) sp.parse(s));
		System.out.println(tbl.toString());
	}
		}
	return "HI";
		}

	public static Tables parseTable(String s) throws JSQLParserException
	{		
	if(s.contains("CREATE TABLE")){	
		Tables tbl = new Tables((CreateTableQuery) sp.parse(s));
		return tbl;
 }
	return null;
	}
 
 	public static void insertToTables(String s) throws Exception
 	{
	 Tables tbl = (Tables) parseTable(s);
	 String query1 = ("INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800456, 123456, 'Saul', 'Cohen', 4.0, True);");
	 InsertQuery iq = (InsertQuery) sp.parse(query1);
	 tbl.insert(iq);
	 String query2 = ("INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800457, 154236, 'Saulie', 'Cohen', 2.0, True);");
	 InsertQuery iq2 = ((InsertQuery) sp.parse(query2));
	 tbl.insert(iq2);
 	 String query3 = "INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800458, 185699, 'Dufenshmirtz', 'blah', 3.0, False);";
	 InsertQuery iq3 = (InsertQuery) sp.parse(query3);
	 tbl.insert(iq3);
	 String hi = "CREATE INDEX BannerID_Index on YCStudent (BannerID);";
	 CreateIndexQuery iqr = (CreateIndexQuery) sp.parse(hi);
	 //tbl.createIndex(iqr, tbl);
	 String query6 = "INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800459, 12347, 'Dufenshmirtz', 'blah', 1.0, False);";
	 InsertQuery iq6 = (InsertQuery) sp.parse(query6);
	 tbl.insert(iq6);
	 String query7 = "INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800460, 15369, 'Dufenshmirtz', 'blah', 2.0, False);";
	 InsertQuery iq7 = (InsertQuery) sp.parse(query7);
	 tbl.insert(iq7);
	 String query8 = "INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800461, 36, 'Dufenshmirtz', 'blah', 6.0, False);";
	 InsertQuery iq8 = (InsertQuery) sp.parse(query8);
	 tbl.insert(iq8);
	 String query9 = "INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800462, 100000, 'Dufenshmirtz', 'blah', 4.8, False);";
	 InsertQuery iq9 = (InsertQuery) sp.parse(query9);
	 tbl.insert(iq9);
	 String query10 = "INSERT INTO YCStudent (BannerID, SSNUM, FirstName, LastName, GPA, CurrentStudent) VALUES (800463, 134036823, 'Dufenshmirtz', 'blah', 2.9, False);";
	 InsertQuery iq10 = (InsertQuery) sp.parse(query10);
	 tbl.insert(iq10);
	 String query5 = "DELETE FROM YCStudent WHERE FirstName = 'Saul' OR GPA >= 3.0;";
	 DeleteQuery rq = ((DeleteQuery) sp.parse(query5));
	 tbl.delete(rq);
	 //System.out.println(resultSet);
	 
 	}
}