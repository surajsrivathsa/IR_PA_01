
import java.io.File; 
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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
		Element date, body;
		Elements time, datetime, summary;
		
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
			
			//add all the summaies from html file, multiple summaries can be there in html file
			try {
				summary = htmlFile.getElementsByTag("summary"); 
				doc.add(new TextField("summary",summary.text(),Field.Store.YES));
			}			
			catch(Exception e)
			{}
			
			try {
				//html can have only <time> 10:00 </time> or <time datetime = "2019-09-10 10:00> this is my text <datetime>
				//also multiple dates can be present in the file, hence it is necessary to parse and add every date.
				//both of the above formats can be repeated in a file, for example a users file denoting current datetime and transaction time;
				time = htmlFile.getElementsByTag("time");
				Iterator<Element> itr = time.iterator();		
				while(itr.hasNext())
				{
					Element tmp = itr.next();
					boolean hasdatetime = tmp.hasAttr("datetime");
					if(!hasdatetime)
					{
						doc.add(new TextField("date",tmp.text(),Field.Store.YES));
						//System.out.println(tmp.text());
					}
					if(hasdatetime)
					{
						doc.add(new TextField("date",tmp.getElementsByAttribute("datetime").attr("datetime"),Field.Store.YES));
						//System.out.println(tmp.getElementsByAttribute("datetime").attr("datetime"));
					}
				}
								
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
