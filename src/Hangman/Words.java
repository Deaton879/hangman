package Hangman;

import java.util.ArrayList;

public class Words extends Strings {

    protected String category;

    public Words(String category, String name) {
        super(name); this.setCategory(category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void setContent(String content) {
        super.setContent(content);
    }

    public String getCategory() { return this.category; }

    @Override
    public String getContent() { return super.getContent(); }

}

