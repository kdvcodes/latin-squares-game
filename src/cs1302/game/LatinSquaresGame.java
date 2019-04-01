package cs1302.game;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class represents a LatinSquaresGame object. Each {@code LatinSquaresGame}
 * has a {@code latinSquare} 2D array that stores the square and its entries, a {@code size}
 * value, and a {@code characters} array that stores the game's allowed characters. 
 * In addition, each object has a {@code k} value, as well as {@code kRow} and {@code kCol} arrays,
 * which represents the number of predetermined characters and their respective rows and columns. 
 * Each {@code LatinSquaresGame} also has a {@code p} value, which is determined by the square's
 * size and is important for spacing when displaying the square.
 */
public class LatinSquaresGame {
    private String[][] latinSquare;
    private int size, k, p;
    private String[] characters;
    private int[] kRow, kCol;
    private Scanner input = new Scanner(System.in);
    
    /**
     * Constructs a {@code LatinSquaresGame} object given the path to a config file. 
     * Sets square size, characters, p, and predetermined locations using
     * {@link #initializeVariables(String)}.
     * @param config the pathway to the config file.
     * @exception FileNotFoundException if {@code config} is not a pathway to a file.
     */
    public LatinSquaresGame(String config) {
	try {
	    String newConfig = "";
	    File configFile = new File(config);
	    Scanner configScanner = new Scanner(configFile);
	    //Converts any format of a config file into one line that a different scanner reads
	    while(configScanner.hasNextLine()) {
		newConfig = newConfig + " " + configScanner.nextLine();
	    } //while
	    newConfig = newConfig.trim();
	    initializeVariables(newConfig);
	} catch (FileNotFoundException e) {
	    //handles exception if file isnt found
	    System.err.println(e);
	    e.printStackTrace();
	    System.exit(0);
	} //try
    } //LatinSquaresGame

    /**
     * Initializes variables for {@code size}, {@code characters}, {@code k}, {@code p},
     * and predetermined coordinates ({@code kRow} and {@code kCol}).
     * @param newConfig a one line version of the config file that is generated beforehand
     * in {@link #LatinSquaresGame(String)}.
     */
    public void initializeVariables(String newConfig) {
	Scanner configLineScanner = new Scanner(newConfig); 
	//Sets size of latin square array
	size = configLineScanner.nextInt();
	latinSquare = new String[size][size]; 
	
	//Creates array of characters that will be in the game
	characters = new String[size];
	for (int n = 0; n < size; n++) {
	    characters[n] = configLineScanner.next();
	} //for

	//Stores number and coordinates of predetermined locations, then adds them to square
	k = configLineScanner.nextInt();
	kRow = new int[k];
	kCol = new int[k];
	String s;
	for (int n = 0; n < k; n++) {  
	    kRow[n] = configLineScanner.nextInt();
	    kCol[n] = configLineScanner.nextInt();
	    s = configLineScanner.next();
	    latinSquare[kRow[n]][kCol[n]] = s;
	} //for
	
	calculateP();
    } //initalizeVariables
    
    /** Prints Welcome Banner with starting config information. */
    public void printWelcome() {
	String welcome =
	    "  _           _   _        _____ \n" +
	    " | |         | | (_)      / ____|\n" +
	    " | |     __ _| |_ _ _ __ | (___   __ _ _   _  __ _ _ __ ___  ___\n" +
	    " | |    / _` | __| | \'_ \\ \\___ \\ / _` | | | |/ _` | \'__/ _ \\/ __|\n" +
	    " | |___| (_| | |_| | | | |____) | (_| | |_| | (_| | | |  __/\\__ \\ \n" +
	    " |______\\__,_|\\__|_|_| |_|_____/ \\__, |\\__,_|\\__,_|_|  \\___||___/\n" + 
	    "                          CSCI 1302 | | v2019.sp\n" +
	    "                                    |_|";
	System.out.println(welcome);
	
	//prints square size and characters 
	System.out.print("n = " + size + " {");
	for (int x = 0; x < size - 1; x++) {
	    System.out.print(" " + characters[x] + ",");
	} //for
	//Prints number of predetermined locations
	System.out.println(" " + characters[size - 1] + " }");
	System.out.println("k = " + k);
    } //printWelcome

    /**
     * Prints the formatted square to the screen, showing row and column numbers,
     * as well as predetermined spots and user filled spots.
     */
    public void displaySquare() {
	System.out.println();
	printTopRow(); //Prints the top row with the column numbers
	for (int i = 0; i < size; i++) {
	    printRowNumber(i); //Prints the row numbers on the side
	    for (int j = 0; j < size; j++) {
		//Fills in square spaces, if possible
		if (latinSquare[i][j] != null) {
		    for (int n = 0; n < k; n++) { 
			//Identifies and fills in predetemined spots with brackets
			if (i == kRow[n] && j == kCol[n]) {
			    String s = "[" + latinSquare[i][j] + "]";
			    System.out.print(spaceCell(s) + "|");
			    break;
		        //Fills in user-inputted spots
			} else if (n == k - 1) {
			    System.out.print(spaceCell(latinSquare[i][j]) + "|");
			} //if
		    } //for
		} else {
		    System.out.print(spaceCell(" ") + "|"); //Fills in empty cells
		} //if
	    } //for
	    
	    System.out.println();
	} //for
	System.out.println();
    } //displaySquare

    /**
     * Calculates the value of {@code p}, a variable that is used to determine spacing 
     * when displaying the square.
     * @return the value of {@code p}.
     */
    public void calculateP() {
	p = 1;
	//10^p should be >= size
	while(Math.pow(10, p) < size) {
	    p++;
	} //while
    } //calculateP

    /** 
     * Prints the row number with appropriate spacing according to {@code p}. 
     * @param row the row number that will be printed.
     */
    public void printRowNumber(int row) {
	String s = "";
	String sRow = String.valueOf(row);
	int spaces = p - sRow.length();
	for (int i = 0; i < spaces; i++) {
	    s += " ";
	} //for
	s += sRow + " |";
	System.out.print(s);
    } //printRowNumber

    /** Prints the top row with column numbers with appropriate spacing according to {@code p}. */
    public void printTopRow() {
	String s = "", sCol;
	int spaces;
	for (int n = 0; n < (p+2); n++) {
	    s += " ";
	} //for
      	for (int col = 0; col < size; col++) {
	    s += spaceCell(String.valueOf(col)) + " ";
      	} //for
	System.out.println(s);
    } //printTopRow	    

    /**
     * Returns a {@code String} version of the {@code value} with appropriate spacing 
     * according to {@code p}. 
     * @return a {@code String} with appropriate spacing for displaying on screen.
     * @param value the value of the cell as a {@code String}. If a cell is predetermined, 
     * include brackets. If a cell is empty, {@code value} should be " ".
     */
    public String spaceCell(String value) {
	String s;
	int spaces = p + 2;
	
	//Checks to see if value is bracketed or not
	if (value.charAt(0) == '[') {
	    s = value;
	} else {
	    s = " " + value + " ";
	} //if

	//Adds the appropriate number of spaces to the end
	int remainingSpaces = spaces - s.length();
	for (int x = 0; x < remainingSpaces; x++) {
		 s += " ";
	} //for

	return s;
    } //spaceCell

    /** 
     * Checks to see if the square is a latin square (has no duplicates in any row or column) 
     * @return true if the square is a latin square, false otherwise.
     */
    public boolean isLatinSquare() {
	boolean isLatinSquare = true;
	//Checks to see if entire square is filled
	for(int i = 0; i < latinSquare.length; i++) {
	    for (int j = 0; j < latinSquare[i].length; j++) {
		if (latinSquare[i][j] == null) {
		    return false; //Skips rest of method if square isn't complete
		} //if
	    } //for
	} //for
	//Checks each row for duplicates
	for(int i = 0; i < size; i++) {
	    for (int col = 0; col < size; col++) {
		String s = latinSquare[i][col];
		for (int x = 0; x < size; x++) {
		    if (s.equals(latinSquare[i][x]) && (col!=x)) {
			isLatinSquare = false;
		    } //if
		} //for
	    } //for
	    //Checks each column for duplicates
	    for (int row  = 0; row < size; row++) {
		String s = latinSquare[row][i];
		for (int x = 0; x < size; x++) {
		    if (s.equals(latinSquare[x][i]) && (row!=x)) {
			isLatinSquare = false;
	 	    } //if
		} //for
	    } //for
	} //for
        return isLatinSquare;
    } //isLatinSquare

    /** Prints Win Message. */
    public void printWin() {
	String win =
	    "                                 .\'\'.\n" +
	    "       .\'\'.             *\'\'*    :_\\/_:     . \n" +
	    "      :_\\/_:   .    .:.*_\\/_*   : /\\ :  .\'.:.\'.\n" +
	    "  .\'\'.: /\\ : _\\(/_  \':\'* /\\ *  : \'..\'.  -=:o:=-\n" +
	    " :_\\/_:\'.:::. /)\\*\'\'*  .|.* \'.\\\'/.\'_\\(/_\'.\':\'.\'\n" +
	    " : /\\ : :::::  \'*_\\/_* | |  -= o =- /)\\    \'  *\n" +
	    "  \'..\'  \':::\'   * /\\ * |\'|  .\'/.\\\'.  \'._____\n" +
	    "      *        __*..* |  |     :      |.   |\' .---\"| \n" +
	    "       _*   .-\'   \'-. |  |     .--\'|  ||   | _|    |\n" +
	    "    .-\'|  _.|  |    ||   \'-__  |   |  |    ||      | \n" +
	    "    |\' | |.    |    ||       | |   |  |    ||      |\n" +
	    " ___|  \'-\'     \'    \"\"       \'-\'   \'-.\'    \'`      |____\n" +
	    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
	    "    CONGRATULATIONS! YOU COMPLETED THE LATIN SQUARE!";
	System.out.println(win);
    } //printWin

    /** 
     * Prompts user to enter commands via standard input.
     * "q" quits the game, the other valid command is {@code row col char}, where 
     * {@code row} and {@code col} are type int and {@code char} is a single character
     * which fills in the specified coordinate on the square with the chosen character, 
     * if possible, by calling {@link isValidInput(int, int, String)}.
     * @exception NumberFormatException if {@code row} and {@code col} aren't ints
     * @exception StringIndexOutOfBoundsException if {@code cmd} is missing tokens
     */
    public void promptUser() 
    {
	boolean validInput = false;
      	String cmd;
	do {
	    System.out.print("latin-squares: ");
	    cmd = input.nextLine();
	    if(cmd.equals("q")) {
		System.out.println();
		System.exit(0); //quits game if user enters "q"
	    } else {
		try {
		    int spaceIndex = cmd.indexOf(" ");
		    int row = Integer.parseInt(cmd.substring(0, spaceIndex));
		    cmd = cmd.substring(spaceIndex + 1);
		    spaceIndex = cmd.indexOf(" ");
		    int col = Integer.parseInt(cmd.substring(0, spaceIndex));
		    cmd = cmd.substring(spaceIndex + 1); //cmd should be one letter now
		    validInput = isValidInput(row, col, cmd);
		   
	        //Transfers control to right before the generic error message
		} catch (NumberFormatException e) {
	       	} catch (StringIndexOutOfBoundsException e) {
              	} //try
	    } //if
	    if (validInput == false) {
		System.out.println("error: invalid input!");
	    } //if
	} while(validInput == false);
	System.out.println();
    } //promptUser

    /**
     * Determines if the specified character can be placed at the specified 
     * row and column of the square, and does so if it can.
     * @return false if {@code row} or {@code col} is out of bounds or refers to a coordinate that
     * has a predetermined character, as well as if {@code cmd} is too long or  doesn't 
     * match any of the chosen game characters
     * @param row the row that needs to be checked
     * @param col the column that needs to be checked
     * @param ch the character that needs to be checked
     */
    public boolean isValidInput (int row, int col, String ch) {
	boolean validInput = false;
	for (String c : characters) {
	    //false if row or col are out of bounds or if cmd isn't one of the game characters
	    if (c.equals(ch) && row < size && col < size) { //will be false if too many tokens
		validInput = true;
		for (int i = 0; i < k; i++) {
		    //true if row and col match a predetermined spot
		    if (row == kRow[i] && col == kCol[i]) {
			validInput = false;
		    } //if
		} //for
		if (validInput) {
		     latinSquare[row][col] = ch;
		 }
	    } //if
	} //for
	return validInput;
    } 
    
    /**
     * Starts the game loop. Displays the welcome banner, then loops through displaying 
     * the square and prompting the user until the latin square is complete. When the user
     * completes the latin square, the win message is printed and the game ends.
     */
    public void play() 
    {
	boolean gameOver = false;
	printWelcome();
	while (gameOver == false) {
	    displaySquare();
	    promptUser();
	    if (isLatinSquare() == true) { //end game if square is a latin square
		displaySquare();
		printWin();
		gameOver = true;
	    } //if
	} //while
    } //play
}

