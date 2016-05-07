package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Parse.BusinessMySQLManger;
import Parse.JsonReader;
import Parse.ReviewMySQLManager;

public class ReviewMySQLImporter {

	public static void main(String x[])
	{
		//String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_review.json";
		String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_review_import4.json";
		JsonReader jsonReader = new JsonReader(fileName);
		ReviewMySQLManager reviewMySQLManger = new ReviewMySQLManager();
		String firstReview_id = "dtsvLo-vVL6NwZh912T4UQ";
		
		Scanner scn = null;
				
		try {
			scn = new Scanner(new File(fileName), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int MAX = 100000000; // the maximum number of records
		//int MAX = 5; // the maximum number of records
		int count = 0;
		Integer i = 0;
		
		Boolean isStarted = false;

	    while(scn.hasNext() && count < MAX)
	    {
	        JSONObject obj = null;
			try {
				obj = (JSONObject) jsonReader.ReadOneJSON(scn.nextLine());
				
				//System.out.println("["+(i++).toString()+"] "+(String)obj.get("business_id")+" : |"+(String)obj.get("date")+"| : "+(long)obj.get("stars")+" : "+(String)obj.get("text"));
				
				//if((String)obj.get("review_id").equals(firstReview_id))
				if(firstReview_id.equals((String)obj.get("review_id")))
				{
					isStarted = true;
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					System.out.println("Time: "+dateFormat.format(date)); //2014/08/06 15:59:48
					System.out.println("The record with review_id: "+firstReview_id+". Started reviews insertion. Need to delete this review because will be two in your database review table.");
				}
				
				if(isStarted)
					reviewMySQLManger.insertReview((String)obj.get("review_id"),(String)obj.get("business_id"), (String)obj.get("user_id"), (long)obj.get("stars"), (String)obj.get("text"), (String)obj.get("date"));

				// to accelerate inserting review records, exlude the following part for vote insertion
				/*
				JSONObject objVote = (JSONObject) obj.get("votes");
				
				Set<String> keys = objVote.keySet();

				for(String key : keys)
				{
				    //System.out.println("vote key: "+key + ", value: "+(long)objVote.get(key));
				    reviewMySQLManger.insertVote((String)obj.get("review_id"), key, (long)objVote.get(key));
				}
				    */
				//System.out.println("vote: "+(String)objVote.get("votes"));
				
				//businessMySQLManger.insertBusiness((String)obj.get("business_id"), (String)obj.get("name"), (String)obj.get("full_address"), (String)obj.get("city"), (String)obj.get("state"), (double)obj.get("latitude"), (double)obj.get("longitude"), (double)obj.get("stars"), (long)obj.get("review_count"));
				/*
		    	JSONArray categories = (JSONArray)obj.get("votes");
		    	
		    	Iterator<String> iterator = categories.iterator();
		    	String category = null;
		    	
		    	while (iterator.hasNext()) {
		    		category = iterator.next().toString();
					System.out.println("("+ category + ")");
					businessMySQLManger.insertBusinessCategory((String)obj.get("vote type"), category);
				}
				*/
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        count++;
	    }

	}
	
	
}
