package org.dnt.whoami.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

/**
 * TODO: add class description
 * @author dima
 * @since  5/25/13 2:04 AM
 */
@XmlRootElement
@Entity
public class UserRecord {

    @Id
    private ObjectId id;

    private String login;
    private String password;
    private String fullName;
    private String email;
    private int age;
    private UserRole role;
    private Date dateCreated;
    private Date dateUpdated;

    public UserRecord() {
    }

    public UserRecord(String login,
                      String password,
                      String fullName,
                      String email,
                      int age,
                      UserRole role,
                      Date dateCreated,
                      Date dateUpdated) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.role = role;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
