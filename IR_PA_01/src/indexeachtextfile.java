
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import java.text.*;


public class indexeachtextfile {
	
	
	public static void indexTextDoc(IndexWriter writer ) throws IOException
	{
		ArrayList<File> txtdocs = fileparser.textfiles;
		String completefile = "";
		for(int i = 0; i < txtdocs.size(); i++)
		{
			Document doc = new Document();			
			completefile = FileUtils.readFileToString(txtdocs.get(i));
			doc.add(new TextField("text",completefile,Field.Store.YES));
			
			doc.add(new TextField("path",txtdocs.get(i).getAbsolutePath(),Field.Store.YES));
			doc.add(new TextField("last_modified_time",convertTime(txtdocs.get(i).lastModified()),Field.Store.YES));
			writer.addDocument(doc);
			
		}
		System.out.println("Index for " + txtdocs.size() + " textfiles have been created");
		//writer.close();
	}
	
	public static String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    return format.format(date);
	}

}
