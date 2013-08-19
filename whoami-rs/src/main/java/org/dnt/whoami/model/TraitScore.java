package org.dnt.whoami.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/20/13 12:31 AM
 */
@XmlRootElement
public class TraitScore {
    private PersonalityTrait trait;
    private int score;

    public TraitScore() {
    }

    public TraitScore(PersonalityTrait trait, int score) {
        this.trait = trait;
        this.score = score;
    }

    public PersonalityTrait getTrait() {
        return trait;
    }

    public void setTrait(PersonalityTrait trait) {
        this.trait = trait;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TraitScore{" +
                "trait=" + trait +
                ", score=" + score +
                '}';
    }
}
