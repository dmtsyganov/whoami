package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The Dictionary Record, represents entry in a dictionary
 *
 * @author dima
 * @since 10/9/13 11:36 PM
 */
@XmlRootElement
@Entity(noClassnameStored = true)
public class DictionaryRecord {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String entry;

    private String definition;

    public DictionaryRecord() {
    }

    public DictionaryRecord(String entry, String definition) {
        this.entry = entry;
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

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "DictionaryRecord{" +
                "id=" + id +
                ", entry='" + entry + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}
