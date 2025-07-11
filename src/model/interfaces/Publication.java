package  model.interfaces;

import java.io.Serializable;
import java.util.Date;

public interface Publication extends Identifiable, Serializable {
    String getTitle();
    String getContent();
    String getAuthor();
    Date getCreationDate();
}