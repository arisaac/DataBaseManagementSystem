import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	static Document documentation = new Document();
	static ArrayList<String> docs = new ArrayList<String>();
	public static void main(String [] args) {
		// The name of the file to open.

		File file = new File("FinalData.txt");

		try {

			Scanner sc = new Scanner(file);
			String line = null;
			while(sc.hasNextLine()){
				String doc = sc.nextLine();
				//System.out.println(doc);
				docs = documentation.Document(doc);
			}
			sc.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<Node> CS457 = new ArrayList<Node>();
		int count = 0;
		for (String obj : docs) {
			//System.out.println(obj);
			String[] objarray = obj.split(" ");
			count += 1;
			String Field;
			int Value;
			for(int i=0; i < objarray.length;){
				Field = objarray[i];
				Value = Integer.parseInt(objarray[i+1]);
				Node node = new Node(count,Field,Value);
				//System.out.println(count + " " + Field +" "+Value);
				i+=2;
				CS457.add(node);
			}
		}
		//        for(Node obj : CS457){
		//        	if(obj.field.equals("Age:") && obj.value == 25){
		//        		System.out.println(obj.id + obj.field + obj.value);
		//        	}
		//        }
		Scanner input = new Scanner(System.in);
		System.out.print("Input Nosql Query ");
		String nosql = input.nextLine();
		String[] queryspec = nosql.split("\\.");
		String collection = queryspec[1];
		String query = queryspec[2];
		//System.out.println(query);
		if(query.equals("query()")){
			for(String obj : docs){
				System.out.println(obj);
			}
		}
		else if(query.substring(0,5).equals("query")){
			if(query.contains(",")){
			String[] parameters = query.split(",");
			String condition = parameters[0].substring(6);
			String field = parameters[1].substring(1,parameters[1].lastIndexOf(')'));
			//System.out.println(field);
			query(CS457,condition,field);
			}
			else{
				String condition = query.substring(6,query.lastIndexOf(')'));
				query2(CS457,condition);
				//System.out.println(condition);
			}
		}
		else if(query.substring(0,query.lastIndexOf('(')).equals("sum")){
			//System.out.println("great");
			String field = query.substring(4,query.lastIndexOf(')'));
			//System.out.println(field);
			sum(CS457,field);
		}
		else if(query.substring(0,query.lastIndexOf('(')).equals("avg")){
			//System.out.println("great");
			String field = query.substring(4,query.lastIndexOf(')'));
			//System.out.println(field);
			avg(CS457,field);
		}
		else if(query.substring(0,query.lastIndexOf('(')).equals("max")){
			//System.out.println("great");
			String field = query.substring(4,query.lastIndexOf(')'));
			//System.out.println(field);
			max(CS457,field);
		}
		else if(query.substring(0,query.lastIndexOf('(')).equals("cartprod")){
			String[] parameters = query.split(",");
			String condition = parameters[0].substring(9);
			//System.out.println(condition);
			String field = parameters[1].substring(1,parameters[1].lastIndexOf(')'));
			//System.out.println(field);
			cartprod(CS457,condition,field);
		}
		else{
			System.out.println("query failed");
		}
	}

	private static void cartprod(ArrayList<Node> collection, String condition, String field) {
		// TODO Auto-generated method stub
		for(Node obj : collection){
			if(condition.equals(obj.field.substring(0,obj.field.lastIndexOf(':')))){
				for(Node obj2 : collection){
					if(obj.id == obj2.id && field.equals(obj2.field.substring(0, obj2.field.lastIndexOf(':')))){
						System.out.println(obj.field + " "+ obj.value + " " +obj2.field + " " +obj2.value + " ");
					}
				}
				
			}
			
		}
	}

	private static void max(ArrayList<Node> collection, String field) {
		// TODO Auto-generated method stub
		int max = 0;
		ArrayList<Node> collectionOfField = new ArrayList<Node>();
		for(Node obj : collection){
			//System.out.println(obj.field.substring(0,obj.field.lastIndexOf(':')));
			//System.out.println(field);
			if(field.equals(obj.field.substring(0,obj.field.lastIndexOf(':')))){
				//System.out.println(obj.value);
				if(max < obj.value){
					//System.out.println(obj.value);
					max = obj.value;
				}
			}
		}
		if(max != 0){
		System.out.println(max);
		}
		else{
			return;
		}
	}

	private static void avg(ArrayList<Node> collection, String field) {
		int sum = 0;
		int count = 0;
		for(Node obj : collection){
			//System.out.println(obj.field.substring(0,obj.field.lastIndexOf(':')));
			if(field.equals(obj.field.substring(0,obj.field.lastIndexOf(':')))){
				sum += obj.value;
				count++;
			}
		}
		int avg = sum / count;
		System.out.println(avg);
		
	}

	private static void sum(ArrayList<Node> collection, String field) {
		int sum = 0;
		for(Node obj : collection){
			//System.out.println(obj.field.substring(0,obj.field.lastIndexOf(':')));
			if(field.equals(obj.field.substring(0,obj.field.lastIndexOf(':')))){
				sum += obj.value;
			}
		}
		System.out.println(sum);
		
	}

	private static void query2(ArrayList<Node> collection, String condition) {
		String[] conditionFields = condition.split(" ");
		String field1 = conditionFields[0];
		String operand1 = conditionFields[1];
		int value1 = Integer.parseInt(conditionFields[2]);
		
		String field2 = conditionFields[4];
		String operand2 = conditionFields[5];
		int value2 = Integer.parseInt(conditionFields[6]);
		
		if(operand1 == "=" && operand2 == "="){
			for(Node obj : collection){
				if(field1.equals(obj.field.substring(0,obj.field.lastIndexOf(':')))){
					for(Node obj2 : collection){
						if(field2.equals(obj2.field.substring(0,obj2.field.lastIndexOf(':')))){
							if(value1 == obj.value && value2 == obj2.value && obj.id == obj2.id ){
								System.out.println(obj.field + " " + obj.value + " " + obj2.field + " " +obj2.value);
							}
						}
					}
				}
			}
		}
		if(operand1 == "=" && operand2 == ">"){
			
		}
		if(operand1 == "=" && operand2 == "<"){
		
	}
		if(operand1 == "<" && operand2 == "="){
			
		}
		if(operand1 == "<" && operand2 == ">"){
			
		}
		if(operand1 == "<" && operand2 == "<"){
			
		}
		if(operand1 == ">" && operand2 == "="){
			
		}
		if(operand1 == ">" && operand2 == "<"){
			
		}
		if(operand1 == ">" && operand2 == "<"){
			
		}
	}

	private static void query(ArrayList<Node> collection,String condition, String field){
		if(condition.length() == 0){
			//System.out.println("in null condition");
			String fields = field.replace('+', ',');
			String[] field2 = fields.split(",");
			for(int i = 0; i < field2.length; i++){
			for(Node obj : collection){
				if(field2[i].equals(obj.field.substring(0, obj.field.lastIndexOf(':')))){
					System.out.println("ID: "+obj.id + " " + obj.field + " " + obj.value + " ");
				}
			}
			//System.out.println();
			}
		}
		else if(condition.length() != 0){
			String[] conditionFields = condition.split(" ");
			String conditionField = conditionFields[0];
			String operand = conditionFields[1];
			int conditionValue = Integer.parseInt(conditionFields[2]);
			String fields = field.replace('+', ',');
			String[] field2 = fields.split(",");
			int count = 1;
			//System.out.println("out in");
			if(conditionFields != null){
				if(conditionFields[1].equals("=")){
					//System.out.println("out for loop");
					for(Node obj : collection){
						//System.out.println(conditionField);
						String objField = obj.field.substring(0,obj.field.lastIndexOf(':'));
						if(objField.equals(conditionField)&& obj.value == conditionValue){
							//System.out.println("in if statement");
							//System.out.println("out for loop");
							int id = obj.id;
							for(int k = 0;k < field2.length; k++){
								//System.out.println("in for loop");
								//System.out.println(field2[k]);
								String fieldneeded = field2[k];
								for(Node obj2 : collection){
									if(obj2.id == id && obj2.field.subSequence(0, obj2.field.lastIndexOf(':')).equals(fieldneeded) ){
										System.out.print(obj2.field + " " + obj2.value + " ");
									}
								}
								//					for(Node obj2 : collection){
								//						if(obj2.id == id && )
								//					}
							}
							System.out.println();
							//					System.out.println(count);
							//					count++; 
						}
					}
				}
				else if(conditionFields[1].equals(">")){
					//System.out.println("out for loop");
					for(Node obj : collection){
						//System.out.println(conditionField);
						String objField = obj.field.substring(0,obj.field.lastIndexOf(':'));
						if(objField.equals(conditionField)&& obj.value > conditionValue){
							//System.out.println("in if statement");
							//System.out.println("out for loop");
							int id = obj.id;
							for(int k = 0;k < field2.length; k++){
								//System.out.println("in for loop");
								//System.out.println(field2[k]);
								String fieldneeded = field2[k];
								for(Node obj2 : collection){
									if(obj2.id == id && obj2.field.subSequence(0, obj2.field.lastIndexOf(':')).equals(fieldneeded) ){
										System.out.print(obj2.field + " " + obj2.value + " ");
									}
								}
								//					for(Node obj2 : collection){
								//						if(obj2.id == id && )
								//					}
							}
							System.out.println();
							//					System.out.println(count);
							//					count++; 
						}
					}
				}
				else if(conditionFields[1].equals("<")){
					//System.out.println("out for loop");
					for(Node obj : collection){
						//System.out.println(conditionField);
						String objField = obj.field.substring(0,obj.field.lastIndexOf(':'));
						if(objField.equals(conditionField)&& obj.value < conditionValue){
							//System.out.println("in if statement");
							//System.out.println("out for loop");
							int id = obj.id;
							for(int k = 0;k < field2.length; k++){
								//System.out.println("in for loop");
								//System.out.println(field2[k]);
								String fieldneeded = field2[k];
								for(Node obj2 : collection){
									if(obj2.id == id && obj2.field.subSequence(0, obj2.field.lastIndexOf(':')).equals(fieldneeded) ){
										System.out.print(obj2.field + " " + obj2.value + " ");
									}
								}
								//					for(Node obj2 : collection){
								//						if(obj2.id == id && )
								//					}
							}
							System.out.println();
							//					System.out.println(count);
							//					count++; 
						}
					}
				}
				else{
					System.out.println("no way");
				}
			}
		}
	}
}
