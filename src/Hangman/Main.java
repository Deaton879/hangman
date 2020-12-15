package Hangman;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;


public class Main {

    /* ================================== PROGRAM CONSTANTS ================================== */

    // File to track progress of current game
    private static final File GUESS = new File("guesses.txt");

    // String Constants
    private static final String CATEGORY1 = "animals";
    private static final String CATEGORY2 = "cities";
    private static final String CATEGORY3 = "instruments";
    private static final String INITIAL = "";
    private static final String BLANK = "_";
    private static final String YES = "[yY]";
    private static final String ALPHABET = "[a-zA-Z]";

    // int Constants
    private static final int FLOOR = 0;
    private static final int MIN = 1;
    private static final int MID = 3;
    private static final int MAX = 4;
    private static final int GUESS_MAX = 6;
    private static final int WORD_MAX = 20;

    // Messages constants
    private static final Messages GREET = new Messages("Welcome to hangman. You have wrong 6 guesses before " +
            "you're hung. Make them count!\n");
    private static final Messages SELECT = new Messages("Select a word category.\n " +
            "1 = " + CATEGORY1 + ", 2 = " + CATEGORY2 + ", 3 = " + CATEGORY3 + ", and 4 = Random: ", "Must be a number between 1 and 4: ");
    private static final Messages CORRECT = new Messages("\nNice guess!");
    private static final Messages INCORRECT = new Messages("\nAww, too bad.");
    private static final Messages LOSE = new Messages("\nOn no! You are dead!");
    private static final Messages WIN = new Messages("\nYay! You guessed the word and you are alive!");
    private static final Messages REPEAT = new Messages("Care to play again? (Y/N): ");
    private static final Messages GOODBYE = new Messages("\nWe'll see you next time..");


    /* ================================== PROGRAM VARIABLES ================================== */

    // String Variables (will update procedurally)
    private static String category;
    private static String currentGuess;
    private static String wordProgress = INITIAL;

    // int Variables (will update procedurally)
    private static int attempt;
    private static int index;
    private static int choice;
    private static int lettersCorrect = FLOOR;

    // Messages variables (will update procedurally)
    private static Messages startRound = new Messages("Start playing first..");

    // For user input
    private static Scanner input = new Scanner(System.in);

    // Array of Word objects from which a word will be chosen at random for the user to guess
    private static ArrayList<Strings> words = new ArrayList<>(WORD_MAX);
    private static String[] letters;
    private static String[] blanks;

    // Boolean to check if game has been won
    private static Boolean youWon = false;

    //color strings
    public static final String ANSI_RED = "\u001B[31m";
    public static final String TEXT_RESET = "\u001B[0m";

    /**
     * The main Method
     * Purpose: Set up and play game(s) of Hangman
     * Procedure 1)
     *           2)
     *           3)
     * @param args
     */
    public static void main(String[] args) {

        /* ================================== SET UP THE GAME ================================== */

        // Greet the user and start the game
        System.out.println(GREET.getContent());

        setupGame();

        /* ================================== GAME BEGINS NOW!! ================================== */

        while(attempt >= FLOOR && !youWon) {
            runGame();
        }
        resolveGame();


    }



    /**
     * The setupGame Method
     * Purpose: Set up various parts of the game so the player can play a new game of hangman
     * Procedure: 1)
     *            2)
     *            3)
     *            4)
     *            5)
     */
    private static void setupGame() {
        // Reset attempts if this is not the first game
        attempt = GUESS_MAX;
        choice = FLOOR;

        // Prompt the user for a particular category and save their answer
        System.out.print(SELECT.getContent());
        // Make sure the user's answer is an integer between 1 and 4.
        do {
            try {
                choice = input.nextInt();
                if (choice < MIN || choice > MAX) {
                    System.out.print(SELECT.getError());
                    input.next();
                }
            } catch (InputMismatchException e) {
                // Make sure the answer is type int
                System.out.println(SELECT.getError());
                input.next();
            }

            // Make sure that int is in the proper range


            // Continue asking while the answer is out of bounds
        } while(choice < MIN || choice > MAX);

        // String representing a filename to access the chosen word bank txt file.
        String filename = INITIAL;

        // Take the number and get the correct file name
        switch (choice) {
            case 1:  filename = CATEGORY1 + ".txt"; category = CATEGORY1; break;
            case 2:  filename = CATEGORY2 + ".txt"; category = CATEGORY2; break;
            case 3:  filename = CATEGORY3 + ".txt"; category = CATEGORY3; break;
            // Case 4 randomly picks a category for the user for a little more challenge
            case 4:
                // cut out the random option and randomly pick a category for the player
                int x = ThreadLocalRandom.current().nextInt(MIN,MID);
                switch (x) {
                    case 1:  filename = CATEGORY1 + ".txt"; category = CATEGORY1; break;
                    case 2:  filename = CATEGORY2 + ".txt"; category = CATEGORY2; break;
                    case 3:  filename = CATEGORY3 + ".txt"; category = CATEGORY3; break;
                } break;
        }


        // Create a file reference
        File wordBank = new File(filename);

        try {
            // Open the file associated with the chosen word category using that reference
            Scanner readFile = new Scanner(wordBank);

            do {
                // Read each line of the opened file (1 word per line)
                String line = readFile.nextLine().trim();
                // Create a word object using the string on the current line
                Words word = new Words(category, line);
                // Add it to the words array
                words.add(word);

                // Do this while there are lines left to read in the file.
            } while(readFile.hasNextLine());

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Choose a word from the words array by generating a random index number
        int randomIndex = ThreadLocalRandom.current().nextInt(FLOOR,words.size() - MIN);

        // Save a reference to the content of that word object (the word itself) to a new string
        Strings selectedWord = words.get(randomIndex);

        // Separate the letters of the word, and make an array to store those letters in order.
        letters = selectedWord.getContent().split("");

        // Create an array to mirror letters, fill it with blanks equal to the number of letters in the letters array
        blanks = new String[letters.length];

        // initiate wordProgress (or reset if playing another game)
        wordProgress = INITIAL;
        // Populate blanks array with "blank spaces" and initialize wordProgress with the right amount of "blank" spaces
        for(int x = FLOOR; x < letters.length; x++) {
            blanks[x] = BLANK;
            wordProgress += BLANK;
        }

        // Write the blank word to a file to keep track
        fileWrite();
    }


    /**
     * The runGame Method
     * Purpose: Run the hangman game loop
     * Procedure: 1) Check each index in blanks against each index in letters. If all match, youWon will be true
     *            2) If player has won, congratulate and ask if the player want to play again, if so, setup/start a new game,
     *               otherwise, end program.
     *            3) Begin round by resetting/updating variables
     *            4) Display round message & prompt the player for a letter by calling the getGuess method
     *            5) Check to see if the guess matches any letters in the word and update the blanks array if necessary
     *            6) Write the current wordProgress to the guesses.txt file
     *            7) Increase the attempt counter and restart loop; if all attempts completed, exit loop
     */
    private static void runGame() {
        for(int x = FLOOR; x < letters.length; x++) {
            if(blanks[x].equalsIgnoreCase(letters[x])) {
                youWon = true;
            }
            else {
                youWon = false;
                break;
            }
        }
        if(youWon) {
            return;
        }

        // Reset index & lettersCorrect back to 0
        index = FLOOR;
        lettersCorrect = FLOOR;
        // Change the round message
        changeRoundMessage();

        // Display the updated message to start the round
        System.out.println(startRound.getContent());
        // Get the player's guess
        getGuess();

        // Reset wordProgress
        wordProgress = INITIAL;

        // Test the player's guess against every letter in the word by iterating through the letters array
        for(String letter : letters) {
            if(Pattern.compile(ALPHABET).matcher(blanks[index]).matches()) {
                wordProgress += blanks[index];
            }
            else if(letter.equalsIgnoreCase(currentGuess)) {
                // If a match, use index to change the matching index in the blanks array from a "_" to the letter
                blanks[index] = currentGuess;
                wordProgress += blanks[index];
                lettersCorrect ++;
            }
            else {
                wordProgress += BLANK;
            }
            // Increment index to keep letters and blanks synchronised
            index ++;
        }
        fileWrite();
        // After the round is over, increment the attempt counter if guess was incorrect and notify
        if (lettersCorrect == 0) {
            attempt--;
            System.out.println(INCORRECT.getContent());
        }
        // If correct, congratulate
        else {
            System.out.println(CORRECT.getContent());
        }

    }


    /**
     * The nextRound Method
     * Purpose: Update the information displayed at the beginning of each round
     */
    private static void changeRoundMessage() {
        System.out.println("\n");
        fileRead();
        startRound.setContent("Current category: " + category + "\nAttempts remaining: " + attempt +
                "\n\nChoose a letter: ");
        gallows(attempt, TEXT_RESET);
    }


    /**
     * The getGuess Method
     * Purpose: Get a single letter from the user
     * Procedure: 1) Collect user input
     *            2) Make sure it is a single letter (either upper or lowercase)
     *            3) Set Global String variable currentGuess
     */
    private static void getGuess() {
        String guess = input.next();
        if (!Pattern.compile("[a-zA-Z]").matcher(guess).matches()) {
            System.out.print("You must enter a single letter (a-z or A-Z): ");
            getGuess();
        }
        else { currentGuess = guess; }
    }


    /**
     * the fileWrite Method
     *
     */
    private static void fileWrite() {
        // Create a txt file to update the blank word with correctly guessed letters.
        try(PrintWriter gameFile = new PrintWriter(GUESS);) {
            gameFile.print(wordProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The fileRead Method
     *
     */
    private static void fileRead() {
        try {
            Scanner checkGuesses = new Scanner(GUESS);
            String line = checkGuesses.nextLine();
            System.out.println(line+"\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The resolveGame Method
     * Purpose: Handle what happens when the game is over, win or lose
     * Procedure: 1) Check if the player has won
     *            2) Display appropriate message
     *            3) Ask if the player wants to play again
     *            4) if yes, restart; if no, exit program
     */
    private static void resolveGame() {
        if(youWon) {
            // Print WIN message
            System.out.println(WIN.getContent());
        }// Otherwise print losing message
        else {
            System.out.println(LOSE.getContent());
        }

        // Ask if the player wants to play again
        System.out.println(REPEAT.getContent());
        // Save the players answer
        String answer = input.next();
        // If answer is YES, set up a new game and run it.
        if (Pattern.compile(YES).matcher(answer).matches()) {
            setupGame();
            while(attempt >= FLOOR) {
                runGame();
            }
            resolveGame();
        }
        else {
            System.out.println(GOODBYE.getContent());
            gallows(0, ANSI_RED);
        }
    }

    /**
     * The gallows method
     * Purpose: Generates and prints a string picture of a gallows. Prints a different picture depending on the gusss num
     * @param guesses
     * @param color
     */
    private static void gallows(int guesses, String color){
        String gallows = "";

        if (guesses == 6){
            gallows = "============\n" +
                      " |         |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "  ==========";
        }
        else if (guesses == 5){
            gallows = "============\n" +
                      " |         |\n" +
                      " 0         |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "  ==========";
        }
        else if (guesses == 4){
            gallows = " ============\n" +
                      " |          |\n" +
                      " 0          |\n" +
                      " |          |\n" +
                      "            |\n" +
                      "            |\n" +
                      "            |\n" +
                      "   ==========";
        }
        else if (guesses == 3){
            gallows = "============\n" +
                      " |         |\n" +
                      " 0         |\n" +
                      "/|         |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "  ==========";
        }
        else if (guesses == 2){
            gallows = "============\n" +
                      " |         |\n" +
                      " 0         |\n" +
                      "/|\\        |\n" +
                      "           |\n" +
                      "           |\n" +
                      "           |\n" +
                      "  ==========";
        }
        else if (guesses == 1){
            gallows = "============\n" +
                      " |         |\n" +
                      " 0         |\n" +
                      "/|\\        |\n" + "" +
                      "/          |\n" +
                      "           |\n" +
                      "           |\n" +
                      "  ==========";
        }
        else if (guesses == 0){
            gallows = "============\n" +
                      " |         |\n" +
                      " 0         |\n" +
                      "/|\\        |\n" +
                      "/ \\        |\n" +
                      "           |\n" +
                      "           |\n" +
                      "  ==========";
        }

        System.out.println(color + gallows);
    }

}
