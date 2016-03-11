/*TokenizerExample.java

 *---------------------
 *Programmer: Peter Lock
 *Date: 26-02-2016
 * */


import java.util.StringTokenizer;

import acm.program.*;

public class TokenizerExample extends ConsoleProgram {

	public void printTokens(String str){
		StringTokenizer tokenizer = new StringTokenizer(str, ", ");
		for(int count = 0; tokenizer.hasMoreTokens(); count++){
			println("Token #" + count + ": " + tokenizer.nextToken());
		}
	}
	
	public void run(){
		this.setSize(650, 300);
		
		setFont("Times New Romans-24");
		String line = readLine("Enter line to tokenize: ");
		println("The tokens of the string are: ");
		printTokens(line);
	}
	
}
