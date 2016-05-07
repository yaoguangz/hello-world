package Project;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewServiceTestFileWriter {

	ArrayList<String> keyWords = null;
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
	
	public ReviewServiceTestFileWriter() {
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
	
	public static void main(String[] args) {
		
		ReviewServiceTestFileWriter reviewServiceTestFileWriter = new ReviewServiceTestFileWriter();
		reviewServiceTestFileWriter.keyWords = readKeyWords();
		
		Statement st;
		int counter = 0;
		String[] words = null;
		
		PrintWriter writer = null;
		Boolean isExisting = false;
		Boolean isStepDone = false;
		String outputFile = "";
		String classLabel = "";
		int i = 0;
		PrintWriter writerLog = null;
		try {
			writerLog = new PrintWriter("E:\\Spring 2016\\CSCI 680-K\\Project\\Review_Java_Classifier\\test\\service\\log.txt", "UTF-8");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
				
		try {
			st = conn.createStatement();
			String sql = ("SELECT * FROM short_review order by RAND() limit 1750;");
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
				System.out.println(counter+"] business_id:"+business_id+",review_id: "+review_id);
				writerLog.println(counter+"] business_id:"+business_id+",review_id: "+review_id);
				i = 0;
				classLabel = "0";
				isStepDone = false;
				
				 //for(String word: reviewServiceFileWriter.keyWords)
				while(i < reviewServiceTestFileWriter.keyWords.size())
				 {
					 if( text.toLowerCase().indexOf(reviewServiceTestFileWriter.keyWords.get(i).toLowerCase()) > -1 )
					 {
						 isExisting = true;
						 isStepDone = true;
						 classLabel = "1";
						 System.out.println("    +words: |"+reviewServiceTestFileWriter.keyWords.get(i)+"|");
						 writerLog.println("    +words: "+reviewServiceTestFileWriter.keyWords.get(i));
					 }
					 /*
					 else{
						 System.out.println("    -words: "+reviewServiceFileWriter.keyWords.get(i));
						 writerLog.println("    -words: "+reviewServiceFileWriter.keyWords.get(i));
					 }
					  */
					 i++;
				 }
				 
				 outputFile = "E:\\Spring 2016\\CSCI 680-K\\Project\\Review_Java_Classifier\\test\\service\\"+classLabel+"\\"+Integer.toString(counter)+"_"+review_id+".txt";
				 
				 try {
						writer = new PrintWriter(outputFile, "UTF-8");
						writer.println(text);
						writer.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}

				 ++ counter;
			}
			
			writerLog.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public static ArrayList<String> readKeyWords()
	{
		BufferedReader br = null;
		ArrayList<String> keyWords = new ArrayList<String>();
		 
		try {
			String keyWordLine;
			br = new BufferedReader(new FileReader("files\\key_words.txt"));

			while ((keyWordLine = br.readLine()) != null) {
				//System.out.println(keyWordLine);
				keyWords.add(keyWordLine);
			}
			
			for(String str: keyWords)
			{
				System.out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return keyWords;
	}
	
	// -1: not found, otherwise: found
	public static int searchWordInText(String text, String word)
	{
		return text.indexOf(word);
	}
		
}
