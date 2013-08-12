package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 2:04 PM
 */
@XmlRootElement
@Entity(noClassnameStored=true)
public class Question {

    @Id
    private ObjectId id;

    private String text;
    private PersonalityTrait trait;

    public Question() {
    }

    public Question(String text, PersonalityTrait trait) {
        this.text = text;
        this.trait = trait;
    }

    public ObjectId getObjectId() {
        return id;
    }

    public void setObjectId(ObjectId id) {
        this.id = id;
    }

    public String getId() {
        return id != null ? id.toString() : null;
    }

    public void setId(String id) {
        if(ObjectId.isValid(id)) {
            this.id = new ObjectId(id);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PersonalityTrait getTrait() {
        return trait;
    }

    public void setTrait(PersonalityTrait trait) {
        this.trait = trait;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", trait=" + trait +
                '}';
    }
}
