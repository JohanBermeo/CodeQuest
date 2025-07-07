package model.content;

import java.io.File;

/**
 * Contenido de tipo video
 */
public class VideoContent extends Content {
    private File videoFile;
    
    public VideoContent(File videoFile) {
        super("VIDEO");
        this.videoFile = videoFile;
    }
    
    @Override
    public boolean isValid() {
        return videoFile != null && videoFile.exists() && videoFile.length() > 0;
    }
    
    @Override
    public long getSize() {
        return videoFile != null ? videoFile.length() : 0;
    }
    @Override
    public File getContent() {
        return videoFile;
    }
    
    public void setVideoFile(File videoFile) {
        this.videoFile = videoFile;
    }
}