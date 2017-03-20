import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Document {
	ArrayList<String> documents = new ArrayList<String>();
	int count = 0;
	
	public ArrayList<String> Document(String object){
		documents.add(count, object);
		count++;
		return documents;
		 
	}

}
