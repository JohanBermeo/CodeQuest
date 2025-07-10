package com.codequest.model.content;

import com.codequest.model.interfaces.Publication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumPost implements Publication, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String content;
    private String author;
    private Date creationDate;
    private int likes;
    private List<String> attachments;
    private String category;

    public ForumPost(String id, String title, String content, String author, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
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
        likes++;
    }

    @Override
    public List<String> getAttachments() {
        return new ArrayList<>(attachments);
    }

    @Override
    public void addAttachment(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            attachments.add(filePath);
        }
    }

    @Override
    public String getType() {
        return "ForumPost";
    }

    public String getCategory() {
        return category;
    }
}