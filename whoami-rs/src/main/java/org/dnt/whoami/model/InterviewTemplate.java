package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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

    private String name;
    private String description;

    // manual reference to the questions
    private List<ObjectId> questions;

    public InterviewTemplate() {
    }

    public InterviewTemplate(String name, String description, List<ObjectId> questions) {
        this.name = name;
        this.description = description;
        this.questions = questions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ObjectId> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ObjectId> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "InterviewTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", questions=" + questions +
                '}';
    }
}
