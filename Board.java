/**
Java™ Project: ICS4U
Package: finalProject
Class: GameManager
@author: Shaan Banday
@version: 1.0

Date Created: Thursday, June 2nd, 2022.
Date Completed: Tuesday, June 21st, 2022. 

Program Description: The following program/class is the GUI for the game, and handles setting up all the graphics. The GUI inherits from JFrame
and implements the ActionListener and KeyListener interfaces. With the ActionListener, the GUI takes care of all button presses and calls the
right methods. This include methods to the GameManager, and handling things becoming visible and invisible. With the KeyListener, the GUI takes
in input only during rounds, and restricts the input to only lower-case and upper-case letters.
*/

package finalProject; //Launch the class from this package named "finalProject"

//Import graphical elements
import java.awt.*; //Import the package with all the graphical objects
import javax.swing.*; //Import the package with more graphical objects
import java.awt.event.*; //Import the ActionListener Class
import javax.swing.border.EtchedBorder; //Import the Border Class
import javax.swing.text.SimpleAttributeSet; //Import a Font Metrics Class
import javax.swing.text.StyleConstants; //Import a Font Metrics Class
import javax.swing.text.StyledDocument; //Import a Font Metrics Class
import java.util.TimerTask; //Import the TimerTask Class



import java.util.Timer; //Import the Timer class to run a task at an certain interval

public class Board extends JFrame implements ActionListener, KeyListener //The name of the class for the GUI
{ //This class inherits from JFrame and implements the ActionListener and KeyListener interfaces
	/**
	 * 
	 */
	private static final long serialVersionUID = -1487779402852134851L;
	

	//Declare all graphical attributes
	private static JPanel boardPanel; //Panel to hold everything
	private static JMenuBar wordleMenuBar; //Menu bar for the GUI
	private static JMenu help; //Menu within the Menu Bar
	private static JMenuItem questions, toOriginal; //Menu items within the help menu
	private static JLabel title, win, lose, trys, wordWas, instructionTitle, errorMessage, seconds, highScoreTitle;//JLabels for various things 
	private static JTextPane instructionSubheading; //JTextPane (allows for wrapped text), for the instructions screen
	private static JButton normalMode, backToStart, instructions, hardMode; //JButtons to do different things
	private static Color lightBlue, darkBlue, sun; //Three customs colours
	private static Font titleFont, buttonFont, messageFont, winLoseFont, trysFont; //Fonts of the same type-face, but varying size
	private static Image cursorImage, arrowImage, wordleIcon; //Image of the cursor, arrow, and the icon
	private static Cursor wordleCursor; //Cursor inside the JFrame
	private static StyledDocument wordleTextStyle; //The style for the JTextPanes to centre the text always, no matter the size
	private static SimpleAttributeSet centerAlignment; //The aforementioned centre alignment
	
	//Declare other attributes
	private static Timer hardModeTimer; //Timer for the hard mode count-down
	private static Letter [][] tiles; //2-Dimensional array of letters to be displayed on the screen
	private static Word userWord; //The word that the user enters, which is then passed to the GameManager on the press of ENTER
	private static String userWordAsString; //The same word, but as a String
	private static boolean isPlaying, gameWon, instructionScreen, isHardMode; //Booleans for various states within the game
	private static int indexInWord, guessNumber, secondsLeft; //Integers for the index being referenced, the guess number, and time left
	
	//Declare all constants
	private static final short F_WIDTH = 1150, F_HEIGHT = 750; //Width and Height of the JFrame
	private static final short DELAY = 300; //Delay for all Thread.sleep calls in the program
	
	public Board() //Constructor which initialises the GUI, and displays all the proper graphical components.
	{		
		super("WORDLE Unlimited"); //Name of JFrame/window
		
		//Call extension methods for the constructor
        constructGUI(); //Construct the GUI
        constructFonts(); //Construct the fonts
        constructColours(); //Construct the colours
        constructMenu(); //Construct the Menu
        constructCursorAndImages(); //Construct the cursor and the images
		constructLabels(); //Construct the JLabels
		constructButtons(); //Construct the JButtons
		constructTiles(); //Construct the tiles (2-d array of letters)

		this.repaint(); //Invoke paint method
		enableButtons(); //Enable the button after a delay
		this.repaint(); //Invoke paint method again
	}
	
	private void constructGUI() //Private constructor method which is an extension of the original constructor to set the GUI elements
	{ //Since it set's all the GUI methods, and refers to "this" as the JFrame, it cannot be referenced statically
		
		//GUI Initialisations
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Terminate the program, and close the window if the close button is hit
        this.setSize(F_WIDTH, F_HEIGHT); //Set the size of the window (JFrame) in pixels to constants initialised outside constructor
        this.setResizable(false); //Window is unable to be resized.
        this.setVisible(true); //Everything in the JFrame is visible, unless otherwise specified  
		this.addKeyListener(this); //Add the key listener
		this.setFocusable(true); //Make JFrame the focal point if it is used
		this.setLocationRelativeTo(null); //When the GUI is constructed, always set its initial position to the centre of the screen
		//this.setAlwaysOnTop(true); //This line makes it so the GUI is always on top within the desktop over other windows open
		
		//Initialise JPanel
        boardPanel = (JPanel)this.getContentPane(); //Create a JPanel to organise contents in the JFrame/Window  
        boardPanel.setLayout(null); //Assign no layout (null) to the JPanel
        
        //Initialise primitive attributes of the class
        indexInWord = 0; //Set the index being referenced to 0
		guessNumber = 1; //Set the guess number to 1
		userWordAsString = ""; //Set the user word as an empty string
		isPlaying = false; //Set is playing to false
		gameWon = false; //Set gameWon to false
		instructionScreen = false; //Set instructionScreen to false
		isHardMode = false; //Set hard mode to false
	}
	
	private static void constructColours() //Private constructor method which is an extension of the original constructor to set colours
	{
		//Light Blue
		lightBlue = new Color(46, 119, 174); //Initialise custom light blue for the background of the JPanel
		boardPanel.setBackground(lightBlue); //Set the background of the JPanel to this custom colour
		
		//Dark Blue
		darkBlue = new Color(13, 33, 55); //Initialise custom dark blue for the buttons and border of the JPanel 
		boardPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, darkBlue)); //Set a border for the JPanel
		
		//Sun
		sun = new Color(255, 142, 43); //Initialise custom sun colour for the text
	}
	
	private static void constructFonts() //Private constructor method which is an extension of the original constructor to set fonts
	{
		titleFont = new Font("Rockwell", Font.PLAIN, 120); //Font for the title to be the biggest
		buttonFont = new Font("Rockwell", Font.ITALIC, 50); //Font for the buttons is italic
		messageFont = new Font("Rockwell", Font.PLAIN, 35); //Font for the "Not in Word List" message is smaller and plain
		winLoseFont = new Font("Rockwell", Font.PLAIN, 100); //Font for the "You WIN" or "You LOSE" is not as small but still plain
		trysFont = new Font("Rockwell", Font.PLAIN, 60); //Font to show how many tries someone got it in
	}
	
	private void constructMenu() //Private static constructor method which is an extension of the original constructor to create the Fonts
	{ //Since it also refers to "this" and the JFrame, it cannot be referenced statically
		
		//Declare and initialise the Menu bar
		wordleMenuBar = new JMenuBar(); //Construct a new menu bar
		wordleMenuBar.setBackground(darkBlue); //Set the background to darkBlue
		wordleMenuBar.setForeground(sun); //Set the font colour to sun
		
		//Initialise the Menu (a sub element of menu bar)
		help = new JMenu("Help"); //Construct a new menu and set the text to display as "Help"
		help.setBackground(darkBlue); //Set the background to darkBlue
		help.setForeground(sun); //Set the font colour to sun
		wordleMenuBar.add(help); //Add the menu to the menu bar
		
		//Initialise the First Menu Item (a sub element of menu) which takes user to external link for FAQ about wordle
		questions = new JMenuItem("Questions?"); //Construct a new menu item and set the text to display as "Questions?"
		questions.setBackground(darkBlue); //Set the background to darkBlue
		questions.setForeground(sun); //Set the font colour to sun
		questions.addActionListener(this); //Add an action listener
		help.add(questions); //Add questions to the menu
		
		help.addSeparator(); //Add a separator between the two
		
		//Initialise the Second Menu Item (a sub element of menu) which takes user to external link for the original wordle
		toOriginal = new JMenuItem("Original WORDLE"); //Construct a new menu item and set the text to display as "Original WORDLE"
		toOriginal.setBackground(darkBlue); //Set the background to darkBlue
		toOriginal.setForeground(sun); //Set the font colour to sun
		toOriginal.addActionListener(this); //Add an action listener
		help.add(toOriginal); //Add toOriginal to the menu
		
		this.setJMenuBar(wordleMenuBar); //Add the menu bar to the JFrame (therefore it exists outside of the JPanel).
		
		this.repaint(); //Repaint the screen
	}
	
	private void constructCursorAndImages() //Private constructor method. Extension of the original constructor to set cursor and images
	{ //Since it also refers to "this" and the JFrame, it cannot be referenced statically
		
		//Initialise Cursor elements
		cursorImage = Toolkit.getDefaultToolkit().getImage("cursor.png"); //Set the image for the cursor
        wordleCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point (0,0), "sortingCursor"); //Set the cursor
        boardPanel.setCursor(wordleCursor); //Add the cursor to the JPanel
        
        arrowImage = Toolkit.getDefaultToolkit().getImage("arrow.png"); //Set the arrow image
        wordleIcon = Toolkit.getDefaultToolkit().getImage("wordleIcon.png"); //Set the icon image
        
        this.setIconImage(wordleIcon); //Make the GUI's icon to the icon image
	}

	private static void constructLabels() //Private constructor method which is an extension of the original constructor to set the JLabels
	{
		//Title Label
		title = new JLabel("WORDLE Unlimited"); //Set string for the title label
		title.setHorizontalAlignment(SwingConstants.CENTER); //Make the title centre aligned
		title.setBounds(new Rectangle(0, 10, 1130, 120)); //Set the bounds of the title within the JFrame
		title.setForeground(sun); //Set the colour of the title to sun
		title.setFont(titleFont); //Set the font of the title
		boardPanel.add(title); //Add the title to the JPanel
		
		//Win Label
		win = new JLabel("YOU WIN"); //Set string for the win label
		win.setHorizontalAlignment(SwingConstants.CENTER); //Make the win label centre aligned
		win.setBounds(new Rectangle(0, 100, 1130, 120)); //Set the bounds of the win label within the JFrame
		win.setForeground(sun); //Set the colour of the win label to sun
		win.setFont(winLoseFont); //Set the font of the win label
		win.setVisible(false); //Make the win label invisible, for now
		boardPanel.add(win); //Add the win label to the JPanel
		
		//Lose Label
		lose = new JLabel("YOU LOSE"); //Set string for the lose label
		lose.setHorizontalAlignment(SwingConstants.CENTER); //Make the lose label centre aligned
		lose.setBounds(new Rectangle(0, 100, 1130, 120)); //Set the bounds of the lose label within the JFrame
		lose.setForeground(sun); //Set the colour of the lose label to sun
		lose.setFont(winLoseFont); //Set the font of the lose label
		lose.setVisible(false); //Make the lose label invisible, for now
		boardPanel.add(lose); //Add the lose label to the JPanel
		
		//Instructions Label
		instructionTitle = new JLabel("Instructions"); //Set string for the instructions label
		instructionTitle.setHorizontalAlignment(SwingConstants.CENTER); //Make the instructions label centre aligned
		instructionTitle.setBounds(new Rectangle(0, 20, 1130, 100)); //Set the bounds of the instructions label within the JFrame
		instructionTitle.setForeground(sun); //Set the colour of the instructions label to sun
		instructionTitle.setFont(winLoseFont); //Set the font of the title
		instructionTitle.setVisible(false); //Make the instructions label invisible, for now
		boardPanel.add(instructionTitle); //Add the instructions label to the JPanel
		
		//Sub-heading JTextPane. It it more versatile than JLabels since it allows multiple lines and automatically wraps the text
		instructionSubheading = new JTextPane(); //Declare a new text pane to hold the sub-heading for the instructions screen
		instructionSubheading.setText("Guess the WORDLE in six tries. Each guess must be a five-letter word. Hit the ENTER button to submit. "
				+ "After each guess, the colour of the "
				+ "tiles will change to show how close your guess was to the word. "
				+ "For the Hard Mode, the word must be guessed before the timer runs out."); //Set the text for the sub-heading
		
		wordleTextStyle = instructionSubheading.getStyledDocument(); //Declare the text style for the text pane
		centerAlignment = new SimpleAttributeSet(); //Declare the attribute for the text pane
		StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER); //Set the attribute to a centre alignment
		wordleTextStyle.setParagraphAttributes(0, wordleTextStyle.getLength(), 
				centerAlignment, false); //Apply this attribute to the text style for the text pane
		instructionSubheading.setBounds(new Rectangle(11, 130, 1112, 365)); //Set the bounds for the sub-heading within the JFrame
		instructionSubheading.setForeground(sun); //Set the colour of the sub-heading to sun
		instructionSubheading.setBackground(boardPanel.getBackground()); //Set it's background to match whatever the JPanel has
		instructionSubheading.setHighlighter(null); //Do not allow the user to highlight the text
		instructionSubheading.setEditable(false); //Do not allow the user to edit the text
		instructionSubheading.setFont(buttonFont); //Set the font of the sub-heading to the same as the button font
		instructionSubheading.setVisible(false); //Make the sub-heading label invisible, for now
		boardPanel.add(instructionSubheading); //Add the sub-heading to the JPanel
		
		//Error Message
		errorMessage = new JLabel("Not in Word List!"); //Set string for the error message label
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER); //Make the error message centre aligned
		errorMessage.setBounds(new Rectangle(820, 50, 313, 35)); //Set the bounds of the error message within the JFrame
		errorMessage.setForeground(sun); //Set the colour of the error message to sun
		errorMessage.setFont(messageFont); //Set the font of the error message
		errorMessage.setVisible(false); //Make the error message label invisible, for now
		boardPanel.add(errorMessage); //Add the error message to the JPanel
		
	    //Seconds Label
		seconds = new JLabel("30"); //Set string for the seconds label to start at 30
		seconds.setHorizontalAlignment(SwingConstants.CENTER); //Make the seconds label centre aligned
		seconds.setBounds(new Rectangle(820, 200, 313, 120)); //Set the bounds of the seconds label within the JFrame
		seconds.setForeground(sun); //Set the colour of the seconds label to sun
		seconds.setFont(titleFont); //Set the font of the seconds label
		seconds.setVisible(false); //Make the seconds label invisible, for now
		boardPanel.add(seconds); //Add the seconds label to the JPanel
		
		//High-Score Label
		highScoreTitle = new JLabel("Today's High Score is " + GameManager.getCurrentHighScore()); //Set string for the high-score label
		highScoreTitle.setHorizontalAlignment(SwingConstants.CENTER); //Make the high-score centre aligned
		highScoreTitle.setBounds(new Rectangle(0, 400, 1130, 100)); //Set the bounds of the high-score within the JFrame
		highScoreTitle.setForeground(sun); //Set the colour of the high-score to sun
		highScoreTitle.setFont(trysFont); //Set the font of the high-score
		highScoreTitle.setVisible(false); //Make the high-score label invisible, for now
		boardPanel.add(highScoreTitle); //Add the high-score to the JPanel
	}
	
	private void constructButtons() //Private constructor method which is an extension of the original constructor to set the JButtons
	{
		//Normal Mode Button, which when pressed, takes the user to play the normal Wordle with no timer
		normalMode = new JButton("Normal Mode"); //Set the name of the normal mode button 
		normalMode.setBounds(new Rectangle (90, 300, 425, 200)); //Set the bounds of the normal mode button within the JFrame
		normalMode.setEnabled(false); //Disable the normal mode button, for now
		normalMode.addActionListener(this); //Add an action listener to respond to a button click
		normalMode.setFocusable(false); //Set the normal mode button to not be focusable with a tab press
		normalMode.setBackground(darkBlue); //Set the background of the normal mode button to darkBlue
		normalMode.setForeground(sun); //Set the text colour to sun to contrast with the darkBlue
		normalMode.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a black border
		normalMode.setFont(buttonFont); //Set the font of the normal mode button
        boardPanel.add(normalMode); //Add the normal mode button to the panel
        
		//Hard Mode Button, which when pressed, takes the user to the play the hard mode of Wordle with a 30-second count-down timer
		hardMode = new JButton("Hard Mode"); //Set the name of the hard mode button 
		hardMode.setBounds(new Rectangle (615, 300, 425, 200)); //Set the bounds of the hard mode button within the JFrame
		hardMode.setEnabled(false); //Disable the hard mode button, for now
		hardMode.addActionListener(this); //Add an action listener to respond to a button click
		hardMode.setFocusable(false); //Set the hard mode button to not be focusable with a tab press
		hardMode.setBackground(darkBlue); //Set the background of the hard mode button to darkBlue
		hardMode.setForeground(sun); //Set the text colour to sun to contrast with the darkBlue
		hardMode.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a black border
		hardMode.setFont(buttonFont); //Set the font of the hard mode button
        boardPanel.add(hardMode); //Add the hard mode button to the panel
        
        
		//Back To Start Button, which when pressed, takes the user back to the main menu
		backToStart = new JButton("Back to Start"); //Set the name of the back to start button 
		backToStart.setBounds(new Rectangle (356, 525, 425, 80)); //Set the bounds of the back to start button within the JFrame
		backToStart.setEnabled(false); //Disable the back to start button, for now
		backToStart.setVisible(false); //Make the back to start button invisible, for now
		backToStart.addActionListener(this); //Add an action listener to respond to a button click
		backToStart.setFocusable(false); //Set the back to start button to not be focusable with a tab press
		backToStart.setBackground(darkBlue); //Set the background of the back to start button to darkBlue
		backToStart.setForeground(sun); //Set the text colour to sun to contrast with the darkBlue
		backToStart.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a black border
		backToStart.setFont(buttonFont); //Set the font of the bubble sort button
        boardPanel.add(backToStart); //Add the bubble sort button to the panel
        
		//Instructions Button, which when pressed, takes the user to the instructions screen
		instructions = new JButton("How to Play"); //Set the name of the instructions button 
		instructions.setBounds(new Rectangle (356, 550, 425, 80)); //Set the bounds of the instructions button within the JFrame
		instructions.setEnabled(false); //Disable the instructions button, for now
		instructions.setVisible(true); //Make the instructions invisible, for now
		instructions.addActionListener(this); //Add an action listener to respond to a button click
		instructions.setFocusable(false); //Set the instructions button to not be focusable with a tab press
		instructions.setBackground(darkBlue); //Set the background of the instructions button to darkBlue
		instructions.setForeground(sun); //Set the text colour to sun to contrast with the darkBlue
		instructions.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)); //Add a black border
		instructions.setFont(buttonFont); //Set the font of the instructions button
        boardPanel.add(instructions); //Add the instructions button to the panel
	}
	
	private static void constructTiles() //Private constructor method which is an extension of the original constructor to create the 2-d array
	{
		//Declare the the size of the 2-d array
		tiles = new Letter[6][5]; //[rows][columns]
		
		//Declare constants to use, which reduced the code to use
		final int START_X = 313, START_Y = 30, GAP = 20; //Declare the starting (X,Y) position and the Gap between tiles
		
		//Loops
		for (int row = 0; row < 6; row++) //Start the loop at zero, and iterate through every row of the 2-d array
		{
			//Nested Loops
			for (int column = 0; column < 5; column++) //Start the loop at zero, and iterate through every column of the specified row
			{
				//Initialise the letter at [row][column]
				tiles[row][column] = new Letter("", column); //Construct the letter and give it an empty String
				
				//Decisions
				if (row == 0) //If the row is 0 (i.e, first row)
				{
					//Nested Decisions
					if (column == 0) //If the column is 0 (i.e, first column)
					{
						tiles[row][column].setBounds(new Rectangle(START_X, START_Y,
								Letter.DIMMENSION, Letter.DIMMENSION)); //Set the bounds for the first letter in the first row
					}
					
					else //Otherwise, for every other column
					{
						tiles[row][column].setBounds(new Rectangle(tiles[row][column-1].getX() + GAP + tiles[row][column-1].getWidth(),
								START_Y, Letter.DIMMENSION, Letter.DIMMENSION)); //Set the bounds for every other letter in the first row
					}
				}
				
				else //Otherwise, for every other row
				{
					//Nested Decisions
					if (column == 0) //If the column is 0 (i.e, first column)
					{
						tiles[row][column].setBounds(new Rectangle(START_X, tiles[row-1][column].getY() + GAP + 
								tiles[row-1][column].getHeight(),
								Letter.DIMMENSION, Letter.DIMMENSION)); //Set the bounds for the first letter in the every other row
					}
					
					else //Otherwise, for every other element
					{
						tiles[row][column].setBounds(new Rectangle(tiles[row][column-1].getX() + GAP + tiles[row][column-1].getWidth(), 
								 tiles[row-1][column].getY() + GAP + tiles[row-1][column].getHeight(),
								 Letter.DIMMENSION, Letter.DIMMENSION)); //Set the bounds for every other letter in the every other row 
					}
				}
				
				boardPanel.add(tiles[row][column]); //Add the Letter at [row][column] to the JPanel
			}
		}
	}
	
	private static void enableButtons() //Private method to enable the buttons after a certain delay.
	{
		/* This is needed so the buttons are not immediately enabled. If they are pressed to early, then an error is thrown because the whole
		   program has not fully complied yet. Therefore, this method gives the GUI a chance to construct everything before the user can 
		   interact with the program in any way shape or form*/
		
		//Try-and-catch
		try //Try to invoke a delay
		{
			Thread.sleep(DELAY); //Sleep for DELAY milliseconds
		} 
		
		catch (InterruptedException buttonDelay) {} //Catch any errors and do nothing
		
		//Enable buttons
		normalMode.setEnabled(true); //Enable the normal mode button
		hardMode.setEnabled(true); //Enable the hard mode button
		instructions.setEnabled(true); //Enable the instructions button
	}
	
	@Override
	public void actionPerformed(ActionEvent action) //Overrided Inherited ActionListener method, to be invoked when a button is pressed 
	{
		//Declare all Variables
		String buttonName = action.getActionCommand(); //String variable that replicates the button name
				
		//Decisions
		switch (buttonName) //Switch statement based on what the button name is
		{
			case "Normal Mode": //If the normal mode button is pressed
				toNormalLevel(); //Call method to handle logic for setting up the normal mode
				break;
			
			case "Hard Mode": //If the hard mode button is pressed
				toHardLevel(); //Call method to handle logic for setting up the hard mode
				break;
			
			case "Back to Start": //If the back to start button is pressed
				backToMainMenu(); //Call method to handle logic for going back to the main menu
				break;
				
			case "How to Play": //If the instructions button is pressed
				toInstructionScreen(); //Call method to handle logic for showing the instructions screen
				break;
				
			case "Questions?": //If the questions menu item is pressed
				GameManager.openFAQ(); //Call method to from GameManager class to handle logic for opening the FAQ link
				break;
				
			case "Original WORDLE": //If the original wordle menu item is pressed
				GameManager.openOGWordle(); //Call method to from GameManager class to handle logic for opening the Original Wordle link
				break;
				
			default: //Otherwise, if anything else is pressed, print a message to the console
				System.out.println("event error"); //Print message to console
				break;
		}
		
		this.repaint(); //Repaint the screen
	}
	
	private static void toNormalLevel() //Private command method to handle all the logic of the setting up the normal mode 
	{
		isPlaying = true; //Set isPlaying to true
		
		//Make things visible and invisible, while also making buttons enabled or disabled accordingly
		normalMode.setVisible(false); //Make the normal mode button invisible
		normalMode.setEnabled(false); //Disable the normal mode button
		hardMode.setVisible(false); //Make the hard mode button invisible
		hardMode.setEnabled(false); //Disable the hard mode button
		instructions.setVisible(false); //Make the instructions button invisible
		instructions.setEnabled(false); //Disable the instructions button
		title.setVisible(false); //Make the title button invisible
		
		//Loops
		for (int r = 0; r < 6; r++) //Start loop at 0, and iterate through every row
		{
			//Nested Loops
			for (int s = 0; s < 5; s++) //Start loop at 0, and iterate through every column
			{
				tiles[r][s].setVisible(Letter.VISIBLE); //Make the letter at [r][s] visible
			}
		}
		
		GameManager.startGame(isHardMode); //Start the game, and pass isHardMode, which would be false for this instance
	}
	
	private void toHardLevel() //Private command method to handle all the logic of the setting up the hard mode 
	{ //Method is not static since it makes a reference to the Countdown() Class

		isPlaying = true; //Set isPlaying to true
		
		//Make things visible and invisible, while also making buttons enabled or disabled accordingly
		normalMode.setVisible(false); //Make the normal mode button invisible
		normalMode.setEnabled(false); //Disable the normal mode button
		hardMode.setVisible(false); //Make the hard mode button invisible
		hardMode.setEnabled(false); //Disable the hard mode button
		instructions.setVisible(false); //Make the instructions button invisible
		instructions.setEnabled(false); //Disable the instructions button
		title.setVisible(false); //Make the title button invisible
		
		//Loops
		for (int t = 0; t < 6; t++) //Start loop at 0, and iterate through every row
		{
			//Nested Loops
			for (int u = 0; u < 5; u++) //Start loop at 0, and iterate through every column
			{
				tiles[t][u].setVisible(Letter.VISIBLE); //Make the letter at [t][u] visible
			}
		}
		
		isHardMode = true; //Set isHardMode to true.
		GameManager.startGame(isHardMode); //Start the game, and pass isHardMode, which would be true for this instance
		
		//Set up Timer
		hardModeTimer = new Timer(); //Initialise a new Timer object
		hardModeTimer.schedule(new Countdown(), 1000, 1000); //Schedule a task called Countdown with a 1 second delay, every 1 second
		secondsLeft = 30; //Set seconds left to 30
		seconds.setText("" + secondsLeft); //Update the seconds JLabel
		seconds.setVisible(true); //Make the seconds JLabel true.
	}
	
	public class Countdown extends TimerTask //Inner-class (class within a class), that inherits from TimerTask
	{
		@Override
		public void run() //Overrided method from the TimerTask parent class which is called when schedule is set for a Timer
		{
			updateSeconds(); //Call a method to update the seconds
			repaint(); //Invoke repaint to update the screen
		}//run
	}
	
	private void updateSeconds() //Method to update seconds
	{	
		//System.out.println(secondsLeft);
		//Line above prints the seconds left to the console for debugging purposes
		
		seconds.setText("" + secondsLeft); //Update the JLabel
		
		//Decisions
		if(secondsLeft == 0) //If the countdown reaches 0
		{
			hardModeTimer.cancel(); //Cancel the timer
			lose(); //Call the lose method
		}
		
		else //Otherwise, if the countdown has not reach 0 yet
		{
			secondsLeft -= 1; //Decrease the timer
		}
	}
	
	private static void backToMainMenu() //Private command method to handle all the logic of going back to the main menu
	{
		//Decisions
		if (!instructionScreen) //If not on the instruction screen
		{
			GameManager.playMenuTheme(gameWon); //Play the menu theme by calling the GameManager method
			
			//Nested Decisions
			if (gameWon) //If the game has been won
			{
				trys.setVisible(false); //Make the try's JLabel invisible
			}
			
			else //Otherwise, if the game was lost
			{
				wordWas.setVisible(false); //Make the word was JLabel invisible
			}
		}
		
		else //Otherwise, if on the instruction screen
		{
			instructionScreen = false; //Set the boolean to false now
		}
		
		//Reset all the variables
		indexInWord = 0; //Reset the index to 0
		guessNumber = 1; //Reset the guess number to 1
		userWordAsString = ""; //Reset the word to an empty string
		isPlaying = false; //Reset isPlaying to false
		isHardMode = false; //Reset hard mode to false, regardless of the previous round
		gameWon = false; //Reset gameWon to false
		
		constructTiles(); //Construct new tiles
		
		//Make things visible and invisible, while also making buttons enabled or disabled accordingly
		backToStart.setVisible(false); //Make the back to start button invisible
		backToStart.setEnabled(false); //Disable the back to start button
		normalMode.setVisible(true); //Make the normal button visible
		normalMode.setEnabled(true); //Enable the normal mode button
		hardMode.setVisible(true); //Make the hard button visible
		hardMode.setEnabled(true); //Enable the hard mode button
		instructions.setVisible(true); //Make the instructions button visible
		instructions.setEnabled(true); //Enable the instructions button
		instructionTitle.setVisible(false); //Regardless of the previous state, make the instruction title invisible
		instructionSubheading.setVisible(false); //Regardless of the previous state, make the instruction sub-heading invisible
		title.setVisible(true); //Make the title visible
		win.setVisible(false); //Regardless of the previous state, make the win title invisible
		lose.setVisible(false); //Regardless of the previous state, make the lose title invisible
		highScoreTitle.setVisible(false); //Regardless of the previous state, make the high score title invisible
	}
	
	private static void toInstructionScreen() //Private command method to handle all the logic of going to the instruction screen
	{
		instructionScreen = true; //Set the corresponding boolean to true
		
		//Make things visible and invisible, while also making buttons enabled or disabled accordingly
		normalMode.setVisible(false); //Make the normal mode button invisible
		normalMode.setEnabled(false); //Disable the normal mode button
		hardMode.setVisible(false); //Make the hard mode button invisible
		hardMode.setEnabled(false); //Disable the hard mode button
		instructions.setVisible(false); //Make the instructions button invisible
		instructions.setEnabled(false); //Disable the instructions button
		title.setVisible(false); //Make the title button invisible
		instructionTitle.setVisible(true); //Make the instruction title visible
		instructionSubheading.setVisible(true); //Make the instruction sub-heading visible
		backToStart.setVisible(true); //Make the back to start button visible
		backToStart.setEnabled(true); //Enable the back to start button
	}
	
	@Override
	public void keyPressed(KeyEvent press) {} //Inherited, unused, KeyListener method to be invoked after a key stroke is pressed
	
	@Override
	public void keyReleased(KeyEvent release) //Inherited KeyListener method to be invoked after a key stroke is released
	{ 
		//Declare all variables
		char typedChar = release.getKeyChar(); //char variable to store the key code to display back on the JPanel
		int pressedCode = release.getKeyCode(); //int variable to store the key code for all other keys
		
		//Decisions
		if (isPlaying) //If the user is playing
		{
			//Nested Decisions
			if (indexInWord < 5) //So long as the index in the word is less than 5
			{
				//Double Nested Decisions
				if ((pressedCode >= KeyEvent.VK_A) && (pressedCode <= KeyEvent.VK_Z)) //So long as the keys are in the range of A-Z
				{
					addCharsToScreen(typedChar); //Add the typed characters to the screen
				}
				//Otherwise, do not add any other characters (i.e., numbers) to the screen
			}
			
			else //Otherwise if every letter of the word is filled. The user is ready to submit the word
			{
				//Double Nested Decisions
				if (pressedCode == KeyEvent.VK_ENTER) //If the key pressed is the ENTER key
				{
					seeValid(); //This means the user wants to submit the word, so check to see if the word is valid
				}
				//Otherwise, if any other key is pressed, do nothing
			}
			
			//Separate Nested Decisions
			if (pressedCode == KeyEvent.VK_BACK_SPACE) //If the key pressed is the BACKSPACE key
			{
				backSpace(); //Call the backspace method to see if it should be possible
			}
		}
		
		this.repaint(); //Invoke paint method, and update panel 
    }
	
	private static void addCharsToScreen(char typedChar) //Private method which adds the character typed to the letter being referenced
	{ //Takes the parameter of a char
		tiles[guessNumber - 1][indexInWord].setText(Character.toString(typedChar)); //Convert character to a string and set the letter text
		userWordAsString += typedChar; //Add the char to the string representation
		indexInWord += 1; //Increase the index by 1
	}
	
	private void seeValid() //Private method which checks to see if a word is valid first, by calling the GameManager methods
	{
		//Declare variables and objects
		userWord = new Word(userWordAsString); //Construct a new word with the string representation
		boolean isValid = GameManager.checkIfValid(userWord); //Boolean for the word's validity
		
		//Decisions
		if (isValid) //If the word is valid
		{
			seeMatch(); //Call the next seeMatch method
		}
		
		else //Otherwise, if the word is not valid
		{
			errorMessage.setVisible(true); //Display the error message and do not let user go to next turn
		}
	}
	
	private void seeMatch() //Private method which checks to see if a word matches the word to be guessed
	{
		//Declare variables
		boolean isMatch = GameManager.checkWord(userWord); //Boolean for the word's match with the target
		
		//Decisions
		if(isMatch) //If the words match
		{
			win(); //The user has won; call the appropriate method
			setTrysMessage(); //Set the message which displays to user the number of try's they got the word in
		}
		
		else //Otherwise, if the words do not match
		{
			noMatch(); //No match so call the appropriate method
			
			//Nested Decisions
			if (guessNumber > 6) //If the number of guesses goes above 6
			{
				lose(); //The user is out of guesses and has lost; call the appropriate method
			}
			//Otherwise, the user has not lost yet. Continue
		}
		
		GameManager.flushArrayList(); //Regardless, flush the array list after a press of enter
	}
	
	private static void win() //Private method to hold logic for user winning
	{
		GameManager.checkIndvLetters(tiles[guessNumber - 1]); //Check the individual letters (although they will all be green)
		
		//Decisions
		if (isHardMode) //If hard mode is active
		{
			hardModeTimer.cancel(); //Cancel the timer
			seconds.setVisible(false); //Make the seconds countdown invisible
		}
		//Otherwise, continue
	
		GameManager.gameWon(isHardMode, guessNumber); //Call the GameManager's gameWon method
		
		//Reset Variables
		isHardMode = false; //Reset hard mode to false, regardless if the previous round was hard mode
		isPlaying = false; //Reset isPlaying to false
		gameWon = true; //Reset gameWon to true
		
		win.setVisible(true); //Make the win title visible
		highScoreTitle.setText("Today's High Score is " + GameManager.getCurrentHighScore()); //Set the text to show today's high score
		highScoreTitle.setVisible(true); //Make the high score label visible
	}
	
	private static void noMatch() //Private method to hold logic for no match, but when the user is still playing
	{
		GameManager.checkIndvLetters(tiles[guessNumber - 1]); //Check the letters individually
		guessNumber += 1; //Increase the number of guesses
		indexInWord = 0; //Reset the index to 0
		userWordAsString = ""; //Reset the string representation to an empty string 
	}
	
	private void lose() //Private method to hold logic for user losing
	{
		isPlaying = false; //Set isPlaying to false
		gameWon = false; //Set gameWon to false
		errorMessage.setVisible(false); //Make the error message invisible (if it is still invisible)
		this.repaint(); //Repaint the screen
		lose.setVisible(true); //Make the lose label visible
		seconds.setVisible(false); //Make the seconds label invisible
		
		//Decisions
		if (isHardMode) //If hard mode is active
		{
			hardModeTimer.cancel(); //Cancel the timer
			seconds.setVisible(false); //Make the seconds countdown invisible
		}
		//Otherwise, continue
		
		//Word Was Label
		wordWas = new JLabel("The word was:  " + GameManager.getWord()); //Set string for the word was label
		wordWas.setHorizontalAlignment(SwingConstants.CENTER); //Make the word was label centre aligned
		wordWas.setBounds(new Rectangle(0, 300, 1130, 120)); //Set the bounds of the word was label within the JFrame
		wordWas.setForeground(sun); //Set the colour of the word was label to sun
		wordWas.setFont(trysFont); //Set the font of the word was label
		wordWas.setVisible(true); //Make the word was label invisible
		boardPanel.add(wordWas); //Add the word was label to the JPanel
		
		GameManager.gameLost(isHardMode); //Call the GameManager's gameLost method
		this.repaint(); //Repaint the screen
	}
	
	private static void setTrysMessage() //Private method to hold logic for setting the number of try's message
	{
		//Declare variables
		String trysMessage = ""; //Initialise empty string for the message
		
		//Decisions
		if (guessNumber == 1) //If the user got it in 1 guess
    	{
			trysMessage = "You got it in " + guessNumber + " Try!"; //Make the word try singular
    	}
    	
    	else //Otherwise, if the user got it in more than 1 guess
    	{
    		trysMessage = "You got it in " + guessNumber + " Try's!"; //Make the word try plural
    	}
		
		//Try's Label
		trys = new JLabel(trysMessage); //Set string for the try's label
		trys.setHorizontalAlignment(SwingConstants.CENTER); //Make the try's label centre aligned
		trys.setBounds(new Rectangle(0, 250, 1130, 120)); //Set the bounds of the try's label within the JFrame
		trys.setForeground(sun); //Set the colour of the try's label to sun
		trys.setFont(trysFont); //Set the font of the try's label
		trys.setVisible(true); //Make the try's label visible
		boardPanel.add(trys); //Add the try's label to the JPanel
	}
	
	private static void backSpace() //Private method for pressing backSpace
	{
		//Decisions
		if (indexInWord != 0) //If the index in the word is not zero
		{
			indexInWord -= 1; //Decrease the index by 1
			tiles[guessNumber - 1][indexInWord].setText(""); //Clear the letter at the index
			
			userWordAsString = userWordAsString.substring(0, userWordAsString.length() - 1); //Cut down the string representation
		}
		//Otherwise, if the index is 0, do not go any more backwards 
		
		errorMessage.setVisible(false); //Make the error message invisible
	}
	
	@Override
	public void keyTyped(KeyEvent type) {} //Inherited, unused, KeyListener method to be invoked after a key stroke is typed
	
	public void paint(Graphics g) //Inherited Paint method to paint and update the JPanel. This method is invoked numerous times
	{
		super.paint(g); //Enable panel to be painted
		
		drawArrow(g); //Draw the arrow
	}
	
	private void drawArrow(Graphics g) //Private arrow drawing method which acts as an extension of the paint method
	{
		//Decisions
		if ((isPlaying) && (guessNumber <= 6)) //If the user is playing, and the guessNumber is less than or equal to 6
		{
			g.drawImage(arrowImage, 40, tiles[guessNumber - 1][0].getY() + 45, this); //Draw the arrow in line with the row being referenced
		}
	}
	
	public static void makeTilesInvisible() //Public method which makes all the letters invisible
	{
		//Try-and-catch
		try //Try to invoke a delay
		{
			Thread.sleep(DELAY); //Sleep for DELAY milliseconds
		} 
		
		catch (InterruptedException tilesDelay) {} //Catch any errors and do nothing
		
		//Loops
		for (int v = 0; v < 6; v++) //Start the loop at 0 and iterate through all the rows
		{
			//Nested Loops
			for (int w = 0; w < 5; w++) //Start the loop at 0 and iterate through all the columns
			{
				tiles[v][w].setVisible(Letter.INVISIBLE); //Set the tile at [v][w] to invisible
			}
		}

		backToStart.setVisible(true); //Make the back to start button visible
		backToStart.setEnabled(true); //Enable the back to start button
	}
} //End of Class!