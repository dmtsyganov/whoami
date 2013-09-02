package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 2:04 PM
 */
@XmlRootElement
@Embedded
public class Question {

    public enum Type {
        DIRECT, INDIRECT, INFORMATION
    }

    public enum ValueType {
        YES_NO, SCORE, TEXT
    }

    private String text;
    private PersonalityTrait trait;
    private Type type;
    private ValueType valueType;

    public Question() {
    }

    public Question(String text, PersonalityTrait trait, Type type, ValueType valueType) {
        this.text = text;
        this.trait = trait;
        this.type = type;
        this.valueType = valueType;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "Question{" +
                ", text='" + text + '\'' +
                ", trait=" + trait +
                ", type=" + type +
                ", valueType=" + valueType +
                '}';
    }
}
