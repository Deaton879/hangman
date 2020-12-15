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

}
