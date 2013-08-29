package org.dnt.whoami.model;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 3:42 PM
 */
public enum PersonalityTrait {

    ENERGETIC(TraitCategory.TEMPERAMENT, "эргичность"),
    FLEXIBILITY(TraitCategory.TEMPERAMENT, "пластичность"),
    SENSITIZATION(TraitCategory.TEMPERAMENT, "сензитивность"),

    COMMUNICABILITY(TraitCategory.PERSONALITY, "коммуникабельность"),
    DOMINANT(TraitCategory.PERSONALITY, "доминантность"),

    TOLERANCE(TraitCategory.CHARACTER, "толерантность"),
    RESISTANCE(TraitCategory.CHARACTER, "резистентость"),

    INTELLECTUAL(TraitCategory.CONSCIOUSNESS, "интеллектуал"),
    CONNOTATIVE(TraitCategory.CONSCIOUSNESS, "конативный"),
    CREATIVITY(TraitCategory.CONSCIOUSNESS, "креативный"),
    EMOTIVE(TraitCategory.CONSCIOUSNESS, "эмотивный"),

    SELF_PRESERVATION(TraitCategory.CONSCIENCE, "самосохранение"),
    SELF_REALIZATION(TraitCategory.CONSCIENCE, "самореализация"),
    SELF_AFFIRMATION(TraitCategory.CONSCIENCE, "самоутверждение"),
    SELF_CONTROL(TraitCategory.CONSCIENCE, "самообладание"),
    SELF_ACTUALIZATION(TraitCategory.CONSCIENCE, "самоактуализация"),
    SELF_IDENTIFICATION(TraitCategory.CONSCIENCE, "самоидентификация"),
    SELF_RESPECT(TraitCategory.CONSCIENCE, "самоуважение");

    private final TraitCategory category;
    private final String description;

    private PersonalityTrait(TraitCategory category, String description) {
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name();
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category.name();
    }

    public String getCategoryName() {
        return category.getName();
    }
}
