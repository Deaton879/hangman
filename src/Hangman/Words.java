package Hangman;

import java.util.ArrayList;

/**
 * The Words Class
 * Purpose: This is a derived class. It extends the Strings class.
 * @author Dallas Eaton
 * @author Nathaniel SNow
 */
public class Words extends Strings {

    protected String category;

    /**
     * The Words class constructor
     * Purpose: Constructs a default Words object.
     * @param category
     * @param name
     */
    public Words(String category, String name) {
        super(name); this.setCategory(category);
    }

    /**
     * The setCategory method
     * Purpose: Sets category.
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
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
     * The getCategory method
     * Purpose: Returns category.
     * @return string
     */
    public String getCategory() { return this.category; }

    /**
     * The getContent method
     * Purpose: Returns content. Overrides Strings class getContent method
     * @return string
     */
    @Override
    public String getContent() { return super.getContent(); }

}

