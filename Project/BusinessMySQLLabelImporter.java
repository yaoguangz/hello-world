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

public class BusinessMySQLLabelImporter 
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
		
		public BusinessMySQLLabelImporter()
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
		BusinessMySQLLabelImporter businessMySQLLabelImporter = new BusinessMySQLLabelImporter();
		//BusinessMySQLManger businessMySQLManger = new BusinessMySQLManger();
		Statement st;
		Statement st_class;
		int counter = 0;
		int max = 100000;
		String classLabel = "worthiness";
		String classValue = "0";
		
		try {
			
			st = conn.createStatement();
			st_class = conn.createStatement();
			String sql = ("SELECT * FROM short_business order by ID;");
			ResultSet rs = st.executeQuery(sql);
			String business_id = null;
			ResultSet rs_class = null;
			String review_id = null;
			String business_name = null;
			String sql_review = null;
			double stars = 0;
			String ratingLabel = "0";
			String priceLable = "0";
			
			while(rs.next() && counter < max) 
			{ 
			 business_id = rs.getString("business_id");
			 business_name = rs.getString("name");
			 stars = rs.getDouble("stars");
			 System.out.println(counter+"] business_id:"+business_id+",business_name: "+business_name+",stars: "+stars);
			 
			 // update business rating label
			 if(stars >= 3)
				 ratingLabel = "1";
			 else
				 ratingLabel = "0";
			 
			 //businessMySQLLabelImporter.updateBusinessClass(business_id, "rating", ratingLabel);
			 //businessMySQLLabelImporter.updateBusinessWithDouble(business_id, "yelp_rating", stars);
			 
			 /*
			 // fetch and update business price
			 int price = businessMySQLLabelImporter.getValueFromTable("business_price", "business_id", business_id, "price");
			 System.out.println("    -- price: "+price);
			 
			 if(price < 3)
				 priceLable = "1";
			 else
				 priceLable = "0";
			 
			 businessMySQLLabelImporter.updateBusinessClass(business_id, "price", priceLable);
			*/
			 
			 
			 // update business service, food, and worthiness with the statement following
			 rs_class = st_class.executeQuery("select count(*) from review_classified where business_id='"+
					 business_id+"' and class='"+classLabel+"' and num=1");

			 while(rs_class.next())
			 {
				 System.out.println("    -- " + rs_class.getInt(1) );
				 
				 if(rs_class.getInt(1) > 3)
				 {
					 classValue = "1";
					 System.out.println("    -- class 1");
					 
				 }
				 else
				 {
					 classValue = "0";
					 System.out.println("    -- class 0");
				 }
				 
				 businessMySQLLabelImporter.updateBusinessClass(business_id, classLabel, classValue);
			 }
			 
			 
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
	
	
	public void updateBusinessClass(String business_id, String classLabel, String classValue)
	{
		try
		{
			String query = "UPDATE business_class SET "+classLabel+" = ? WHERE business_id = ? " ;
		 
		      System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, classValue);
		      preparedStmt.setString (2, business_id);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
			      
			
		}catch(SQLException se)
		{
				se.printStackTrace();
		}catch(Exception e)
		{
				e.printStackTrace();
		}

		System.out.println("updateBusinessClass Done!");
	}
	
	public int getValueFromTable(String table, String fieldSearch, String fieldValue, String fieldReturn)
	{
		Statement st;
		int counter = 0;
		int field = 0;
		
		try {
			
			st = conn.createStatement();
			String sql = ("SELECT "+fieldReturn+" FROM "+table+" where "+fieldSearch+"='"+fieldValue+"';");
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) 
			{ 
			 field = rs.getInt(fieldReturn);
			 //business_name = rs.getString("name");
			 System.out.println(counter+"] fieldValue:"+fieldValue+",field: "+field);
			 
			 ++ counter;
			}
			
			//conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return field;
	}
	
	public void updateBusinessWithDouble(String business_id, String classLabel, double classValue)
	{
		try
		{
			String query = "UPDATE business_class SET "+classLabel+" = ? WHERE business_id = ? " ;
		 
		      System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setDouble (1, classValue);
		      preparedStmt.setString (2, business_id);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
			      
			
		}catch(SQLException se)
		{
				se.printStackTrace();
		}catch(Exception e)
		{
				e.printStackTrace();
		}

		System.out.println("updateBusinessClass Done!");
	}
	
}
