/**
Java™ Project: ICS4U
Package: finalProject
Class: Letter
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class serves as a template to create a Letter object, which would have to do ore things than just an
ordinary char. As a result, this class represents the individual letters of a word in a game, and handles their equality, and what colour they
must be. Since letters are a visual display, Letter() must inherit from JTextPane so it can be shown on the GUI. Letter() has all the functions
and methods from the parent class.
*/

package finalProject; //The name of the class that handles all the logic

//Import Graphical Elements
import java.awt.Color; //Import Colour Class for a Custom Green, Yellow, and Gray that matched the original wordle
import java.awt.Font; //Import Font Class
import javax.swing.JTextPane; //Import Parent Class to inherit from

//Import Font Attribute Elements
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Letter extends JTextPane //The name of the class that inherits from JTextPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Declare all attributes of the class
	private int letterState; //Non-static integer to hold the state of the colour of a letter (see constants below)
	private int letterIndex; //Non-static integer to hold the index within the word that this letter represents
	private static StyledDocument sortingTextStyle; //The style for the JTextPanes to centre the text always, no matter the size
	private static SimpleAttributeSet centerAlignment; //The aforementioned centre alignment
	private static Font letterFont; //Font for the letters
	private static Color wordleGreen, wordleYellow, wordleGray; //Three custom colours which exactly match the original wordle colours
	
	//Declare all constants
	public static final int WHITE = 0, GRAY = 1, YELLOW = 2, GREEN = 3; //Public integers that other classes can access to set the colour state
	public static final boolean IS_EDITABLE = true, NOT_EDITABLE = false; //Public Booleans that other classes can access to set the visibility
	public static final boolean VISIBLE = true, INVISIBLE = false; //Public Booleans that other classes can access to set the editability
	public static final int DIMMENSION = 85; //Public integer that sets the dimension of the letter in pixels
	
	public Letter(String letterAsString, int index) //Constructor, which takes the parameters of a String and the index within the word
	{
		constructColoursAndFont(); //Construct Colours 
		
		this.letterIndex = index; //Set the index for the object to the one taken as a parameter
		this.letterState = WHITE; //Set the state of the letter to white by default
		this.setText(letterAsString); //Set the text displayed to the String taken as a parameter 
		//This has to be String and not simply a char because the setText method only takes a String as a parameter
		
		//Set Font Attributes
		sortingTextStyle = this.getStyledDocument(); //Declare the text style for the Letter
		centerAlignment = new SimpleAttributeSet(); //Declare the alignment attribute for the Letter
		StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER); //Set the attribute to a centre alignment
		sortingTextStyle.setParagraphAttributes(0, sortingTextStyle.getLength(), 
				centerAlignment, false); //Apply this attribute to the text style for the Letter
		
		//Set Letter Attributes
		this.setForeground(Color.BLACK); //Set the colour of the Letter's text to black by default
		this.setBackground(Color.WHITE); //Set the Letter's background to white by default
		this.setHighlighter(null);; //Do not allow the user to highlight the text for the Letter
		this.setEditable(NOT_EDITABLE); //Do not allow the user to edit the text for the Letter
		this.setFont(letterFont); //Set the font of the Letter to a custom one
		this.setVisible(INVISIBLE); //Make the Letter invisible by default
	}
	
	private static void constructColoursAndFont() //Private method which acts as an extension for the constructor to set the colours and fonts
	{
		//Set the three colours
		wordleGreen = new Color (106, 170, 100); //Set Green
		wordleYellow = new Color (201, 180, 88); //Set Yellow
		wordleGray = new Color (120, 124, 126); //Set Gray
		
		//Set the font
		letterFont = new Font("Helvetica", Font.PLAIN, 60); //Font for the letters is Helvetica, size 60 to fit in 85 x 85 pixels
	}
	
	public int getState() //Public getter method which returns the state (i.e., current colour). This is called by the Colour Changing Class
	{
		return this.letterState; //Simply return the letter state attribute of this class
	}
	
	public void setState(int state) //Public setter method which sets a new state (colour) for a letter. This is called by the GameManager
	{ //Takes the parameter of the int.
		
		this.letterState = state; //Set the attribute to the parameter taken by the method
	}
	
	public int getIndex() //Public getter method which returns the index of the letter within the word
	{
		return this.letterIndex; //Simply return the letter index attribute of this class
	}
	
	public static Color getGreen() //Public getter method which returns the green colour to change the letter
	{
		return wordleGreen; //Simply return the static green colour
	}
	
	public static Color getYellow() //Public getter method which returns the yellow colour to change the letter
	{
		return wordleYellow; //Simply return the static green colour
	}
	
	public static Color getGray() //Public getter method which returns the gray colour to change the letter
	{
		return wordleGray; //Simply return the static green colour
	}
	
	public boolean equals (Word fromUser, int index) //Public equals method which compares a letter to specific character in a word
	{ //Takes the parameter of the word and the index of the letter being compared, within the word. It returns the equality as true or false
		
		return (Character.toLowerCase(this.getText().charAt(0)) == Character.toLowerCase(fromUser.toString().charAt(index))); 
		//If the letter equals the character at the specified index within the word, return true. Otherwise, false;
	}
}