package Project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TrainToTestFileWriter {

	public static void main(String[] args) {

		BufferedReader br = null;
		String fileNameOnly = "E:\\Spring 2016\\CSCI 680-K\\Project\\Output_Review_Weka_Output\\review_food_100_test1_java_editting";
		String fileTypeOnly = ".arff";
		String fileNameNew = fileNameOnly + "_g2"+ fileTypeOnly;
		
		String strSearch0 = "',0";
		String strSearch1 = "',1";
		String strReplace = "',?";
		
		//String 
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileNameNew, "UTF-8");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileNameOnly+fileTypeOnly));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replace(strSearch0, strReplace);
				sCurrentLine = sCurrentLine.replace(strSearch1, strReplace);
				System.out.println(sCurrentLine);
				writer.println(sCurrentLine);
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

	}
}
