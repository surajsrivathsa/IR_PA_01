
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
import dnl.utils.text.table.*;
import org.apache.commons.lang.*;

public class searchdocuments {
	
	static String[] fieldstobeanalyzed = new String[] {"title", "text", "body", "summary", "date"};
    static int required_results = 10;
    static String columnnames[] = {"Rank", "Path", "Last Modified Time", "Relevance score", "Title", "Summary"};
    static String datatable[][] = new String[required_results][columnnames.length];
	
	public static void searchDocuments(String querystr, Analyzer analyzer, Directory dir) throws ParseException, IOException
	{
		//Open an IndexSearcher to read through the index files
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        
        //we need to parse multiple fields concurrently [title, body, date] for html and normal "text" for txt files
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fieldstobeanalyzed, analyzer);		
		Query query = parser.parse(querystr);
		
		//Search for results of the query in the index
		//get rank, path, last modification time, relevance score and in addition for HTML documents title and summary.
        System.out.println("The query is: " +  query );
        TopDocs results = searcher.search(query, required_results);
        int cnt = 0;
        if(results.scoreDocs.length > 0)
        {
	        for (ScoreDoc result : results.scoreDocs) 
	        {
	            Document resultDoc = searcher.doc(result.doc);
	            /*
	            System.out.println("rank: " + cnt);
	            System.out.println("score: " + result.score);
	            System.out.println("score: " + " -- title: " + resultDoc.get("title"));
	            System.out.println("score: " + " -- summary: " + resultDoc.get("summary"));
	            System.out.println("score: " + " -- lastmodifiedtime: " + resultDoc.get("last_modified_time"));
	            System.out.println("score: " + " -- path: " + resultDoc.get("path"));
	            */            
	            datatable[cnt][0] = String.valueOf(cnt+1);
	            datatable[cnt][1] = String.valueOf(resultDoc.get("path"));
	            datatable[cnt][2] = String.valueOf(resultDoc.get("last_modified_time"));
	            datatable[cnt][3] = String.valueOf(result.score);
	            if(resultDoc.get("path").endsWith(".html") || resultDoc.get("path").endsWith(".htm"))
	            {
	            	if( resultDoc.get("title") != null)
	            		datatable[cnt][4] = resultDoc.get("title");
	            	else
	            		datatable[cnt][4] = "No title found";
	            	
	            	if( resultDoc.get("summary") != null)
	            		datatable[cnt][5] = resultDoc.get("summary");
	            	else
	            		datatable[cnt][5] = "No summary found";
	            	
	            }
	            cnt++;
	        }
        }
        else if(results.scoreDocs.length == 0)
        	System.out.println("The query text was not found in the document corpus");
        
        reader.close();
        
        //for printing a table
        if(results.scoreDocs.length > 0)
        {
	        TextTable tt = new TextTable(columnnames, datatable);
	        tt.setAddRowNumbering(true);
	        tt.printTable();
        }
        
	}

}
