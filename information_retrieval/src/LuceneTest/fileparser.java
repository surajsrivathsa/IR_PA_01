package LuceneTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;


public class fileparser 
{	
	static ArrayList<File> textfiles = new ArrayList<>();
	static ArrayList<File> htmlfiles = new ArrayList<>();
	
	
	public static ArrayList<File> getTextfiles() {
		return textfiles;
	}

	public static void setTextfiles(ArrayList<File> textfiles) {
		fileparser.textfiles = textfiles;
	}

	public static ArrayList<File> getHtmlfiles() {
		return htmlfiles;
	}

	public static void setHtmlfiles(ArrayList<File> htmlfiles) {
		fileparser.htmlfiles = htmlfiles;
	}
	
	public static void listFilesForFolder(final File folder) 
	{
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            //System.out.println(fileEntry.getAbsolutePath());
	        }
	    }
	}
	
	public static void getHtmlFiles(final File folder)
	{
		int cnt = 0;
		
		for (File fileEntry : folder.listFiles()) 
		{
	        if (fileEntry.isDirectory()) {
	            getHtmlFiles(fileEntry);
	        } else if(fileEntry.getName().endsWith(".html") || fileEntry.getName().endsWith(".htm"))
	        {
	            cnt++;
	            htmlfiles.add(fileEntry);
	        }
	       
	    }
		//System.out.println("count of files is: " + cnt);			
	}
	
	public static void getTextFiles(final File folder)
	{
		int cnt = 0;		
		for (File fileEntry : folder.listFiles()) 
		{
	        if (fileEntry.isDirectory()) {
	        	getTextFiles(fileEntry);
	        } else if(fileEntry.getName().endsWith(".txt"))
	        {
	            cnt++;
	            textfiles.add(fileEntry);
	        }
	       
	    }
		//System.out.println("count of files is: " + cnt);			
	}

	public static String parsefiles(ArrayList<File> tf) throws IOException
		
		{	
			String completefile = "";
			for ( int i = 0; i < tf.size() ;i++)
			{
				File file = tf.get(i);
				completefile = FileUtils.readFileToString(file);
				//System.out.println(completefile);
			}
			return completefile;
		}

}
/*
 * 
 * 				System.out.println("-------------------------------------");
				System.out.println("-------------------------------------");
				System.out.println("-------------------------------------");
				System.out.println("");
				System.out.println("");
				System.out.println("");
 

try (Stream<Path> paths = Files.walk(Paths.get("/Users/surajshashidhar/Desktop/ovgu/semester_1/information_retrieval"))) 
{
    paths.filter(Files::isRegularFile).forEach(System.out::println);
} 
*/