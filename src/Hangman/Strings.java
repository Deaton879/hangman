package Hangman;

/**
 * The Strings Class
 * Purpose: This is a base class.
 * @author Dallas Eaton
 * @author Nathaniel SNow
 */

public class Strings {

    protected String content;

    /** Default values of the Strings class*/
    public Strings() {
    }

    /** Constructs a default Strings object */
    public Strings(String content) {
        this.setContent(content);
    }

    /** Return content */
    public String getContent() { return this.content; }

    /** Set new content */
    public void setContent(String content) { this.content = content; }

    /**
     * The getInfo method
     * Purpose: Prints the objects variable values to the console
     */
    public void getInfo() {
        System.out.println("Content: " + this.getContent());
    }

}
