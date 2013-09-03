package org.dnt.whoami.utils;

import org.dnt.whoami.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Calculator class. Calculates interview scores.
 *
 * @author dima
 * @since 9/2/13 11:10 PM
 */
public final class Calculator {
    private final static Logger logger = LoggerFactory.getLogger(Calculator.class);

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
                totalIndirectScores,
                directScores,
                isComplete);
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
