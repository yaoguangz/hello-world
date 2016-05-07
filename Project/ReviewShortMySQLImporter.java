package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

public class ReviewShortMySQLImporter {

	public static void main(String x[])
	{
		//String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_review.json";
		//String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_review_import4.json";
		String fileName="E:\\Spring 2016\\CSCI 680-K\\Project\\Output\\yelp_review_short_all.json";
		JsonReader jsonReader = new JsonReader(fileName);
		ReviewShortMySQLManger reviewMySQLManger = new ReviewShortMySQLManger();
		String firstReview_id = "dtsvLo-vVL6NwZh912T4UQ";
		/*
		Scanner scn = null;
				
		try {
			scn = new Scanner(new File(fileName), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//int MAX = 100000000; // the maximum number of records
		int MAX = 5; // the maximum number of records
		int count = 0;
		Integer i = 0;
		*/
		
		JSONParser parser = new JSONParser();
		//int count = 10;
		
		try {

			//Object obj = parser.parse(new FileReader("E:\\Spring 2016\\CSCI 680-K\\Project\\Datasets\\Yelp\\yelp_dataset_challenge_academic_dataset\\yelp_academic_dataset_business.json"));
			//Object obj = parser.parse(new FileReader("E:\\business1.json"));

			Object jsonObj = parser.parse(new FileReader("E:\\Spring 2016\\CSCI 680-K\\Project\\Output\\yelp_review_short_all_array.json"));
			
			JSONArray jsonObjects = (JSONArray) jsonObj;
	/*
			for(JSONObject jsonObject:  jsonObjects)
			{
				String business_id = (String) jsonObject.get("name");
				System.out.println(business_id);
		
				String full_address = (String) jsonObject.get("full_address");
				System.out.println(full_address);
			}
			*/
			//for (int i = 0; i < jsonObjects.size(); ++i) {
			int num = 0;
			JSONObject obj = null;
			for (int i = 0; i < jsonObjects.size(); ++i) {
			    obj = (JSONObject) jsonObjects.get(i);
			    //String business_id = (String) obj.get("business_id");
			    //String review_id = (String) obj.get("review_id");
			    reviewMySQLManger.insertReview((String)obj.get("review_id"),(String)obj.get("business_id"), (String)obj.get("user_id"), (double)obj.get("stars"), (String)obj.get("text"), (String)obj.get("date"));
			    System.out.println("review_id:"+(String)obj.get("review_id"));
			    ++num;
			}
			
			System.out.println("total: "+num);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		

	}
	
	
}
