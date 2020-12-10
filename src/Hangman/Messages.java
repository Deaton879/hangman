package Hangman;

public class Messages extends Strings {

    protected String error = "";

    public Messages(String content) {
        super(content);
    }

    public Messages(String content, String error) {
        super(content); setError(error);
    }


    @Override
    public String getContent() {
        return super.getContent();
    }

    public String getError() {
        return this.error;
    }

    @Override
    public void setContent(String content) {
        super.setContent(content);
    }

    public void setError(String error) {
        this.error = error;
    }


}