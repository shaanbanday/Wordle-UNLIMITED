/*
Java™ Project: ICS4U
Package: date
Class: DateClass
Programmer: Shaan Banday.

Date Created: Monday, 21st March, 2022.
Date Completed: Friday, 25th March, 2022. 

Description: The following program/class defines the DateClass object that the ImportantDates class references to store the dates from the text file.
The object has 3 components: an integer to represent the year of the date, and two bytes to represent the month and day. The DateClass has methods,
which includes one that prints the date as a string, one that prints the whole written date, and various others to manipulate the dates or get any
data from the dates in some way.
*/

package date; //Launch the class from this package named "date"

import java.time.LocalDate; //Import the LocalDate scanner class, to know the current given date

public class DateClass //The name of the class that is defines the object that is being referenced by the "ImportantDates" class
{
	//Declare all variables
	private int year; //Integer to hold the year. This is an integer, since in the far future, the year may be 5, 6, or 7 digits
	private byte month, day; //Byte to hold the month and day. This is a byte since the month and day can't go over 12 and 31, respectively
	private byte dateFormat; //Byte to hold what the format of the date is. Can only be 1, 2, or 3.
	private boolean eraFormat; //Boolean to hold what format the era of the date is. Can only be true (secular format) or false (non-secular format)
	
	//Declare all constants
	public final static byte MMDDYY = 1, YYMMDD = 2, DDMMYY = 3; //Public bytes that a user can access to set the format
	public final static boolean SECULAR = true, NON_SECULAR = false; //Public booleans that that a user can access to set the era
	private final static String YEAR_ZERO = "Error. In the Gregorian calender, there is no year 0."; //String to hold the error message
	
	public DateClass (int y, int m, int d) //Constructor which initialises the object by taking in three inputs for the date
	{
		this.year = y; //Set the year to the first input when a user creates a new DateClass object
		
		this.month = (byte)m; //Set the month to the second input when a user creates a new DateClass object
		
		this.day = (byte)d; //Set the day to the third input when a user creates a new DateClass object
		
		this.dateFormat = YYMMDD; //By default, set the date format as YYMMDD
		
		this.eraFormat = SECULAR; //By default, set the era format to secular
	}
	
	public DateClass () //Another constructor which initialises the object by no input, which by default, sets the date to today's date
	{
		LocalDate currentDate = LocalDate.now(); //Set the local date to right now (which just mirrors the computer's date
		
		this.year = currentDate.getYear(); //Set the year to the current year
		
		this.month = (byte)currentDate.getMonthValue(); //Set the month to the current month
		
		this.day = (byte)currentDate.getDayOfMonth(); //Set the day to the current day
		
		this.dateFormat = YYMMDD; //By default, set the date format as YYMMDD
		
		this.eraFormat = SECULAR; //By default, set the era format to secular
	}
	
	public int getYear() //Public method if user wants the year after it has been initialised, which returns it as an integer
	{
		return this.year; //Return the year back to the user
	}
	
	public int getMonth() //Public method if user wants the month after it has been initialised, which returns it as an integer
	{
		return this.month; //Return the month back to the user
	}
	
	public String getMonthName() //Public method if user wants the current month as a String
	{
		//Declare all Variables
		String monthName = ""; //String to be returned, which represents the month name
		
		//Decisions
		switch (this.month) //Switch statement based on what the current month is
		{
			case 1: //If the month is "1" or January
				monthName = "January"; //Set the string to be returned as January
				break;
			
			case 2: //If the month is "2" or February
				monthName = "February"; //Set the string to be returned as February
				break;
			
			case 3: //If the month is "3" or March
				monthName = "March"; //Set the string to be returned as March
				break;
			
			case 4: //If the month is "4" or April
				monthName = "April"; //Set the string to be returned as April
				break;
			
			case 5: //If the month is "5" or May
				monthName = "May"; //Set the string to be returned as May
				break;
			
			case 6: //If the month is "6" or June
				monthName = "June"; //Set the string to be returned as June
				break;
			
			case 7: //If the month is "7" or July
				monthName = "July"; //Set the string to be returned as July
				break;
			
			case 8: //If the month is "8" or August
				monthName = "August"; //Set the string to be returned as August
				break;
			
			case 9: //If the month is "9" or September
				monthName = "September"; //Set the string to be returned as September
				break;
			
			case 10: //If the month is "10" or October
				monthName = "October"; //Set the string to be returned as October
				break;
			
			case 11: //If the month is "11" or November
				monthName = "November"; //Set the string to be returned as November
				break;
			
			case 12: //If the month is "12" or December
				monthName = "December"; //Set the string to be returned as December
				break;
			
			default: //If the month is any other number
				monthName = "Invalid Month"; //Set the string to be returned as an error
				break;
		}
		
		return monthName; //After the month is found (or not found), return it back to the user
	}
	
	public int getDay() //Public method if user wants the day after it has been initialised, which returns it as an integer
	{
		return this.day; //Return the day back to the user
	}
	
	public int getDayOfYear() //Public method if user wants the day of the year as a whole, which returns it as an integer
	{
		//Initialise all the variables
		boolean leapYear = isLeapYear(); //Set the boolean leap year by calling the private method
		int sumOfDays = 0; //Set the day number to zero for now
		int [] leapDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //Integer array for the number of days in each month for a leap year
		int [] normalDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //Integer array for the number of days in each month for a normal year
		
		//Decisions
		if (leapYear) //If it is a leap year
		{
			for (int i = 0; i < (this.month - 1); i++) //Start the loop at zero, and iterate through the array until before the month is reached 
			{
				//So, for any given month (n), this loop will run n-1 times, and keep on adding the days from the array to the sum
				sumOfDays += leapDays[i]; //Add the days in a specific month during a leap year to the sum
			}
		}
		
		else //If it is not a leap year
		{
			for (int i = 0; i < (this.month - 1); i++) //Start the loop at zero, and iterate through the array until before the month is reached 
			{
				sumOfDays += normalDays [i]; //Add the days in a specific month during a normal year to the sum
			}
		}
		/*Return the sum, plus the day. So for January (n = 1), the loops above will not run (n - 1 = 0), and the day number will just be returned, 
		  which makes sense since January 17th is just the 17th day of the year. For February (n = 2), the loop above will run once (n - 1 = 1), and
		  add all the days in January. Then in the return statement, all the days in January, plus whatever the day number is, will be returned. So,
		  for a date like February 17th, the loop will add 31 to the sum, and then 31 + 17 will be returned back */
		
		return (sumOfDays + this.day); //Return the sum of the previous months plus the number of days for the current month
	}
	
	private boolean isLeapYear() //Private method which is called by other methods to check if the year is a leap year
	{
		//Declare all variables
		boolean checkLeap; //Boolean to be returned and states whether or not the year is a leap year
		
		if  ((this.year % 400) == 0) //If the year is divisible by 400 (e.g, 1600 or 2000), then it is a leap year
		{
			checkLeap = true; //Set the return to true
		}
		
		else if ((this.year % 100) == 0) //Unless, if the year can also be divided by 100 (e.g, 1800 or 1900), it is NOT a leap year
		{
			checkLeap = false; //Set the return to false
		}
		
		else if ((this.year % 4) == 0) //If the year can be divided by 4 (e.g., 2016 or 2020), it is a leap year
		{
			checkLeap = true; //Set the return to true
		}
		
		else //Otherwise, if it is not divisible by 4, 100, or 400, then it is NOT a leap year
		{
			checkLeap = false; //Set the return to false
		}
		
		return checkLeap; //Return whether or not it is a leap year
	}
	
	public boolean equals(DateClass d) //Public method which returns a boolean on whether on method is equal to another
	{
		//Declare all variables
		boolean equality; //Boolean to hold the value of the equality
		
		//Decisions
		if ((this.year == d.year) && (this.month == d.month) && (this.day == d.day)) //If the year, month, and day match
		{
			equality = true; //Set the equality to true
		}
		
		else //Otherwise, if there is no match
		{
			equality = false; //Set the equality to false
		}
		
		return equality; //Return the result on whether the equality is true or false
	}
	
	public String toString() //Public method which prints the date as a string, based on the format
	{
		//Declare all variables
		String date = ""; //Initialise the string to hold the date when returned
		
		//Decisions
		if (this.year == 0) //If the year is 0, which is not possible in the Gregorian calendar
		{
			date = YEAR_ZERO; //Set the string to an error message constant
		}
		
		/* For all the year's below, it is the absolute value. If a user enters a negative year, this method takes the absolute value. However, the
		   dateAsWritten method takes care of this by adding a BC if the year is negative */
		
		else if (this.dateFormat == MMDDYY) //Otherwise, if the dateFormat is MMDDYY
		{
			date = toStringMMDDYY(); //Call an auxiliary method
		}
		
		else if (this.dateFormat == YYMMDD) //Otherwise, if the dateFormat is YYMMDD
		{
			date = toStringYYMMDD(); //Call an auxiliary method
		}
		
		else if (this.dateFormat == DDMMYY) //Otherwise, if the dateFormat is DDMMYY
		{
			date = toStringDDMMYY(); //Call an auxiliary method
		}
		
		else {} //Otherwise, do nothing if the format is set to anything else by the user (an empty string is returned)
		
		return date; //Return the resultant date
	}
	
	private String toStringMMDDYY() //Private method which is an extension of the toString method
	{
		//Declare all variables
		String dateInMMDDYY = ""; //Date as a string, to be returned to the toString method
		
		//Decisions
		if ((this.day < 10) && (this.month < 10)) //If the month and date are both single digits (e.g., February 8th)
		{
			dateInMMDDYY = "0" + this.month + "/" + Math.abs(this.year) + "/0" + this.day; //Set the date to an appropriate string by adding leading zeros
		}
		
		else if (this.day < 10) //If just the day is single digits (e.g., October 2nd)
		{
			dateInMMDDYY = this.month + "/" + Math.abs(this.year) + "/0" + this.day; //Set the date to an appropriate string by adding a leading zero
		}
		
		else if (this.month < 10) //If just the month is single digits (e.g, April 30th)
		{
			dateInMMDDYY = "0" + this.month + "/" + Math.abs(this.year) + "/" + this.day; //Set the date to an appropriate string by adding a leading zero
		}
		
		else //If the both the month and day are in double digits (e.g, December 19th)
		{
			dateInMMDDYY = this.month + "/" + Math.abs(this.year) + "/" + this.day; //Set the date normally
		}
		
		return dateInMMDDYY; //Return the date back to the toString method
	}

	private String toStringYYMMDD() //Private method which is an extension of the toString method
	{
		//Declare all variables
		String dateInYYMMDD = ""; //Date as a string, to be returned to the toString method
		
		//Decisions
		if ((this.day < 10) && (this.month < 10)) //If the month and date are both single digits (e.g., September 4th)
		{
			dateInYYMMDD = Math.abs(this.year) + "/0" + this.month + "/0" + this.day; //Set the date to an appropriate string by adding leading zeros
		}
		
		else if (this.day < 10) //If just the day is single digits (e.g., November 9th)
		{
			dateInYYMMDD = Math.abs(this.year) + "/" + month + "/0" + this.day; //Set the date to an appropriate string by adding a leading zero
		}
		
		else if (this.month < 10) //If just the month is single digits (e.g, August 23rd)
		{
			dateInYYMMDD = Math.abs(this.year) + "/0" + this.month + "/" + this.day; //Set the date to an appropriate string by adding a leading zero
		}
		
		else //If the both the month and day are in double digits (e.g, October 11th)
		{
			dateInYYMMDD = Math.abs(this.year) + "/" + this.month + "/" + this.day; //Set the date normally
		}
		
		return dateInYYMMDD; //Return the date back to the toString method
	}
	
	private String toStringDDMMYY()//Private method which is an extension of the toString method
	{
		//Declare all variables
		String dateInDDMMYY = ""; //Date as a string, to be returned to the toString method
		
		//Decisions
		if ((this.day < 10) && (this.month < 10)) //If the month and date are both single digits (e.g., March 1st)
		{
			dateInDDMMYY = "0" + this.day + "/0" + this.month + "/" + Math.abs(this.year); //Set the date to an appropriate string by adding leading zeros
		}
		
		else if (this.day < 10) //If just the day is single digits (e.g., December 7th)
		{
			dateInDDMMYY = "0" + this.day + "/" + this.month + "/" + Math.abs(this.year); //Set the date to an appropriate string by adding a leading zero
		}
		
		else if (this.month < 10) //If just the month is single digits (e.g, May 15th)
		{
			dateInDDMMYY = this.day + "/0" + this.month + "/" + Math.abs(this.year); //Set the date to an appropriate string by adding a leading zero
		}
		
		else //If the both the month and day are in double digits (e.g, November 29th)
		{
			dateInDDMMYY = this.day + "/" + this.month + "/" + Math.abs(this.year); //Set the date normally
		}
		
		return dateInDDMMYY; //Return the date back to the toString method
		
	}
	
	public void switchFormat(byte input) //Public method which switches the format. The user can decide the format with the constants
	{
		if ((input >= 1) && (input <= 3)) //So long as the input is between 1 and 3 and therefore valid
		{
			this.dateFormat = input; //Set the format to whatever the user inputs
		}
		else {} //Otherwise, if the user sets the format to anything else, do nothing
	}
	
	public void increaseDate() //Public method which increases the date by one day
	{
		//Initialise all the variables
		boolean leapYear = this.isLeapYear(); //Set the boolean leap year by calling the private method
		int [] leapDays = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //Integer array for the number of days in each month for a leap year
		int [] normalDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //Integer array for the number of days in each month for a normal year
		
		//Decisions
		if (leapYear) //If it is a leap year
		{
			//Nested Decisions
			if (this.getDayOfYear() == 366) //If the day is December 31st
			{
				//Double Nested Decisions
				if (this.year == -1) //If the year is 1 BC/BCE
				{
					this.year = 1; //Skip to 1 AD/CE, since there is no year 0
				}
				
				else //Otherwise, if is it any other year
				{
					this.year += 1; //Add one to the year
				}
				
				this.day = 1; //Reset day to the first of the next year
				this.month = 1; //Reset month to January (first month of the next year)
			}
			
			else if (this.day == leapDays[getMonth()-1]) //If the day is the last day of a particular month
			{
				this.day = 1; //Reset day to the first of the next month
				this.month += 1; //Add 1 to the month to traverse to the next month
			}
			
			else //Otherwise, if the day is any normal day
			{
				this.day += 1; //Add 1 to the day
			}
		}
		
		else //Otherwise, if it is not a leap year
		{
			//Nested Decisions
			if (this.getDayOfYear() == 365) //If the day is December 31st
			{
				//Double Nested Decisions
				if (this.year == -1) //If the year is 1 BC/BCE
				{
					this.year = 1; //Skip to 1 AD/CE, since there is no year 0
				}
				
				else //Otherwise, if is it any other year
				{
					this.year += 1; //Add one to the year
				}
				
				this.day = 1; //Reset day to the first of the next year
				this.month = 1; //Reset month to January (first month of the next year)
			}
			
			else if (this.day == normalDays[this.getMonth()-1]) //If the day is the last day of a particular month
			{
				this.day = 1; //Reset day to the first of the next month
				this.month += 1; //Add 1 to the month to traverse to the next month
			}
			
			else //Otherwise, if the day is any normal day
			{
				this.day += 1; //Add 1 to the day
			}
		}
	}
	
	public static String writtenDate (DateClass d) //Public static method which displays the date in a written version
	{
		//Declare all variables
		String daySuffix = d.getSuffix(), dateAsWritten; //Strings for the suffix and the date as a written version  
		
		
		//Decisions
		if (d.eraFormat) //If the era format is secular (true)
		{
			if (d.checkEra()) //If the year is positive and is in the Common Era (CE)
			{
				dateAsWritten = d.getMonthName() + " " + d.day + daySuffix + ", " + d.year + " CE."; //Set the date accordingly
			}
			
			else if (d.year == 0) //If the year is 0, which is impossible in the Gregorian calendar
			{
				dateAsWritten = YEAR_ZERO; //Set the string as the error message
			}
			
			else //Otherwise, if the year is negative and is Before the Common Era (BCE)
			{
				dateAsWritten = d.getMonthName() + " " + d.day + daySuffix + ", " + Math.abs(d.year) + " BCE."; //Set the date accordingly
			}
			
		}
		
		else //If the era format is non-secular (false)
		{
			if (d.checkEra()) //If the year is positive and it is Anno Domini (AD)
			{
				dateAsWritten = d.getMonthName() + " " + d.day + daySuffix + ", AD " + d.year + "."; //Set the date accordingly
				
			}
			
			else if (d.year == 0) //If the year is 0, which is impossible in the Gregorian calendar
			{
				dateAsWritten = YEAR_ZERO; //Set the string as the error message
			}
			
			else //If the year is negative and it is Before Christ (BC)
			{
				dateAsWritten = d.getMonthName() + " " + d.day + daySuffix + ", " + Math.abs(d.year) + " BC."; //Set the date accordingly
			}
		}
		
		return dateAsWritten; //Return the string, based on the era format, the era itself, and whether it is year 0
	}

	private String getSuffix() //Private method which gets the suffix based on the date from the writtenDate method
	{
		//Declare all variables
		String suffix = "";
		
		//Decisions
		if (!((this.day > 10) && (this.day < 20))) //If the day is NOT between 11 and 19
		{
			switch (this.day % 10) //Switch statement based on the remainder of the day, divided by 10, which gives the ones digit
			{
				//For example, 23 % 10 is 20 with a remainder of 3
					
				case 1: //If the ones digit is 1
					suffix = "st"; //Set the suffix to st
						break;
							
				case 2: //If the ones digit is 2
					suffix = "nd"; //Set the suffix to nd
					break;
						
				case 3: //If the ones digit is 3
						suffix = "rd"; //Set the suffix to rd
						break;
							
				default: //If the ones digit is anything else
						suffix = "th"; //Set the suffix to th for any other ones digit
						break;
			}
		}
				
		else //If the day IS between 11 and 19
		{
			suffix = "th"; //For all days, set the suffix to th. This is because we do not day 11st, 12nd, and 13rd; we say 11th, 12th, and 13th 
		}
		
		return suffix; //Return the suffix back to the method
	}
	
	private boolean checkEra() //Private method which checks whether the year is positive or negative
	{
		//Declare all variables
		boolean isCommonEra; //Boolean to show the state of the whether the date is in the common era (positive) or not 
		
		//Decisions
		if (this.year > 0) //If the year is greater than 0
		{
			isCommonEra = true; //The date IS in the common era, and the boolean is set to true
		}
		
		else //Otherwise, if the date is no greater than 0.
		{
			//If the year is zero, then this decision occurs, but the other method takes care of year zero so it does not produce any string
			isCommonEra = false; //The date is NOT in the common era, and the boolean is set to false
		}
		
		return isCommonEra; //Return the boolean for whether or not the date is in the common era
	}
	
	public void switchEraFormat(boolean input) //Public method which switches the era format. The user can decide the era format with the constants
	{
		this.eraFormat = input; //Set the era format to whatever the user inputs
	}
	
	public boolean getEraFormat() //Public method which gets the format. This is needed for the ImportantDatesClass for the button to switch
	{
		return this.eraFormat; //Return the era format back to the user
	}
}