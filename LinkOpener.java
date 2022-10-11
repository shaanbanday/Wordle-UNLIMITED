/**
Java™ Project: ICS4U
Package: finalProject
Class: LinkOpener
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class has methods called by the GameManager to open links for the FAQ page and the original wordle.
Since that is the only thing this class does, it does not need a constructor. 
*/

package finalProject; //Launch the class from this package named "finalProject"

import java.io.IOException; //Import the IOException Class to handle a FileNotFound Error

public class LinkOpener //The name of the class that opens the links
{
	public static void openFAQ() //Public static method called by the GameManager to open the FAQ link
	{
		//Declare link to open as a String
		String urlFAQ = "https://help.nytimes.com/hc/en-us/articles/360029050872-Word-Games-and-Logic-Puzzles#h_01FVGCB2Z00ZQMDMCYWBPWJNXB";

		//Try and Catch statement
		try //Try to open the link
		{
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlFAQ)); //Get control of the desktop and open the link
			
			/*Whatever is set as the default browser is the one that is opened. For Windows, by default it is Edge unless otherwise changed
			  Therefore, it will probably open in Edge. Regardless, the web browser does not matter*/
		} 
		
		catch (IOException faqError) {} //Catch any errors and do nothing
	}
	
	public static void openOGWordle() //Public static method called by the GameManager to open the Original Wordle link
	{
		//Declare link to open as a String
		String urlWordle = "https://www.nytimes.com/games/wordle/index.html";

		//Try and Catch statement
		try  //Try to open the link
		{
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlWordle));  //Get control of the desktop and open the link
		} 
		
		catch (IOException ogWordleError) {} //Catch any errors and do nothing
	}
}