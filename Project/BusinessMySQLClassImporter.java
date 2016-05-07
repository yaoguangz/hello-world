package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class BusinessMySQLClassImporter 
{

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
		
		public BusinessMySQLClassImporter()
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
		BusinessMySQLClassImporter businessMySQLClassImporter = new BusinessMySQLClassImporter();
		//BusinessMySQLManger businessMySQLManger = new BusinessMySQLManger();
		Statement st;
		Statement st_review;
		int counter = 0;
		int max = 100000;
		
		try {
			
			st = conn.createStatement();
			st_review = conn.createStatement();
			String sql = ("SELECT * FROM short_business order by ID;");
			ResultSet rs = st.executeQuery(sql);
			String business_id = null;
			ResultSet rs_review = null;
			String review_id = null;
			String business_name = null;
			String sql_review = null;
			
			while(rs.next() && counter < max) 
			{ 
			 business_id = rs.getString("business_id");
			 business_name = rs.getString("name");
			 System.out.println(counter+"] business_id:"+business_id+",business_name: "+business_name);
			 
			 businessMySQLClassImporter.insertBusinessIDName(business_id, business_name);
			 
			 ++ counter;
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertBusinessIDName(String business_id, String business_name)
	{
		try
		{
			String query = "INSERT INTO business_class (business_id, business_name) " +
					"VALUES (?,?)";
		 
		      System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, business_id);
		      preparedStmt.setString (2, business_name);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
			      
			
		}catch(SQLException se)
		{
				se.printStackTrace();
		}catch(Exception e)
		{
				e.printStackTrace();
		}

		System.out.println("insertBusinessIDName Done!");
	}
	
}
