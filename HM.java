/*Programmers: Jared McDonald
 *Class: CSC 342, Fall 2016
 *Instructor: Dr. Becnel
 * Date created: 10/27/2016
 * Date completed: 10/29/2016
 * Algorithm(s) Used: Horspool's Algorithm, page 261-262
 * Program Purpose:
 * a. Program takes a data file named sm.txt to read
 * b. Program reads the file, line by line for data. The first line is called the pattern and the next line is called the text.
 * c. Program uses Horspool's Algorithm to find the pattern in the text
 * d. Program outputs the index of the pattern, if it is in the text.
 * Sample Inputs:
	gh
	aaabbbcccdddeeefffgggihaaabbbcccdddeeefffgggh
	
	efgh
	aaabbbcccdfefgh
 * Sample Outputs:
	Pattern is gh (length 2)
	Text to search is aaabbbcccdddeeefffgggihaaabbbcccdddeeefffgggh (length 45)
	Pattern located starting at index 43

	Time took: 1798097 nanoseconds
	
	Pattern is efgh (length 4)
	Text to search is aaabbbcccdfefgh (length 15)
	Pattern located starting at index 11

	Time took: 1557716 nanoseconds
 */
import java.io.BufferedReader;
import java.io.FileReader;

public class HM {

	String pattern = null; //Declare pattern
	int[] shiftby = new int[27]; //Declare shift table
	char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y','z',' '}; //All possible characters in the string
	public static void main(String[] args) throws Exception {
		new HM().nonstatic(); //Call the nonstatic Input method
	}

	public void nonstatic() throws Exception {
		int ln = 0; //Declare line number variable
		String text = " ";
		BufferedReader reader = new BufferedReader(new FileReader("sm.txt"));//BufferedReader will read the file line by line
		String read = " "; //Declaring String to read after each line is fed from the BufferedReader
		while((read = reader.readLine()) != null && ln <= 1){ //While loop to reach end of file
			if(ln == 0){ //Line number is 0
				pattern = read; //Line is the pattern to search for
				System.out.println("Pattern is "+pattern+" (length "+pattern.length()+")"); //Print the pattern and its length
				ln++; //Increment line number
			}
			else{
				text = read; //Grab the text from the read in line
				System.out.println("Text to search is "+text+" (length "+text.length()+")"); //Print the text that is to be searched
				ln++; //Increment line number
			}
		}
		long started = System.nanoTime();//Start timing
		int result = Horspool(pattern.toCharArray(),text.toCharArray()); //Call Horspool function, passing char arrays of pattern and text
		long diff = System.nanoTime() - started; //Calculate execution time
		if(result != -1){ //If the pattern was in the string
			System.out.println("Pattern located starting at index "+result); //Print the index of where it starts
		}
		else{ //If the pattern was not in the string
			System.out.println("Pattern not found in text!"); //Print it was not found
		}
		System.out.println("\nTime took: "+diff+" nanoseconds"); //Print execution time
		if(result != -1){ //If the pattern was found, print text from the starting index
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

	public int Horspool(char[] pattern, char[] text) { //Find the pattern in the text using Horspool's Matching algorithm and return the index or -1
		shiftTable(pattern); //Generate the shift table
		int m = pattern.length; //Get length of pattern
		int n = text.length; //Get length of text
		int i = m-1; //the number of characters in the pattern-1
		while(i <= n-1){ //While i is less than or equal to the number of characters in the text-1
			int k = 0;
			while(k <= m-1 && Character.toLowerCase(pattern[m-1-k]) == Character.toLowerCase(text[i-k])){ //While k is less than m-1 and the characters are equal
				k++; //Increment k
			}
			if(k == m){ //If k is == m
				return i-m+1; //Return the index where the pattern was found
			}
			else{ //Otherwise
				i = i+ getShift(text[i]);//Shift by the appropiate amount depending on the character
			}
		}
		return -1; //Pattern not found
	}

	public void shiftTable(char[] pat) { //Generate the shift table, passing the pattern
		int lngth = pat.length; //Get length of the pattern so we don't have to pat.length whenever we need to use it
		for(int i = 0; i < 27; i++){ //For every letter in the alphabet and space
			shiftby[i] = lngth; //Set every shift to the length of the pattern initially
		}
		for(int j = 0; j <lngth-1;j++){ //For every character in the pattern
			int ascii = (int)Character.toLowerCase(pat[j]); //Calculate the ascii of the char
			shiftby[ascii-97] = lngth-1-j; //Change the shift number of the char in the shift table
			//System.out.println("Index "+(ascii-97)+"("+pat[j]+") = "+shiftby[ascii-97]);
		}
	}
	
	public int getShift(char c){ //Return how many characters to shift by, looking up in the shift table
		int index = -1; //We don't know what to look for yet
		for(int i = 0;i < 27;i++){ //For every element in the shift table
			if(chars[i] == Character.toLowerCase(c)){ //If we find the char
				index = shiftby[i]; //Grab its shift element
			}
		}
		return index; //Return shift element
	}
}
