/* File name: AnotherFileExample.java
 * ----------------------------------
 * Date: 10-03-2015
 * Programmer: Peter Lock
 * 
 */


import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class AnotherFileExample extends ConsoleProgram {
	
	private BufferedReader openFile(String prompt){
		
		BufferedReader reader = null;
		
		while(reader == null){
			
			try{
				String filename = readLine(prompt);
				reader = new BufferedReader(new FileReader(filename));
			} catch (IOException ex){
				println("Nice try punk. That file doesnt exist.");
			}
		}
		return reader;
	}
	
	public void run(){
		
		this.setSize(650, 600);
		
		setFont("Courier-24");
		BufferedReader reader = openFile("Please enter the filename: ");
		
		try{
			while(true){
				String line = reader.readLine();
				if(line == null) break;
				println("Read line: [" + line + "]");
			}
			reader.close();
		} catch(IOException ex){
			throw new ErrorException(ex);
		}
	}
	
}
