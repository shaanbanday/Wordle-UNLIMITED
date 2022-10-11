/**
Java™ Project: ICS4U
Package: finalProject
Class: WordleMain
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class has a main method, and creates the GameManager(), which takes care of all the logic for the
WordleGUI. Since the main method is always ran first, and since the GameManager() class does not have a main method, then there needs to be 
one to create the GameManager() object. Otherwise, the code would run, but not do anything, since Eclipse wouldn't know which method to run
first. So this main method has to be here.
*/

package finalProject; //Launch the class from this package named "finalProject"

@SuppressWarnings("unused") //Suppress any warnings of unused objects. The compiler considers the GameManager created to be unused

public class WordleMain //The name of the class that creates the GameManager object
{
	public static void main(String[] args) //Main method
	{	
		GameManager wordleManager = new GameManager(); //Create instance of GameManager called wordleManager
	}
}