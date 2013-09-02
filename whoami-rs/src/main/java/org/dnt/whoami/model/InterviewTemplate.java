package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
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
    private boolean active;
    private int order;

    @Embedded
    private List<Question> questions;

    public InterviewTemplate() {
    }

    public InterviewTemplate(String id) {
        setId(id);
    }

    public InterviewTemplate(ObjectId id) {
        this.id = id;
    }

    public InterviewTemplate(String name,
                             String description,
                             boolean active,
                             int order,
                             List<Question> questions) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.order = order;
        this.questions = questions;
    }

    @XmlTransient
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "InterviewTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", order=" + order +
                ", questions=" + questions +
                '}';
    }
}
