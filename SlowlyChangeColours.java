/**
Java™ Project: ICS4U
Package: finalProject
Class: SlowlyChangeColours
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class inherits from TimerTask to slowly change the colour of the letters, which is called by the 
GameManager() class. It inherits the run method, but overrides to change the colours. This class also has a constructor which takes the letter
array, the Timer (to eventually stop it), and the GUI (to call it's repaint method) from the GameManager.
*/

package finalProject; //Launch the class from this package named "finalProject"

//Import necessary classes
import java.awt.Color; //Import the color classes to set the tiles
import java.util.Timer; //Import the Timer class
import java.util.TimerTask; //Import the TimerTask Class

public class SlowlyChangeColours extends TimerTask //The name of the class that inherits from TimerTask
{
	//Declare all attributes of the class
	private Letter [] userLetters; //Non-static array of letters
	private static Timer changeLetterColourTimer; //Static timer
	private static Board wordleGUI; //Static GUI
	private static int index; //Static integer to hold the current index being referenced
	
	public SlowlyChangeColours(Letter [] lettersFromUser, Timer timer, Board wordleBoard) //Constructor for the class
	{
		//Initialise all attributes based on what is passed to the constructor
		this.userLetters = lettersFromUser;
		changeLetterColourTimer = timer;
		wordleGUI = wordleBoard;
		index = 0;
	}
	
	@Override
	public void run() //Overrided method from the TimerTask parent class which is called when schedule is set for a Timer
	{
		//Decisions
		if (userLetters[index].getState() == Letter.GRAY) //If the letter at the specified index should be gray
		{
			userLetters[index].setBackground(Letter.getGray()); //Set its background to a custom gray from the Letter Class
			
		}
		
		else if (userLetters[index].getState() == Letter.YELLOW) //If the letter at the specified index should be gray
		{
			userLetters[index].setBackground(Letter.getYellow()); //Set its background to a custom yellow from the Letter Class
		}
		
		else //If the letter at the specified index should be green
		{
			userLetters[index].setBackground(Letter.getGreen()); //Set its background to a custom green from the Letter Class
		}
		
		userLetters[index].setForeground(Color.WHITE); //Regardless of the background, set its text colour to white
		
		index++; //Increase the index by one
		
		//Separate decisions
		if (index == 5) //If the index reaches 5
		{
			changeLetterColourTimer.cancel(); //Cancel the timer
		}
		//Otherwise, continue running the timer
		
		wordleGUI.repaint(); //After everything, update the GUI by calling its repaint method
	}
}