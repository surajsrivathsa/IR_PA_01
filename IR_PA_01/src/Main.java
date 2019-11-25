

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

public class Main {
	//"/Users/surajshashidhar/Desktop/irdata"
    public static void main(String[] args) throws IOException, ParseException
    {
    	String folderpath = args[0];
    	String querystr = args[1];
    	
    	//samplehtmlparsing.parsesamplehtml();
    	if(querystr == null)
    	{
    		System.out.println("Please provide your query, seems like null string was given");
    		System.exit(2);
    	}
    	
    	fileparser.getHtmlFiles(new File(folderpath));
    	fileparser.getTextFiles(new File(folderpath));
    	System.out.println("there are " + fileparser.htmlfiles.size()+ " html files and " + fileparser.textfiles.size() + " text files in the corpus");    	
   	
    	//Configure an index writer, here we use Englishanalyzer which by default uses vanilla version of porter stemmer

        Directory dir = new RAMDirectory();
        Analyzer analyzer = new EnglishAnalyzer();//EnglishAnalyzer has porter stemmer by default
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        
        //Index text and HTML documents and close the index writer
        indexeachtextfile.indexTextDoc(writer);
        indexeachhtmlfile.indexHTMLDoc(writer);        
        writer.close();
        
        //search for given query among the index and print the results
        searchdocuments.searchDocuments(querystr, analyzer, dir);
        
    }
}
