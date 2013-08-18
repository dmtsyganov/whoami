package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 10:34 AM
 */
@XmlRootElement
@Embedded
public class Answer {

    private ObjectId question;
    private String value;

    public Answer() {
    }

    public Answer(ObjectId question, String value) {
        this.question = question;
        this.value = value;
    }

    public ObjectId getQuestion() {
        return question;
    }

    public void setQuestion(ObjectId question) {
        this.question = question;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question=" + question +
                ", value='" + value + '\'' +
                '}';
    }
}
