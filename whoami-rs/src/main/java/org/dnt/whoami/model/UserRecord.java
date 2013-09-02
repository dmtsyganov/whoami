package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 2:04 AM
 */
@XmlRootElement
@Entity(noClassnameStored=true)
public class UserRecord {

    @Id
    private ObjectId id;

    @Indexed(unique=true)//, dropDups=true)
    private String login;

    private String password;
    private UserRole role;

    @Embedded
    UserProfile profile;

    public UserRecord() {
    }

    public UserRecord(String id) {
        setId(id);
    }

    public UserRecord(ObjectId id) {
        this.id = id;
    }

    public UserRecord(String login,
                      String password,
                      UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "UserRecord{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", profile=" + profile +
                '}';
    }
}
