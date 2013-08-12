package org.dnt.whoami.model;

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
@Entity
public class Answer {

    @Id
    private ObjectId id;

    public Answer() {

    }
}
