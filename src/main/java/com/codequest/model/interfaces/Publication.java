package com.codequest.model.interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Publication extends Identifiable, Serializable {
    String getTitle();
    String getContent();
    String getAuthor();
    Date getCreationDate();
    int getLikes();
    void addLike();
    List<String> getAttachments();
    void addAttachment(String filePath);
    String getType();
}