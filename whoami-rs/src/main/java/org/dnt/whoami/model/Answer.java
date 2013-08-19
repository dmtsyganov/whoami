package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Embedded;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The answer class, holds response to the question. Question is referenced by id.
 *
 * @author dima
 * @since 8/11/13 10:34 AM
 */
@XmlRootElement
@Embedded
public class Answer {

    private ObjectId question;
    private PersonalityTrait trait;
    private Question.QuestionType type;
    private String value;

    public Answer() {
    }

    public Answer(ObjectId question, PersonalityTrait trait, Question.QuestionType type) {
        this.question = question;
        this.trait = trait;
        this.type = type;
    }

    public Answer(ObjectId question, PersonalityTrait trait, Question.QuestionType type, String value) {
        this.question = question;
        this.trait = trait;
        this.type = type;
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

    public PersonalityTrait getTrait() {
        return trait;
    }

    public void setTrait(PersonalityTrait trait) {
        this.trait = trait;
    }

    public Question.QuestionType getType() {
        return type;
    }

    public void setType(Question.QuestionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question=" + question +
                ", value='" + value + '\'' +
                '}';
    }
}
