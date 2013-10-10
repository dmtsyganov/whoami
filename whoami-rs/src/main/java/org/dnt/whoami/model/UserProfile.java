package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Embedded;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/10/13 7:02 PM
 */
@XmlRootElement
@Embedded
public class UserProfile {

    private String fullName;
    private String email;
    private int age;
    private Gender gender;
    private Date dateCreated;
    private Date dateUpdated;

    public UserProfile() {
    }

    public UserProfile(String fullName, String email, int age, Gender gender, Date dateCreated, Date dateUpdated) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    @Override
    public String toString() {
        return "UserProfile{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}

