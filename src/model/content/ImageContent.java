package model.content;

import java.io.File;

/**
 * Contenido de tipo imagen
 */
public class ImageContent extends Content {
    private File imageFile;
    
    public ImageContent(File imageFile) {
        super("IMAGE");
        this.imageFile = imageFile;
    }
    
    @Override
    public boolean isValid() {
        return imageFile != null && imageFile.exists() && imageFile.length() > 0;
    }
    
    @Override
    public long getSize() {
        return imageFile != null ? imageFile.length() : 0;
    }
    
    @Override
    public File getContent() {
        return imageFile;
    }
    
    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}