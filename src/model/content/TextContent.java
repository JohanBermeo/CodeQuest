package model.content;

/**
 * Contenido de tipo texto
 */
public class TextContent extends Content {
    private String text;
    
    public TextContent(String text) {
        super("TEXT");
        this.text = text;
    }
    
    @Override
    public boolean isValid() {
        return text != null && !text.trim().isEmpty() && text.length() <= 500;
    }
    
    @Override
    public long getSize() {
        return text != null ? text.getBytes().length : 0;
    }

    @Override
    public String getContent() { 
        return text; 
    }
    
    public void setText(String text) {
        this.text = text;
    }
}