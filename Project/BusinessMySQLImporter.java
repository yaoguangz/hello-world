package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Parse.BusinessMySQLManger;
import Parse.JsonReader;

public class BusinessMySQLImporter 
{

	public static void main(String x[])
	{
		String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_business.json";
		JsonReader jsonReader = new JsonReader(fileName);
		BusinessMySQLManger businessMySQLManger = new BusinessMySQLManger();
		
		Scanner scn = null;
				
		try {
			scn = new Scanner(new File(fileName), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int MAX = 100000000; // the maximum number of records
		int count = 0;
		Integer i = 0;

	    while(scn.hasNext() && count < MAX)
	    {
	        JSONObject obj = null;
			try {
				obj = (JSONObject) jsonReader.ReadOneJSON(scn.nextLine());
				
				System.out.println("["+(i++).toString()+"] "+(String)obj.get("business_id")+" : "+(String)obj.get("name")+" : "+(String)obj.get("city"));
				
				businessMySQLManger.insertBusiness((String)obj.get("business_id"), (String)obj.get("name"), (String)obj.get("full_address"), (String)obj.get("city"), (String)obj.get("state"), (double)obj.get("latitude"), (double)obj.get("longitude"), (double)obj.get("stars"), (long)obj.get("review_count"));
				
		    	JSONArray categories = (JSONArray)obj.get("categories");
		    	
		    	Iterator<String> iterator = categories.iterator();
		    	String category = null;
		    	
		    	while (iterator.hasNext()) {
		    		category = iterator.next().toString();
					System.out.println("("+ category + ")");
					businessMySQLManger.insertBusinessCategory((String)obj.get("business_id"), category);
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        count++;
	    }

	}
	
	
}
