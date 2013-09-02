package org.dnt.whoami.model;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/11/13 3:42 PM
 */
public enum PersonalityTrait {

    ENERGETIC(TraitCategory.TEMPERAMENT, "эргичность", 1, 7),
    FLEXIBILITY(TraitCategory.TEMPERAMENT, "пластичность", 1, 7),
    SENSITIZATION(TraitCategory.TEMPERAMENT, "сензитивность", 1, 7),

    COMMUNICABILITY(TraitCategory.PERSONALITY, "коммуникабельность", 1, 7),
    DOMINANT(TraitCategory.PERSONALITY, "доминантность", 1, 7),

    TOLERANCE(TraitCategory.CHARACTER, "толерантность", 1, 7),
    RESISTANCE(TraitCategory.CHARACTER, "резистентость", 1, 7),

    INTELLECTUAL(TraitCategory.CONSCIOUSNESS, "интеллектуал", 1, 7),
    CONNOTATIVE(TraitCategory.CONSCIOUSNESS, "конативный", 1, 7),
    CREATIVITY(TraitCategory.CONSCIOUSNESS, "креативный", 1, 7),
    EMOTIVE(TraitCategory.CONSCIOUSNESS, "эмотивный", 1, 7),

    SELF_PRESERVATION(TraitCategory.CONSCIENCE, "самосохранение", 1, 7),
    SELF_REALIZATION(TraitCategory.CONSCIENCE, "самореализация", 1, 7),
    SELF_AFFIRMATION(TraitCategory.CONSCIENCE, "самоутверждение", 1, 7),
    SELF_CONTROL(TraitCategory.CONSCIENCE, "самообладание", 1, 7),
    SELF_ACTUALIZATION(TraitCategory.CONSCIENCE, "самоактуализация", 1, 7),
    SELF_IDENTIFICATION(TraitCategory.CONSCIENCE, "самоидентификация", 1, 7),
    SELF_RESPECT(TraitCategory.CONSCIENCE, "самоуважение", 1, 7);

    private final TraitCategory category;
    private final String description;
    private final int minScore;
    private final int maxScore;

    private PersonalityTrait(TraitCategory category,
                             String description,
                             int minScore,
                             int maxScore) {
        this.category = category;
        this.description = description;
        this.minScore = minScore;
        this.maxScore = maxScore;
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

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
