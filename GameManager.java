/**
Java™ Project: ICS4U
Package: finalProject
Class: GameManager
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class creates an instance of the GUI and handles all the logic of Wordle. It holds the code for 
the methods which control the starting, winning, and losing of a game. The GUI — named Board() — calls those methods when a button is clicked
or if the user reaches the maximum number of moves. The GameManager() also takes care of the logic to call the WordBank() class to set a word
out of the word list. After a turn is done, the Board() passes that word to the GameManager(), which checks the word to see if there is a 
match and which letters to colour what. To do this, the GameManager makes a reference to the SlowlyCHangeColours() class, which inherits from
TimerTask to one-by-one reveal the letters and their status as grey, green, or yellow. Lastly, when the game is one, the GameManager() reads
from a file to check for the high score of the day. It uses the DateClass() from a previous assignment, which checks the computers date to
see if the date in the text file matches with "today." 
*/

package finalProject; //Launch the class from this package named "finalProject"

//Import File Reading and Writing elements
import java.io.File; //Import FileIO Class
import java.io.FileReader; //Import FileReader Class for reading the high score file, and also for opening the sounds for the game music
import java.util.Scanner; //Import the Scanner Class to actually read the high score file.
import java.io.FileWriter; //Import FileWriter Class to open the high score file to be updated
import java.io.PrintWriter; //Import PrintWriter Class to actually update the high score file
import java.io.IOException; //Import the IOException Class to handle a FileNotFound Error

//Import other elements
import java.util.ArrayList; //Import the ArrayList Class to handle checking for letter match and setting the colour accordingly
import java.util.Timer; //Import the Timer class to run a task at an certain interval
import date.DateClass; //Import DateClass to handle checking dates for the high score

//Import Audio elements
import javax.sound.sampled.AudioInputStream; //Import the AudioInputStream class which calls the right audio file
import javax.sound.sampled.AudioSystem; //Import the AudioSystem class which connects the AudioInputStream to the Clip
import javax.sound.sampled.Clip; //Import the Clip class to play and stop the music
import javax.sound.sampled.LineUnavailableException; //Import one of the exceptions to be handled
import javax.sound.sampled.UnsupportedAudioFileException; //Import the other exception to be handled

public class GameManager //The name of the class that handles all the logic
{
	//Declare all object attributes of the class (they are all static since only one instance of GameManager is created
	private static WordBank wb; //WordBank attribute that reads the text file with all the words, and sets the word
	private static Word currentWord; //Word Object to hold the word for current round. This is updated each new round
	private static ArrayList <String> lettersUsedSoFar; //ArrayList of Strings to hold what letters have been used so far in the checking
	private static Board wordleGUI; //Instance of the GUI that the GameManager creates
	private static AudioInputStream wordleMenuStream, hardModeStream, winStream, loseStream; //AudioInputStreams for each song
	private static Clip wordleMenuTheme, hardModeTheme, winTheme, loseTheme; //The clips of the songs to be played
	private static Timer changeLetterColourTimer; //Timer to run and slowly turn each letter
	
	//Declare all primitive variable attributes of the classes
	private static boolean [] greens; //Array of booleans to know which letters should be green (true for exact letter match, false for not)
	private static int year; //Integer to hold the current year (for today's high score)
	private static byte month, day, highScore; //Byte to hold the month, day, and current high-score

	//Declare all constants
	public static final boolean WIN = true, LOSE = false; //Public constants for the state of a round, available for the GUI to use
	
	public GameManager() //Constructor which initialises the GameManager
	{
		lettersUsedSoFar = new ArrayList<String>(); //Initialise the ArrayList for the letters checked
		greens = new boolean [5]; //Initialise the size of the greens array to 5, which corresponds to the maximum number of letters in a word
		
		//Loops
		for (int i = 0; i < greens.length; i++) //Start at 0, and iterate through every element of the greens array
		{
			greens[i] = false; //Set the value of the boolean at index s to false by default
		}
		
		readHighScoreFile(); //Call method to read the file which has the high scores and dates
		
		//Try-and-catch
		try //Try to create a new WordBank object
		{
			wb = new WordBank();
		} 
		
		catch (IOException wordBankError) {} //Catch any errors and do nothing
		
		wordleGUI = new Board(); //Create a new GUI object
		
		playMenuTheme(WIN); //Play the menu theme, and pass the game status of WIN by default
	}
	
	public static void startGame(boolean isHardMode) //Public static method to start a round called by the GUI
	{ //Takes the parameter of a boolean to know whether the hard mode is active, to know to play the right sound
		
		wb.setNewWord(); //Set a new word from the text file
		
		//Decisions
		if (isHardMode) //If the hard mode is active
		{
			wordleMenuTheme.close(); //Stop playing the main menu theme song
			playHardModeTheme(); //Play the hard mode theme
		}
		//Otherwise, continue playing the main menu theme song
		
		currentWord = new Word(wb.getWord()); //Set the current word by getting it from the word bank
	}
	
	public static void gameWon(boolean isHardMode, int score) //Public static method to end a round with a win, called by the GUI
	{ //Takes the parameter of a boolean to know whether the hard mode is active and the current score
		stopLevelThemes(isHardMode); //Stop the correct level theme
		playWinTheme(); //Play the win sound
		checkHighScore(score); //Check the score from the round and check to see if it is higher than today's high score
		readHighScoreFile(); //Re-read the high score file
		Board.makeTilesInvisible(); //Make the tiles invisible on the GUI
	}
	
	public static void gameLost(boolean isHardMode) //Public static method to end a round with a loss, called by the GUI
	{ //Takes the parameter of a boolean to know whether the hard mode is active. Unlike the win method, it doesn't need the score
		
		stopLevelThemes(isHardMode); //Stop the correct level theme
		playLoseTheme(); //Play the lose sound
		Board.makeTilesInvisible(); //Make the tiles invisible on the GUI
	}
	
	private static void stopLevelThemes(boolean isHardMode) //Private method to stop playing the right level sounds
	{ //It is called by the gameWon or gameLost methods and takes the parameter of the hard mode state that those methods are given
		//Decisions
		if (isHardMode) //If the hard mode is active
		{
			hardModeTheme.close(); //Stop playing the hard mode theme
		}
		
		else //Otherwise, if it is the normal mode
		{
			wordleMenuTheme.close(); //Stop playing the main menu theme, which is the same as the normal mode theme (it just continues)
		}
	}
	
	public static boolean checkIfValid(Word fromUser) //Public static method called by the GUI to check if a word typed by the user is valid
	{ //Takes the parameter of a Word object from the GUI and returns whether it is in the word bank or not
		return wb.checkIfInList(fromUser); //Return whether the word is valid by calling a method from the word bank 
	}
	
	public static boolean checkWord(Word fromUser) //Public static method called by the GUI to check if the user word matches the current word
	{ //Takes the parameter of a Word object from the GUI and returns whether is the same as the current word.
		return currentWord.equals(fromUser); //Return whether the words are equal from the user by calling a method from the word class
	}
	
	public static void checkIndvLetters(Letter [] userLetters) //Public static method to check individual letters and set their colours
	{ //Called by the GUI and takes the parameter of an array of letters for the word (in order)
		
		checkForGreens(userLetters); //Check for the greens only
		
		//Loops
		for (int j = 0; j < userLetters.length; j++) //Start at zero and iterate through every letter
		{
			//Declare all variables
			boolean alreadyUsed = false; //Set the boolean to break the first inner loop to false
			boolean breakLoop = false; //Set the boolean to break the second inner loop to false
			
			//Decisions
			if (!greens[j]) //So long as the letter at j is not already green
			{
				alreadyUsed = checkIfAlreadyUsed(userLetters[j], alreadyUsed); //Check to see if the letter has already been checked
				
				//Nested Decisions
				if (!alreadyUsed) //If the letter has not been used yet
				{
					breakLoop = checkForYellows(userLetters, breakLoop, j); //Check for the remaining letters to see if they are yellow
				}
				//Otherwise, do not bother checking the letter
			}
			//Otherwise, do not bother checking the letter
		}
		
		checkForGrays(userLetters); //Check for any remaining letters to be turned to gray
		
		changeLetterColours(userLetters); //Initialise the timer to slowly change the colour of the letters
	}

	private static void checkForGreens(Letter[] userLetters) //Private static method to check individual letters and set their colours
	{ //Takes the parameter of the Letter array that the GUI passes to the checkIndvLetters method
		
		//Loops
		for (int k = 0; k < userLetters.length; k++) //Start at zero and iterate through every letter
		{
			//Decisions
			if(userLetters[k].equals(currentWord, k)) //If the letter at k is equal to the letter at the same index in the word
			{
				userLetters[k].setState(Letter.GREEN); //Set the state of the letter to green
				greens[k] = true; //Update the boolean in the greens array to reflect this
			}
			//Otherwise, do not change the state of the letter yet
		}
	}
	
	private static boolean checkIfAlreadyUsed (Letter userLetter, boolean alreadyUsed) //Private static method to see if a letter has been used
	{ //Takes the parameter of a singular letter being referenced, and the boolean to be returned back to the checkIndvLetters method
		
		//Loops
		for (int l = 0; (l < lettersUsedSoFar.size()) && (!alreadyUsed); l++) //Start at zero and iterate through all elements of the ArrayList
		{
			//Decisions
			if (userLetter.getText().equalsIgnoreCase(lettersUsedSoFar.get(l))) //If the letter equals one in the array list that has been used
			{
				alreadyUsed = true; //Reflect this status in the boolean and break the loop
			}
			//Otherwise, continue the loop by searching through the array list
		}
		
		return alreadyUsed; //Return the boolean
	}
	
	private static boolean checkForYellows(Letter [] userLetters, boolean breakLoop, int index) //Private static method to check for yellows
	{ //Takes the parameter of the letter array passed by the GUI, a boolean to be returned back, and the index of the array being referenced
		
		//Loops
		for (int m = 0; (m < userLetters.length) && (!breakLoop); m++) //Start the loop at zero and iterate through all the letters
		{				
			//Decisions
			if (!greens[m]) //So long as the letter at m is not already green
			{
				//Nested Decisions
				if (userLetters[index].equals(currentWord, m)) //If the letter at m is equal to the letter at the same index in the word
				{
					breakLoop = true; //Set the boolean accordingly and break the loop
					
					userLetters[index].setState(Letter.YELLOW); //Set the state of the letter to yellow
					
					lettersUsedSoFar.add(userLetters[index].getText()); //Add it to the array list
				}
				//Otherwise, keep running the loop, iterating through the array
			}
		}
		
		return breakLoop; //Return the boolean back to the original method
	}
	
	private static void checkForGrays(Letter[] userLetters) //Private static method to check for any remaining grays
	{ //Takes the parameter of the letter array passed by the GUI and sets the remaining ones to gray if they have not already been changed
		
		//Loops
		for (int n = 0; n < userLetters.length; n++) //Start the loop at zero and iterate through all the letters
		{
			//Decisions
			if (userLetters[n].getState() == Letter.WHITE) //If the letter is still at the default state of white
			{
				//If this is the case, that means it is not green or yellow. If it is still white, then there was no match
				userLetters[n].setState(Letter.GRAY); //Change the state of the letter to gray
			}
			//Otherwise, do not bother checking the letter
		}
	}
	
	private static void changeLetterColours(Letter [] userLetters) //Private static method to initialise the timer to change the letter colours
	{ //Called by the GUI and takes the parameter of the array of letters passed by the GUI
		
		changeLetterColourTimer = new Timer(); //Initialise a new Timer object
		changeLetterColourTimer.schedule(new SlowlyChangeColours(userLetters, changeLetterColourTimer, wordleGUI), 0, 100); 
		
		/*Start the timer, and schedule a task by creating a new SlowlyChangeColours() class with no delay, every 100 milliseconds Give the 
		  class all the variables it needs by passing it to its constructor*/
	}
	
	public static void flushArrayList() //Public static method called by the GUI which empties the array list after a guess
	{
		lettersUsedSoFar.clear(); //Clear the whole array list
		
		//Loops
		for (int p = 0; p < greens.length; p++) //Start the loop at 0 and iterate through every letter
		{
			greens[p] = false; //Reset the boolean at index p to false
		}
	}
	
	public static String getWord() //Public static method called by the GUI to get the current word if the game was lost
	{
		return currentWord.toString(); //Return the current Word as a String
	}	
	
	public static void playMenuTheme(boolean gameState) //Public static method to play the menu theme, called by both this class and the GUI
	{ //Takes the parameter of the game state to know if it is a win or a loss
		
		//Try-and-catch
		try //Try to stop a certain song from playing
		{
			if (gameState) //If the game was won
			{
				winTheme.stop(); //Stop the win theme
			}
			
			else //Otherwise, if the game was lost
			{
				loseTheme.stop(); //Stop the lose theme
			}
		}
		
		catch (NullPointerException songNotInitialised) {} //Catch any errors and do nothing
		
		//Another try-and-catch
		try //Try to start the music
		{
			wordleMenuStream = AudioSystem.getAudioInputStream(new File("wordleMenuTheme.wav").getAbsoluteFile()); //Get the Audio file
			wordleMenuTheme = AudioSystem.getClip();  //Initialise the audio clip
			wordleMenuTheme.open(wordleMenuStream); //Open the audio file in the clip
			wordleMenuTheme.start(); //Start playing the clip
		}
		
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException mainMenuThemeError) {} //Catch any errors and do nothing
	}
	
	private static void playHardModeTheme() //Private static method to play the hard mode theme, called by this class only
	{
		//Try-and-catch
		try //Try to start the music
		{
			hardModeStream = AudioSystem.getAudioInputStream(new File("wordleHardModeTheme.wav").getAbsoluteFile()); //Get the Audio file
			hardModeTheme = AudioSystem.getClip(); //Initialise the audio clip
			hardModeTheme.open(hardModeStream); //Open the audio file in the clip
			hardModeTheme.start(); //Start playing the clip
		}
		
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException hardModeThemeError) {} //Catch any errors
	}
	
	private static void playWinTheme() //Private static method to play the win theme, called by this class only
	{
		//Try and Catch statement
		try //Try to start the music
		{
			winStream = AudioSystem.getAudioInputStream(new File("wordleWinTheme.wav").getAbsoluteFile()); //Get the Audio file
			winTheme = AudioSystem.getClip();  //Initialise the audio clip
			winTheme.open(winStream); //Open the audio file in the clip
			winTheme.start(); //Start playing the clip
		}
		
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException winThemeError) {} //Catch any errors and do nothing
	}
	
	private static void playLoseTheme() //Private static method to play the lose theme, called by this class only
	{
		//Try and Catch statement
		try //Try to start the music
		{
			loseStream = AudioSystem.getAudioInputStream(new File("wordleLoseTheme.wav").getAbsoluteFile()); //Get the Audio file
			loseTheme = AudioSystem.getClip();  //Initialise the audio clip
			loseTheme.open(loseStream); //Open the audio file in the clip
			loseTheme.start(); //Start playing the clip
		}
		
		catch (LineUnavailableException | UnsupportedAudioFileException | IOException loseThemeError) {} //Catch any errors and do nothing
	}
	
	private static void readHighScoreFile() //Private static method to read the high score file, called by this class only
	{	
		//Try and Catch statement
		try //Try to open and read the file
		{
			Scanner takeInput = new Scanner(new FileReader("WordleHighScores.txt")); //Initialise the Scanner by passing it the file to read
			
			//Take input from file
			year = takeInput.nextShort(); //Read the first line, which will always say the year
			month = takeInput.nextByte(); //Read the second line, which will always say the month
			day = takeInput.nextByte(); //Read the third line, which will always say the day
			highScore = takeInput.nextByte(); //Read the fourth line, which will always say the highScore
			
			takeInput.close(); //Close the scanner because it is done reading. It is also closed to prevent a resource leak 
		}
		
		catch (IOException fileNotFound) {} //Catch any errors and do nothing
	}
	
	private static void checkHighScore(int newHighScore) //Private static method to check if the high score should be updated
	{ //Takes the parameter of the new high score, which may or may not replace the old one
		
		//Declare Dates
		DateClass today = new DateClass(); //Initialise a new DateClass object with an empty constructor, which by default sets it to "today"
		DateClass fromFile = new DateClass(year, month, day); //Initialise a second DateClass and pass it the year, month and day from the file
		
		//Decisions
		if (!(today.equals(fromFile))) //If the date from the file is not "today"
		{
			//Update the date within the program
			year = today.getYear(); //Set the new year
			month = (byte) today.getMonth(); //Set the new month
			day = (byte) today.getDay(); //Set the new day
			
			updateScore(newHighScore); //Update the dates (and score) in the file
		}
		
		else //Otherwise, if the dates match
		{
			//Nested Decisions
			if ((newHighScore < highScore)) //If the new high score passed by the GUI is less (less guesses is better) than the previous one
			{
				updateScore(newHighScore); //Update just the score (but not the date)
			}
			//Otherwise, if the dates match AND the new score is not less than the previous one, do not update anything
		}
	}
	
	private static void updateScore(int newHighScore) //Private static method to update actually update the date and/or the high score
	{ //Takes the parameter of the new high score, which needs to replace the old one
		
		//Try and Catch statement
		try //Try to open and write to the file
		{
			//Initialise the PrintWriter by passing it the file to write to
			PrintWriter giveOutput = new PrintWriter(new FileWriter("WordleHighScores.txt"));
			
			//Give output to the file
			giveOutput.println(year); //Print the year to the first line
			giveOutput.println(month); //Print the month to the second line
			giveOutput.println(day); //Print the day to the third line
			giveOutput.println(newHighScore); //Print the high score to the fourth line
			
			giveOutput.close(); ////Close the PrintWriter because it is done reading. It is also closed to prevent a resource leak 
		}
		 
		catch (IOException fileNotFound) {} //Catch any errors and do nothing
	}
	
	public static int getCurrentHighScore() //Public static method called by the GUI to get the current high score
	{
		return highScore; //Simply return the high score as an integer
	}
	
	public static void openFAQ() //Public static method called by the GUI when the questions button is pressed
	{
		LinkOpener.openFAQ(); //Call a static method from the link opener class
	}
	
	public static void openOGWordle() //Public static method called by the GUI when the original wordle button is pressed
	{
		LinkOpener.openOGWordle(); //Call a static method from the link opener class
	}
}