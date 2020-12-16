package Hangman;

/**
 * The Messages Class
 * Purpose: This is a derived class. It extends the Strings class.
 * @author Dallas Eaton
 * @author Nathaniel SNow
 */

public class Messages extends Strings {

    protected String error = "";

    /** Default values of the Messages class*/
    public Messages(String content) {
        super(content);
    }

    /** Constructs a default Messages object */
    public Messages(String content, String error) {
        super(content); setError(error);
    }

    /**
     * The getContent method
     * Purpose: Returns content. Overrides Strings class getContent method
     * @return string
     */
    @Override
    public String getContent() {
        return super.getContent();
    }

    /**
     * The getError method
     * Purpose: Returns error.
     * @return string
     */
    public String getError() {
        return this.error;
    }

    /**
     * The setContent method
     * Purpose: Sets content. Overrides Strings class setContent method
     * @param content
     */
    @Override
    public void setContent(String content) {
        super.setContent(content);
    }

    /**
     * The setError method
     * Purpose: Sets error.
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * The getInfo method
     * Purpose: Prints the objects variable values to the console
     */
    @Override
    public void getInfo() {
        System.out.println("Content: " + this.getContent() + "Error: " + this.getError());
    }
}
