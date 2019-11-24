package LuceneTest;


import java.io.File; 
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

//Read more: https://javarevisited.blogspot.com/2014/09/how-to-parse-html-file-in-java-jsoup-example.html#ixzz66CqKX53y

public class indexeachhtmlfile{ 
	
	public static void indexHTMLDoc(IndexWriter writer) throws IOException
	{
		ArrayList<File> htmldocs = fileparser.htmlfiles;
		String completefile = "";
		String title = "";	
		Document htmlFile = null;
		Element summary, date, body;
		
		for(int i = 0; i < htmldocs.size(); i++)
		{
		
			org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
			htmlFile = Jsoup.parse(htmldocs.get(i), "ISO-8859-1"); 	
			try {
				title = htmlFile.title();
				doc.add(new TextField("title",title,Field.Store.YES));
			}			
			catch(Exception e)
			{}
			
			try {
				body = htmlFile.body();
				doc.add(new TextField("body",body.text(),Field.Store.YES));
			}			
			catch(Exception e)
			{}
			
			try {
				summary = htmlFile.getElementById("summary"); 
				doc.add(new TextField("summary",summary.text(),Field.Store.YES));
			}			
			catch(Exception e)
			{}
			
			try {
				date = htmlFile.getElementById("date");
				doc.add(new TextField("date",date.text(),Field.Store.YES));
			}			
			catch(Exception e)
			{}
			
			doc.add(new TextField("path",htmldocs.get(i).getAbsolutePath(),Field.Store.YES));
			doc.add(new TextField("last_modified_time",convertTime(htmldocs.get(i).lastModified()),Field.Store.YES));
			writer.addDocument(doc);
		}
		System.out.println("Index has been created for " + htmldocs.size() + " html documents");
	}
	
	public static String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    return format.format(date);
	}
	
}
/*	
	public static void main(String args[]) throws IOException { 
		// Parse HTML String using JSoup library 
		String HTMLSTring = "<!DOCTYPE html>" + "<html>" + "<head>" + "<title>JSoup Example</title>" + "</head>" + "<body>" + "<table><tr><td><h1>HelloWorld</h1></tr>" + "</table>" + "</body>" + "</html>"; 
		Document html = Jsoup.parse(HTMLSTring); 
		String title = html.title(); 
		String body = html.body().text();
		String h1 = html.body().getElementsByTag("h1").text(); 
		System.out.println("Input HTML String to JSoup :" + HTMLSTring); 
		System.out.println("After parsing, Title : " + title); 
		System.out.println("After parsing, Body : " + body); 
		System.out.println("Afte parsing, Heading : " + h1); 
		
		Document htmlFile = null; 
		htmlFile = Jsoup.parse(new File("/Users/surajshashidhar/Desktop/irdata/subdir2/WritableComparable.html"), "ISO-8859-1"); 
		title = htmlFile.title(); 
		Element div = htmlFile.getElementById("login"); 
		try
		{
			String cssClass = div.className(); 
		}
		catch(Exception e )
		{
			//ignore
		}
		System.out.println("Jsoup can also parse HTML file directly"); 
		System.out.println("title : " + title); 
		System.out.println("body : " + htmlFile.body().text()); 
		//System.out.println("class of div tag : " + cssClass);
	}
}
*/