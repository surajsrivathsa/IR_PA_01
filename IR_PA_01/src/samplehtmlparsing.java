

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

public class samplehtmlparsing {
	
	public static void parsesamplehtml() throws IOException
	{
		
		Document htmlFile = Jsoup.parse(new File("/Users/surajshashidhar/Desktop/irdata/subdir3/myfile.html"), "ISO-8859-1"); 
		Elements time = htmlFile.getElementsByTag("time");
		Iterator<Element> ax = time.iterator();		
		while(ax.hasNext())
		{
			Element q = ax.next();
			boolean p = q.hasAttr("datetime");
			if(!p)
				System.out.println(q.text());
			if(p)
			{
				System.out.println(q.getElementsByAttribute("datetime").attr("datetime"));
			}
		}
		
		
		Elements datetime = htmlFile.getElementsByAttribute("datetime");
		if(time.size() > 0 && time.hasAttr("datetime"))
			System.out.println("time obtained is: " + time.text());
		System.out.println("time obtained is: " + datetime.attr("datetime"));
		datetime = time.attr("datetime", "datetime");
		
		Element div = htmlFile.select("time").first();
		System.out.println(div.attr("datetime"));
	}

}
