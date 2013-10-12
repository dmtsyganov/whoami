package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Glossary Record, represents a term and a definition in a glossary
 *
 * @author dima
 * @since 10/9/13 11:36 PM
 */
@XmlRootElement
@Entity(noClassnameStored = true)
public class GlossaryRecord {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String term;

    private String definition;

    public GlossaryRecord() {
    }

    public GlossaryRecord(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    @XmlTransient
    public ObjectId getObjectId() {
        return id;
    }

    public String getId() {
        return id != null ? id.toString() : null;
    }

    public void setId(String id) {
        if (ObjectId.isValid(id)) {
            this.id = new ObjectId(id);
        }
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "GlossaryRecord{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}
