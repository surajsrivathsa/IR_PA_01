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

public class HelloLucene {
	
    public static void main(String[] args) throws IOException, ParseException
    {
    	String folderpath = "/Users/surajshashidhar/Desktop/irdata";
    	String tst = "Lucene in Action";
    	System.out.println("byte array : " + Arrays.toString(tst.getBytes()));
    	//fileparser.listFilesForFolder(new File(folderpath));
    	fileparser.getHtmlFiles(new File(folderpath));
    	fileparser.getTextFiles(new File(folderpath));
    	System.out.println("there are " + fileparser.htmlfiles.size()+ " html files and " + fileparser.textfiles.size() + " text files in the corpus");    	
   	
    	//Configure an index writer, here we use Englishanalyzer which by default uses vanilla version of porter stemmer
        Directory dir = new RAMDirectory();
        Analyzer analyzer = new EnglishAnalyzer();//EnglishAnalyzer has porter stemmer may need to use it
        IndexWriterConfig config = new IndexWriterConfig(analyzer);//creates indices we have to give a folder path where index should be created
        IndexWriter writer = new IndexWriter(dir, config);
        
        //Index text and HTML documents and close the index writer
        indexeachtextfile.indexTextDoc(writer);
        indexeachhtmlfile.indexHTMLDoc(writer);        
        writer.close();
        
        searchdocuments.searchDocuments("date", analyzer, dir);
        
    }
}

/*
 * 
 * 
        //Create a document to index, In our project we don't have to do below steps
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        doc1.add(new TextField("text","Lucene in Action",Field.Store.YES));
        doc2.add(new TextField("text", "Lucene for Dummies", Field.Store.YES));
        doc3.add(new TextField("text","Managing Gigabytes", Field.Store.YES));
        doc2.add(new TextField("text", "The Art of Computer Science", Field.Store.YES));
        doc1.add(new TextField("text", "Hello World!", Field.Store.YES));
        Document docs[] = {doc1, doc2, doc3};
    
        //Index the document and close the writer, In our project directly take the document list from fileparser and read the contents 
        //and pipe it into indexer
        System.out.println("Indexing document: " + docs);
        for (int i = 0; i < docs.length; i++)
        {
        	writer.addDocument(docs[i]);
        
        }        
        writer.close();
    
        //Open an IndexSearcher
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
    
        //Create a query
        QueryParser parser = new QueryParser("text", analyzer);
        Query query = parser.parse("hhh lucene");
    
        //Search for results of the query in the index
        System.out.println("Searching for: \"" + query + "\"");
        TopDocs results = searcher.search(query, 10);
        int cnt = 0;
        for (ScoreDoc result : results.scoreDocs) {
            Document resultDoc = searcher.doc(result.doc);
            System.out.println("score: " + result.score + " -- text: " + resultDoc.get("text"));
            cnt++;
        }
        if(cnt == 0)
        	System.out.println("The query text was not found in the document corpus");
        reader.close();
    }
 * 
 * */
