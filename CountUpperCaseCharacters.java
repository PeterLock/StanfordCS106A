/* File name: CountUpperCaseCharacters.java
 * ----------------------------------------
 * Date: 10-03-2015
 * Programmer: Peter Lock
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CountUpperCaseCharacters {
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter a string: ");
		
		String sentence = reader.readLine();
		
		System.out.println(sentence);
		
		System.out.println("The string: (" + sentence + ") contains " + getUpperCaseNumber(sentence) + " uppercase letters.");
		
		reader.close();
		System.out.println("End program.");
	}

	private static int getUpperCaseNumber(String sentence) {

		int number = 0;
		
		for(int i = 0; i < sentence.length(); i++){
			
			char ch = sentence.charAt(i);
			if(Character.isUpperCase(ch)){
				number++;
			}
		}
		
		
		return number;
	}
	

}
