package Parse;

//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;

public class BusinessMySQLManger {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/yelp";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "User1212";
	static Connection conn = null;
	static Statement stmt = null;
	
	public BusinessMySQLManger()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		BusinessMySQLManger businessMySQLManger = new BusinessMySQLManger();
		
		businessMySQLManger.insertBusiness("xx2x","yao com","twon","DeKalb","IL",2.3,3.2,(float) 4.2,100);
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("C Yao1");
		list.add("C Yao2");
		list.add("C Yao3");
		
		businessMySQLManger.insertBusinessCategories("1", list);
	 
	}//end main
	
	public void insertBusiness(String business_id, String name, String full_address, String city, String state, double latitude, double longitude, double stars, long review_count)
	{
		try
		{
			 // the mysql insert statement
		      String query = "INSERT INTO business (business_id, name, full_address, city, state, latitude, longitude, stars, review_count) " +
		  			"VALUES (?,?,?,?,?,?,?,?,?)";
		 
		      System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, business_id);
		      preparedStmt.setString (2, name);
		      preparedStmt.setString (3, full_address);
		      preparedStmt.setString (4, city);
		      preparedStmt.setString (5, state);
		      preparedStmt.setDouble (6, latitude);
		      preparedStmt.setDouble (7, longitude);
		      
		      preparedStmt.setDouble (8, stars);
		      preparedStmt.setLong(9, review_count);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
		      
			/*
			String sql = "INSERT INTO business (business_id, name, full_address, city, state, latitude, longitude, stars, review_count) " +
			"VALUES ('"+business_id+"', '"+name+"', '"+full_address+"', '"+city+"', '"+state+"', "+ latitude +"," + longitude+","+stars+","+review_count +")";
			System.out.println("sql:"+sql);
			
			stmt.executeUpdate(sql);
			*/
		}catch(SQLException se){
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		/*
		finally{
			try{
			   if(stmt!=null) conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
			
		}
		*/
		System.out.println("insertBusiness Done!");
	}
	
	
	public void insertBusinessCategory(String business_id, String category)
	{
		try
		{
			String query = "INSERT INTO category (business_id, category_label) " +
					"VALUES (?,?)";
		 
		      System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, business_id);
		      preparedStmt.setString (2, category);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
			      
			
		}catch(SQLException se)
		{
				se.printStackTrace();
		}catch(Exception e)
		{
				e.printStackTrace();
		}

		System.out.println("insertBusinessCategory Done!");
	}
	
	public void insertBusinessCategories(String business_id, ArrayList<String> categories)
	{
		try
		{
			for (String category : categories)
			{
				String query = "INSERT INTO category (business_id, category_label) " +
						"VALUES (?,?)";
			 
			      System.out.println("sql:"+query);
			      
			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setString (1, business_id);
			      preparedStmt.setString (2, category);
			 
			      // execute the preparedstatement
			      preparedStmt.execute();
			}
			
		}catch(SQLException se)
		{
				se.printStackTrace();
		}catch(Exception e)
		{
				e.printStackTrace();
		}
		
		System.out.println("insertBusinessCategories Done!");
	}
	
	public void insertBusinessPrice(String business_id, long price)
	{
		try
		{
			String query = "INSERT INTO business_price (business_id, price) " +
					"VALUES (?,?)";
		 
		      System.out.println("sql:"+query);
		      
		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, business_id);
		      preparedStmt.setLong (2, price);
		 
		      // execute the preparedstatement
		      preparedStmt.execute();
			      
			
		}catch(SQLException se)
		{
				se.printStackTrace();
		}catch(Exception e)
		{
				e.printStackTrace();
		}

		System.out.println("insertBusinessPrice Done!");
	}
	
	
	 
}
