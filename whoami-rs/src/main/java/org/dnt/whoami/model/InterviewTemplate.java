package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 2:08 PM
 */
@XmlRootElement
@Entity(noClassnameStored=true)
public class InterviewTemplate {

    @Id
    private ObjectId id;

    public InterviewTemplate() {
    }

    public ObjectId getObjectId() {
        return id;
    }

    public String getId() {
        return id != null ? id.toString() : null;
    }

    public void setId(String id) {
        if(ObjectId.isValid(id)) {
            this.id = new ObjectId(id);
        }
    }

}
