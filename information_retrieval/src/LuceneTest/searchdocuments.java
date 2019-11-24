package LuceneTest;

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

public class searchdocuments {
	
	static String[] fieldstobeanalyzed = new String[] {"title", "text", "body", "summary", "date"};
    static int required_results = 10;
    static String data[][] = new String[5][11];
	
	public static void searchDocuments(String querystr, Analyzer analyzer, Directory dir) throws ParseException, IOException
	{
		//Open an IndexSearcher
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fieldstobeanalyzed, analyzer);		
		Query query = parser.parse(querystr);
		
		//Search for results of the query in the index
		//get rank, path, last modification time, relevance score and in addition for HTML documents title and summary.
        System.out.println("Searching for: \"" + query + "\"");
        TopDocs results = searcher.search(query, required_results);
        int cnt = 1;
        for (ScoreDoc result : results.scoreDocs) {
            Document resultDoc = searcher.doc(result.doc);
            System.out.println("rank: " + cnt);
            System.out.println("score: " + result.score);
            System.out.println("score: " + " -- title: " + resultDoc.get("title"));
            System.out.println("score: " + " -- summary: " + resultDoc.get("summary"));
            System.out.println("score: " + " -- lastmodifiedtime: " + resultDoc.get("last_modified_time"));
            System.out.println("score: " + " -- path: " + resultDoc.get("path"));
            cnt++;
        }
        if(cnt == 0)
        	System.out.println("The query text was not found in the document corpus");
        reader.close();
	}

}
