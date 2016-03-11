/* File name: ReadLine.java
 * ----------------------------------
 * Date: 10-03-2015
 * Programmer: Peter Lock
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadLine {
	
	public static void main(String[] args){
	
		try {
			BufferedReader reader = new BufferedReader(new FileReader("files/test.txt"));
		
			while(true){
				
				String line = reader.readLine();
				if(line == null) break;

				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
