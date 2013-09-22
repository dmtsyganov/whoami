package org.dnt.whoami.utils;

import org.dnt.whoami.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The Calculator class. Calculates interview scores.
 *
 * @author dima
 * @since 9/2/13 11:10 PM
 */
public final class Calculator {
    private final static Logger logger = LoggerFactory.getLogger(Calculator.class);

    /**
     * Calculate results for an interview
     * @param interview {@link Interview}
     * @return {@link InterviewResult}
     */
    public static InterviewResult calcInterviewResult(Interview interview) {

        Map<PersonalityTrait, TraitScore> indirectScores = new HashMap<PersonalityTrait, TraitScore>();
        List<TraitScore> directScores = new ArrayList<TraitScore>();
        int indirectQuestions = 0;
        boolean isComplete = true;
        for (Answer answer : interview.getAnswers()) {

            if (answer.getType() == Question.Type.INFORMATION)
                continue; // skip the information/text question

            Integer score = 0;
            if (answer.getValue() == null) {
                isComplete = false;
            } else {
                score = getScore(answer);
            }

            if (answer.getType() == Question.Type.INDIRECT) {
                // this is indirect evaluation question
                indirectQuestions++;
                TraitScore indirectScore = indirectScores.get(answer.getTrait());
                if (indirectScore == null) {
                    indirectScore = new TraitScore(answer.getTrait(), score);
                    indirectScores.put(answer.getTrait(), indirectScore);
                } else {
                    indirectScore.setScore(indirectScore.getScore() + score);
                }
            } else if (answer.getType() == Question.Type.DIRECT) {
                // this is direct evaluation question (self)
                directScores.add(new TraitScore(answer.getTrait(), score));
            }
        }

        // divide scores by max # trait questions / max score (eg 14 / 7 = 2 - divide by 2 etc.)
        int divisor = 1;
        if(indirectScores.values().size() > 0) {
            divisor = indirectQuestions / indirectScores.values().size() / 7; //NOTE: assumes 7 is max
        }

        if(divisor == 0)
            divisor = 1;

        List<TraitScore> totalIndirectScores = new ArrayList<TraitScore>(indirectScores.values().size());

        for (TraitScore ts : indirectScores.values()) {
            ts.setScore(ts.getScore()/divisor);
            totalIndirectScores.add(ts);
        }

        return new InterviewResult(interview.getUserId().toString(),
                interview.getTemplateId().toString(),
                interview.getId(),
                interview.getName(),
                totalIndirectScores,
                directScores,
                isComplete);
    }

    /**
     * Calculates overall result and coefficient of concordance
     *
     * @param interviewResults {@link InterviewResult} list
     * @return {@link Result}
     */
    public static Result calculateResult(List<InterviewResult> interviewResults) {

        // get user id from the firs one
        String userId = "";
        if(interviewResults.size() > 0 ) {
            userId = interviewResults.get(0).getUserId();
        }

        EnumSet<PersonalityTrait> allTraits = EnumSet.allOf(PersonalityTrait.class);
        EnumMap<PersonalityTrait, TraitScore> indirect = new EnumMap<PersonalityTrait, TraitScore>(PersonalityTrait.class);
        EnumMap<PersonalityTrait, TraitScore> direct = new EnumMap<PersonalityTrait, TraitScore>(PersonalityTrait.class);

        for(InterviewResult result: interviewResults) {

            for(TraitScore indirectScore : result.getIndirectScores()) {
                indirect.put(indirectScore.getTrait(), indirectScore);
            }

            for(TraitScore directScore : result.getDirectScores()) {
                direct.put(directScore.getTrait(), directScore);
            }
        }

        // calculate coefficient of concordance
        double coefficient = 0.0;
        for(PersonalityTrait trait: allTraits) {
            TraitScore d = direct.get(trait);
            if(d == null) {
                // put 0 score
                d =  new TraitScore(trait, 0);
                direct.put(trait, d);
            }

            TraitScore ind = indirect.get(trait);
            if(ind == null) {
                // put 0 score
                ind = new TraitScore(trait, 0);
                indirect.put(trait, ind);
            }

            coefficient += Math.abs(d.getScore() - ind.getScore());
        }

        List<TraitScore> indirectScores = new ArrayList<TraitScore>(indirect.values());
        List<TraitScore> directScores = new ArrayList<TraitScore>(direct.values());

        return new Result(userId, interviewResults, directScores, indirectScores, coefficient);

    }

    private static Integer getScore(Answer answer) {
        Integer score = 0;

        try {
            switch (answer.getValueType()) {
                case SCORE:
                    score = Integer.valueOf(answer.getValue()); // from 1 up to trait's max score (7)
                    break;
                case YES_NO:
                    score = Integer.valueOf(answer.getValue()); // 1 - yes, 0 - no
                    break;
            }
        } catch (NumberFormatException e) {
            logger.error("Unable to parse the value: {}", e);
        }

        return score;
    }
}
