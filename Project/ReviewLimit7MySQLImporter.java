package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Parse.BusinessMySQLManger;
import Parse.JsonReader;
import Parse.ReviewShortMySQLManger;

public class ReviewLimit7MySQLImporter {

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
	
	public ReviewLimit7MySQLImporter()
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
		ReviewLimit7MySQLImporter reviewLimit7MySQLImporter = new ReviewLimit7MySQLImporter();
		ReviewShortMySQLManger reviewMySQLManger = new ReviewShortMySQLManger();
		Statement st;
		Statement st_review;
		int counter = 0;
		
		try {
			
			st = conn.createStatement();
			st_review = conn.createStatement();
			String sql = ("SELECT * FROM short_business;");
			ResultSet rs = st.executeQuery(sql);
			String business_id = null;
			int review_count;
			ResultSet rs_review = null;
			String review_id = null;
			String sql_review = null;
			
			while(rs.next()) 
			{ 
			 review_count = rs.getInt("review_count"); 
			 business_id = rs.getString("business_id");
			 System.out.println(counter+"] business_id:"+business_id+",review_count: "+review_count);
			 
			 sql_review = ("SELECT * FROM short_review where business_id='"+business_id+"' order by date DESC limit 7;");
			 rs_review = st_review.executeQuery(sql_review);
			
				while(rs_review.next()) 
				{ 
					review_id = rs_review.getString("review_id"); 
					 //String business_id = rs.getString("business_id");
					 System.out.println("-- review_id:"+review_id);
					 reviewMySQLManger.insertReview_limit7(rs_review.getString("review_id"), 
							 	rs_review.getString("business_id"), rs_review.getString("user_id"), 
							 	rs_review.getDouble("stars"), rs_review.getString("text"), rs_review.getString("date"));
				}
				
			 ++ counter;
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		

	}
	
	
}
