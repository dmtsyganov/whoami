package org.dnt.whoami.model;

import com.google.code.morphia.annotations.*;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private ObjectId userId; // reference to the user
    private ObjectId templateId; // reference to the template

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

    public Interview(ObjectId userId,
                     ObjectId templateId,
                     List<Answer> answers,
                     Date startDate, Date endDate) {
        this.userId = userId;
        this.templateId = templateId;
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

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getTemplateId() {
        return templateId;
    }

    public void setTemplateId(ObjectId templateId) {
        this.templateId = templateId;
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
                ", userId=" + userId +
                ", templateId=" + templateId +
                ", answers=" + answers +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
