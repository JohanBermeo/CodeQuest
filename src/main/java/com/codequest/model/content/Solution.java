package model.content;

import model.interfaces.Publication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solution implements Publication, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String challengeId;
    private String title;
    private String content;
    private String author;
    private Date creationDate;
    private int likes;
    private List<String> attachments;

    public Solution(String id, String challengeId, String title, String content, String author) {
        this.id = id;
        this.challengeId = challengeId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationDate = new Date();
        this.likes = 0;
        this.attachments = new ArrayList<>();
    }

    @Override
    public int getId() {
        // Si el id es un String UUID, puedes usar hashCode o parsear a int si es numérico
        // Aquí usamos hashCode, pero puedes cambiarlo si tienes otro criterio
        return id.hashCode();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public Date getCreationDate() {
        return new Date(creationDate.getTime());
    }

    @Override
    public int getLikes() {
        return likes;
    }

    @Override
    public void addLike() {
        this.likes++;
    }

    @Override
    public List<String> getAttachments() {
        return new ArrayList<>(attachments);
    }

    @Override
    public void addAttachment(String filePath) {
        if (filePath != null && !filePath.trim().isEmpty()) {
            this.attachments.add(filePath);
        }
    }

    @Override
    public String getType() {
        return "Solution";
    }

    public String getChallengeId() {
        return challengeId;
    }
}