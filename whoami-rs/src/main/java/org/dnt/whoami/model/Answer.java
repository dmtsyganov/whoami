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
    private Question.Type type;
    private Question.ValueType valueType;
    private String value;

    public Answer() {
    }

    public Answer(ObjectId question, PersonalityTrait trait,
                  Question.Type type ,Question.ValueType valueType) {
        this.question = question;
        this.trait = trait;
        this.type = type;
        this.valueType = valueType;
    }

    public Answer(ObjectId question, PersonalityTrait trait,
                  Question.Type type, Question.ValueType valueType, String value) {
        this.question = question;
        this.trait = trait;
        this.type = type;
        this.valueType = valueType;
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

    public Question.Type getType() {
        return type;
    }

    public void setType(Question.Type type) {
        this.type = type;
    }

    public Question.ValueType getValueType() {
        return valueType;
    }

    public void setValueType(Question.ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question=" + question +
                ", trait=" + trait +
                ", type=" + type +
                ", valueType=" + valueType +
                ", value='" + value + '\'' +
                '}';
    }
}
