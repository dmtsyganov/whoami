package org.dnt.whoami.model;

import com.google.code.morphia.annotations.Entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 4:31 PM
 */
@XmlRootElement
@Entity(noClassnameStored=true)
public class Trait {
    private String category;
    private String categoryName;
    private String name;
    private String description;

    public Trait() {
    }

    public Trait(String category, String categoryName, String name, String description) {
        this.category = category;
        this.categoryName = categoryName;
        this.name = name;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Trait{" +
                "category='" + category + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
