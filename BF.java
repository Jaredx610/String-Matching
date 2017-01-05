/*Programmers: Jared McDonald
 *Class: CSC 342, Fall 2016
 *Instructor: Dr. Becnel
 * Date created: 10/27/2016
 * Date completed: 10/29/2016
 * Algorithm(s) Used: http://www.stoimen.com/blog/2012/03/27/computer-algorithms-brute-force-string-matching/
 * Program Purpose:
 * a. Program reads a data file, named sm.txt for two lines.
   b. Program takes the first line and saves it as the pattern.
   c. Program takes the second line and saves it as the text.
   d. Program searches the text character by character for the pattern. A count of consecutive matching characters is returned.
   e. Program outputs whether or not the pattern exists in the text and its execution time.
 * Sample Inputs:
 *	h
	aaabcdefgh
	
	gh
	aaabbbcccdddeeefffgggihaaabbbcccdddeeefffgggh
	
 * Sample Outputs:
 *  Pattern is h (length 1)
	Text to search is aaabcdefgh (length 10)
	Pattern was located starting at index 9	
	Time took: 1310773 nanoseconds

	Pattern is gh (length 2)
	Text to search is aaabbbcccdddeeefffgggihaaabbbcccdddeeefffgggh (length 45)
	Pattern was located starting at index 43

	Time took: 1428723 nanoseconds

 */
import java.io.BufferedReader;
import java.io.FileReader;

public class BF {

	String pattern = null;
	public static void main(String[] args) throws Exception {
		new BF().nonstatic(); //Call the nonstatic Input method
	}

	public void nonstatic() throws Exception {
		int ln = 0;
		String text = " ";
		BufferedReader reader = new BufferedReader(new FileReader("sm.txt"));//BufferedReader will read the file line by line
		String read = " "; //Declaring String to read after each line is fed from the BufferedReader
		while((read = reader.readLine()) != null && ln <= 1){ //While loop to reach end of file
			if(ln == 0){ //Line number is 0
				pattern = read; //Line is the pattern to search for
				System.out.println("Pattern is "+pattern+" (length "+pattern.length()+")"); //Print the pattern to search for
				ln++; //Increment line number counter
			}
			else{
				text = read; //Grab the line from the read string
				System.out.println("Text to search is "+text+" (length "+text.length()+")"); //Print the text to search
				ln++; //Increment line number counter
			}
		}
		long started = System.nanoTime();//Start timing
		int result = bruteForce(pattern,text); //Search for the pattern in text using brute force
		long diff = System.nanoTime() - started; //Get execution time
		System.out.println("\nTime took: "+diff+" nanoseconds\n");
		if(result != -1){ //If the pattern is in the string
			System.out.println("Pattern was located starting at index "+result); //Print the index
		}
		else{
			System.out.println("Pattern was not found in text!"); //Print that it wasnt found in the string
		}
		if(result != -1){ //Print where it was found
				/*for(int i = result;i < text.length();i++){
					System.out.print(i+" ");
				}
			System.out.println();
			for(int i = result;i < text.length();i++){
				System.out.print(text.charAt(i)+" ");
			}
			System.out.println();
			for(int i = 0;i < pattern.length();i++){
				System.out.print(pattern.charAt(i)+" ");
			}*/
		}
		reader.close(); //Close the BufferedReader
	}

	public int bruteForce(String pattern, String text) { //Function to find the pattern in the text using a brute force approach
		int n = text.length(); //Get the length of the text
		int m = pattern.length(); //Get the length of the pattern
		for(int i = 0; i <= n-m;i++){ //For every character in the text
			int j = 0; //Variable to hold the index while traversing the text
			while(j < m && Character.toLowerCase(text.charAt(i+j)) == Character.toLowerCase(pattern.charAt(j))){ //While j is in the text and the characters in the text and pattern are the same
				j++; //Next index
			}
			if(j == m){ //If the end of the string was reached
				return i; //Return the index we found
			}
		}
		return -1; //Pattern wasn't found. Return -1
	}
}
