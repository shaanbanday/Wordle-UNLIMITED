/**
Java™ Project: ICS4U
Package: finalProject
Class: WordBank
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class handles reading the text file with all the words, and setting a new word each round. It also 
takes care of seeing if a word entered by the user is valid by using a recursive binary search algorithm to find the word. The nature of this
means the file is only read once (when the program is compiled), but afterwards all the words are added to an array, the array is simply
checked over and over again with each new round and each new guess.
*/

package finalProject; //Launch the class from this package named "finalProject"

//Import File Reading
import java.io.FileReader; //Import FileReader Class for open the text file
import java.util.Scanner; //Import the Scanner Class to actually read the text file
import java.io.IOException; //Import the IOException Class to handle a FileNotFound Error

public class WordBank //The name of the class that handles all the file reading and word validity checking
{
	//Declare all attributes of the class
	private String currentWord; //Non-static String to hold the current word
	private static String [] wordList; //Array of Strings to hold every single word after the file is read
	
	//Declare all constants
	private static final int LIST_LENGTH = 5757; //Set the length of the text file and the string array
	
	public WordBank() throws IOException //Constructor throws an IOException to handle a FileNotFound error 
	{
		wordList = new String[LIST_LENGTH]; //Initialise the string array by setting its size to 5757 (number of lines in the text file)
		
		readFile("five_letter_words_sorted.txt"); //Read the file, by passing the file name as a String
		//This means the file is only read once, when the GameManager is initially constructed
	}
	
	private static void readFile(String fileName) throws IOException //Private static file reader method also throws an IOException 
	{ //Takes the parameter of the file to be read
		
		Scanner wordReader = new Scanner(new FileReader(fileName)); //Declare a scanner to read the file passed to the method
		
		//Loops
		for (int q = 0; q < LIST_LENGTH; q++) //Start the loop at 0 and iterate through every element of the array
		{
			wordList[q] = wordReader.nextLine(); //Set the value in wordList at j to the next line read
		}	
	}
	
	public void setNewWord() //Public method to select a random word from the list to set as the current word
	{
		int randomIndex = (int) (Math.random()*LIST_LENGTH); //Initialise a random integer from 0 to 5757, but not including 5757
		
		this.currentWord = wordList[randomIndex]; //Set the current word to this random index from the array
		//System.out.println(this.currentWord);
		//Line above prints the current word to the console for debugging purposes
	}
	
	public String getWord() //Public method get the word and give it back to the GameManager
	{
		return this.currentWord; //Simply return the word as a String
	}
	
	public boolean checkIfInList(Word w) //Public method called by the GameManager to check if user inputed word is in the word list
	{ //Takes the input of the Word as a word object and returns a boolean on whether it is valid or not
		
		//Since the word list is sorted alphabetically, binary search can be used
		int right = LIST_LENGTH - 1; //Set the right side of the array to the last index (length - 1)
		int left = 0; //Set the left side of the array to the first index
		
		int index = findWord(left, right, w); //Set the index of the word by calling the findWord recursive method
		
		return (index >= 0); //If the word was not found, -1 is returned, which means the boolean return is set to whether the index is >= 0
	}
	
	private static int findWord(int leftIndex, int rightIndex, Word target) //Private recursive method which uses binary search to find the word
	{ 
		/*Takes the parameter of the first (left) and last (right) index of the sub-array, while also taking the target word. The sub-array
		  gets smaller each time the method is called, until the word is found or not*/
		
		//Decisions
		if ((rightIndex >= leftIndex) && (leftIndex < LIST_LENGTH)) //So long as the bounds of left and right make sense
		{
            int middleIndex = ((leftIndex + rightIndex) / 2); //Set the middle index to the average between left and right
 
            //Nested Decisions
            if (wordList[middleIndex].equalsIgnoreCase(target.toString())) //If the target word is at the middle index
            {
            	return middleIndex; //Word is found, so return that middle index
            }
            //Otherwise, the target has not been found yet, so narrow the array down
                
            //Separate Nested Decisions
            if((wordList[middleIndex].compareToIgnoreCase(target.toString())) < 0) //If the element is later in the array alphabetically
            {
            	return findWord(middleIndex + 1, rightIndex, target); //Search again, but now with the right half of the array
            }
            
            else //Otherwise, if the element is before in the array alphabetically
            {
            	 return findWord(leftIndex, middleIndex - 1, target); //Search again, but now with the left  half of the array
            }           
        }
		
		else //Otherwise, if the right becomes greater than left or if left reached the end of the array, the word has not been found
		{
			return -1; //Return -1 as the index
		}
	}
}