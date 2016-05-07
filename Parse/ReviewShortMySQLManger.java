package Parse;

//STEP 1. Import required packages
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReviewShortMySQLManger {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	//static final String DB_URL = "jdbc:mysql://50.165.82.247:3307/yelp";
	static final String DB_URL = "jdbc:mysql://localhost/yelp";
	
	//  Database credentials
	static final String USER = "Yao";
	static final String PASS = "User1212";
	static Connection conn = null;
	static Statement stmt = null;
	private SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
	private java.util.Date date;
	
	public ReviewShortMySQLManger()
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
	
	public static void main(String[] args) 
	{
		ReviewShortMySQLManger reviewMySQLManager = new ReviewShortMySQLManger();
		
		reviewMySQLManager.insertReview("rxx1x","bxx2x","uxx2x",2.5,"DeKalb review text","2015-09-01");
		
		//reviewMySQLManager.insertVote("rxx1x","funny",10);
		
		/*
		ArrayList<String> list = new ArrayList<String>();
		list.add("C Yao1");
		list.add("C Yao2");
		list.add("C Yao3");
		
		businessMySQLManger.insertBusinessCategories("1", list);
		*/
	 
	}//end main
	
	public void insertReview(String review_id, String business_id, String user_id, double stars, String text, String dateStr)
	{
		/*
		try {
			date = formatter.parse(dateStr);
			System.out.println(date);
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		*/
		
		try
		{
			 // the mysql insert statement
		      String query = "INSERT INTO short_review (review_id, business_id, user_id, stars, text, date) " +
		  			"VALUES (?,?,?,?,?,?)";
		 
		      //System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, review_id);
		      preparedStmt.setString (2, business_id);
		      preparedStmt.setString (3, user_id);
		      preparedStmt.setDouble (4, stars);
		      preparedStmt.setString (5, text);
		      preparedStmt.setDate(6, java.sql.Date.valueOf(dateStr));

		      // execute the preparedstatement
		      preparedStmt.execute();

		}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		//System.out.println("insertReview Done!");
	}
	
	public void insertReview_limit7(String review_id, String business_id, String user_id, double stars, String text, String dateStr)
	{
		try
		{
			 // the mysql insert statement
		      String query = "INSERT INTO short_review_7 (review_id, business_id, user_id, stars, text, date) " +
		  			"VALUES (?,?,?,?,?,?)";
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, review_id);
		      preparedStmt.setString (2, business_id);
		      preparedStmt.setString (3, user_id);
		      preparedStmt.setDouble (4, stars);
		      preparedStmt.setString (5, text);
		      preparedStmt.setDate(6, java.sql.Date.valueOf(dateStr));

		      // execute the preparedstatement
		      preparedStmt.execute();

		}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		//System.out.println("insertReview Done!");
	}
	
	
	 
}
