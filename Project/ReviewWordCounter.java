package Project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import Parse.ReviewShortMySQLManger;

public class ReviewWordCounter {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/yelp";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "User1212";
	static Connection conn = null;
	static Statement stmt = null;
	private SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
	private java.util.Date date;
		
	public ReviewWordCounter() 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String x[])
	{
		//ReviewLimit7MySQLImporter reviewLimit7MySQLImporter = new ReviewLimit7MySQLImporter();
		ReviewWordCounter reviewWordCounter = new ReviewWordCounter();
		Statement st;
		int counter = 0;
		String[] words = null;
		
		PrintWriter writer = null;
				
		try {
			writer = new PrintWriter("E:\\Spring 2016\\CSCI 680-K\\Project\\Output\\wordCount2.csv", "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			st = conn.createStatement();
			String sql = ("SELECT * FROM short_review_7;");
			ResultSet rs = st.executeQuery(sql);
			String business_id = null;
			int review_count;
			String review_id = null;
			String text = null;
			String wordLower = null;
			Map<String, Integer> map = new HashMap<>();
			
			while(rs.next()) 
			{ 
				business_id = rs.getString("business_id");
				review_id = rs.getString("review_id");
				text = rs.getString("text");
				 //review_count = rs.getInt("review_count"); 
				 System.out.println(counter+"] business_id:"+business_id+",review_id: "+review_id);
				 
				 words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
				 
			    for (String w : words) {
			    	wordLower = w.toLowerCase();
			        Integer n = map.get(wordLower);
			        n = (n == null) ? 1 : ++n;
			        map.put(wordLower, n);
			    }
				    
				 ++ counter;
			}
			
			System.out.println("word,count");
			writer.println("word,count");
			
		    for (String key : map.keySet()) {
		        System.out.println(key + "," + map.get(key));
		        writer.println(key + "," + map.get(key));
		    }
		    
		    writer.close();
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
