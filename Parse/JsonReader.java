package Parse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class JsonReader
{
	//String FileName="E:\\business1.json";
	//String FileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_review.json";
	//String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_business.json";
	String fileName;
	
	public JsonReader(String fileName)
	{
		this.fileName = fileName;
	}
	
	public static void main(String x[])
	{
		String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_review.json";
		JsonReader jsonReader = new JsonReader(fileName);
		
		Scanner scn = null;
				
		try {
			scn = new Scanner(new File(fileName), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int MAX = 5; // the maximum number of records
		int count = 0;
		Integer i = 0;

	    //ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	    
	    while(scn.hasNext() && count < MAX)
	    {
	        JSONObject obj = null;
			try {
				//obj = (JSONObject) new JSONParser().parse(scn.nextLine());
				obj = (JSONObject) jsonReader.ReadOneJSON(scn.nextLine());
				
				System.out.println("["+(i++).toString()+"] : "+(String)obj.get("review_id")+" : "+(String)obj.get("business_id")+" : "+(String)obj.get("user_id")+" : "+(String)obj.get("text"));
		    	
		    	//System.out.println((JSONArray)obj.get("categories"));
		    	
		    	
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //json.add(obj);
	        count++;
	    }
	    
	    
		/*
		String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_business.json";
		JsonReader jsonReader = new JsonReader(fileName);
		
		Scanner scn = null;
				
		try {
			scn = new Scanner(new File(fileName), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int MAX = 5; // the maximum number of records
		int count = 0;
		Integer i = 0;

	    //ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	    
	    while(scn.hasNext() && count < MAX)
	    {
	        JSONObject obj = null;
			try {
				//obj = (JSONObject) new JSONParser().parse(scn.nextLine());
				obj = (JSONObject) jsonReader.ReadOneJSON(scn.nextLine());
				
				System.out.println("["+(i++).toString()+"] "+(String)obj.get("business_id")+" : "+(String)obj.get("name")+" : "+(String)obj.get("city"));
		    	
		    	//System.out.println((JSONArray)obj.get("categories"));
		    	
		    	JSONArray categories = (JSONArray)obj.get("categories");
		    	
		    	Iterator<String> iterator = categories.iterator();
				// Iterator<String> iterator = msg.iterator();
		    	
		    	while (iterator.hasNext()) {
					System.out.println("("+ iterator.next() + ")");
				}
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //json.add(obj);
	        count++;
	    }
	    
	    */
		
		/*
	    try 
	    {
	    	ArrayList<JSONObject> jsons = ReadJSON(new File(FileName),"UTF-8");

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    */
	}
	
	public synchronized JSONObject ReadOneJSON(String line) throws ParseException 
	{
		JSONObject obj = null;
		
		//Scanner scn = new Scanner(MyFile,Encoding);
		
		//int MAX = 5; // the maximum number of records
		//int count = 0;
		//Integer i = 0;
		
	    //ArrayList<JSONObject> json = new ArrayList<JSONObject>();

	    obj = (JSONObject) new JSONParser().parse(line);

	    return obj;
	}
	
	public static synchronized ArrayList<JSONObject> ReadJSON(File MyFile,String Encoding) throws FileNotFoundException, ParseException 
	{
		Scanner scn = new Scanner(MyFile,Encoding);
		
		int MAX = 5; // the maximum number of records
		int count = 0;
		Integer i = 0;
		
	    ArrayList<JSONObject> json = new ArrayList<JSONObject>();
	    
	    //Reading and Parsing Strings to Json
	    while(scn.hasNext() && count < MAX){
	        JSONObject obj= (JSONObject) new JSONParser().parse(scn.nextLine());
	        json.add(obj);
	        count++;
	    }
	    
	    //Here Printing Json Objects
	    for(JSONObject obj : json){
	        //System.out.println("["+(i++).toString()+"] "+(String)obj.get("business_id")+" : "+(String)obj.get("full_address")+" : "+(String)obj.get("type"));
	    	System.out.println("["+(i++).toString()+"] "+(String)obj.get("business_id")+" : "+(String)obj.get("name")+" : "+(String)obj.get("city"));
	    	
	    	//System.out.println((JSONArray)obj.get("categories"));
	    	
	    	JSONArray categories = (JSONArray)obj.get("categories");
	    	
	    	Iterator<String> iterator = categories.iterator();
			// Iterator<String> iterator = msg.iterator();
	    	
	    	while (iterator.hasNext()) {
				System.out.println("("+ iterator.next() + ")");
			}
	    	
	    }
	    
	    return json;
	}

}