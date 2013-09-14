package org.dnt.whoami.model;

import com.google.code.morphia.annotations.*;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * The Interview class. Holds answers for interview questions
 *
 * @author dima
 * @since  5/25/13 2:04 PM
 */
@XmlRootElement
@Entity(noClassnameStored=true)
@Indexes(@Index(value = "userId, templateId", unique=true))
public class Interview {

    @Id
    private ObjectId id;

    private String userId; // reference to the user
    private String templateId; // reference to the template

    private String name;
    private String description;

    @Embedded
    private List<Answer> answers;

    private Date startDate;
    private Date endDate;

    public Interview() {
    }

    public Interview(String id) {
        setId(id);
    }

    public Interview(ObjectId id) {
        this.id = id;
    }

    public Interview(String userId,
                     String templateId,
                     String name,
                     String description,
                     List<Answer> answers,
                     Date startDate, Date endDate) {
        this.userId = userId;
        this.templateId = templateId;
        this.name = name;
        this.description = description;
        this.answers = answers;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", templateId='" + templateId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", answers=" + answers +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
