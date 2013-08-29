package org.dnt.whoami.model;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/29/13 1:12 AM
 */
public enum TraitCategory {
    TEMPERAMENT("темперамент"),
    PERSONALITY("личность"),
    CHARACTER("характер"),
    CONSCIOUSNESS("сознание"),
    CONSCIENCE("совесть");

    private final String name;

    private TraitCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
