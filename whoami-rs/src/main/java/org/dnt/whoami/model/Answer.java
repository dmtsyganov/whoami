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

    private String question;
    private PersonalityTrait trait;
    private Question.Type type;
    private Question.ValueType valueType;
    private int valueEffect; // positive/negative
    private String value;

    public Answer() {
    }

    public Answer(String question,
                  PersonalityTrait trait,
                  Question.Type type,
                  Question.ValueType valueType,
                  int valueEffect) {
        this.question = question;
        this.trait = trait;
        this.type = type;
        this.valueType = valueType;
        this.valueEffect = valueEffect;
    }

    public Answer(String question,
                  PersonalityTrait trait,
                  Question.Type type,
                  Question.ValueType valueType,
                  int valueEffect,
                  String value) {
        this.question = question;
        this.trait = trait;
        this.type = type;
        this.valueType = valueType;
        this.valueEffect = valueEffect;
        this.value = value;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getValueEffect() {
        return valueEffect;
    }

    public void setValueEffect(int valueEffect) {
        this.valueEffect = valueEffect;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question='" + question + '\'' +
                ", trait=" + trait +
                ", type=" + type +
                ", valueType=" + valueType +
                ", valueEffect=" + valueEffect +
                ", value='" + value + '\'' +
                '}';
    }
}
