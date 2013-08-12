package org.dnt.whoami.model;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 3:42 PM
 */
public enum PersonalityTrait {

    ONE("One - 1"),
    TWO("Two - 2"),
    THREE("Three - 3");

    private final String description;

    private PersonalityTrait(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
