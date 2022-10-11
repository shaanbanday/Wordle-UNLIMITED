/**
Java™ Project: ICS4U
Package: finalProject
Class: Word
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class serves as a template to create a word object, which would have to do more things than just an
ordinary String. Unsurprisingly, the word class represents the words within the game, and they can be valid (i.e., in the Word Array), or 
invalid. This class does not deal with the validity of words — the word bank takes care of that. All this does is create a word and establishes
certain methods that all words must have access to. Different instances of Word() are used throughout the GameManager(), WordBank(), and the 
GUI.
*/

package finalProject; //Launch the class from this package named "finalProject"

public class Word //The name of the class for Words
{
	//Declare all attributes
	private String wordAsString; //Non-static String for a basic representation of a word
	
	public Word(String s) //Constructor takes the parameter of a String
	{
		this.wordAsString = s; //Set the String for the object to the one taken as a parameter
	}
	
	public boolean equals(Word w) //Public Equals method to check if two words are equals
	{ // Takes the parameter of another word and returns a boolean
		return (this.wordAsString.equalsIgnoreCase(w.toString())); //Call their respective toString methods and compare
		
		/*Comparison ignores upper-case and lower-case. So if user typed in "CrAnE", and the word in the WordBank was "crane", 
		  it would still be valid. Regardless, if they are equal, true is returned, and if not, false.*/
	}

	@Override
	public String toString() //Public overrided toString method to return the word as a String
	{
		return this.wordAsString; //Return String attribute of class
	}
	
	public int length() //Public method to return the length of a word
	{
		return 5; //Always return 5
	}
}