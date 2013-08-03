package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 2:08 PM
 */
@XmlRootElement
@Entity
public class InterviewTemplate {

    @Id
    private ObjectId id;

    public InterviewTemplate() {
    }
}
