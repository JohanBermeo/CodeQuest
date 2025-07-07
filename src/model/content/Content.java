package model.content;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase abstracta para el contenido de las publicaciones
 */
public abstract class Content implements Serializable { 
    private static final long serialVersionUID = 1L; 

    protected final String type;
    protected final Date dateCreated;
    
    protected Content(String type) {
        this.type = type;
        this.dateCreated = new Date();
    }
    
    public String getType() { 
        return type; 
    }
    
    public Date getDateCreated() { 
        return new Date(dateCreated.getTime()); 
    }

    public abstract Object getContent();
    
    public abstract boolean isValid();
    public abstract long getSize();
}