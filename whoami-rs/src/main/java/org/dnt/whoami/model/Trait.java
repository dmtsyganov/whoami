package org.dnt.whoami.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 4:31 PM
 */
@XmlRootElement

public class Trait {
    private String name;
    private String description;

    public Trait() {
    }

    public Trait(String name, String description) {
        this.name = name;
        this.description = description;
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
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
