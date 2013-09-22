package org.dnt.whoami.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The Results, contains interview results and the summaries
 *
 * @author dima
 * @since 9/22/13 4:37 PM
 */
@XmlRootElement
public class Result {

    private String userId;
    private List<InterviewResult> interviewResults;
    private List<TraitScore> directScores;
    private List<TraitScore> indirectScores;
    private double coefficientOfConcordance;

    public Result() {
    }

    public Result(String userId,
                  List<InterviewResult> interviewResults,
                  List<TraitScore> directScores,
                  List<TraitScore> indirectScores,
                  double coefficientOfConcordance) {
        this.userId = userId;
        this.interviewResults = interviewResults;
        this.directScores = directScores;
        this.indirectScores = indirectScores;
        this.coefficientOfConcordance = coefficientOfConcordance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<InterviewResult> getInterviewResults() {
        return interviewResults;
    }

    public void setInterviewResults(List<InterviewResult> interviewResults) {
        this.interviewResults = interviewResults;
    }

    public List<TraitScore> getDirectScores() {
        return directScores;
    }

    public void setDirectScores(List<TraitScore> directScores) {
        this.directScores = directScores;
    }

    public List<TraitScore> getIndirectScores() {
        return indirectScores;
    }

    public void setIndirectScores(List<TraitScore> indirectScores) {
        this.indirectScores = indirectScores;
    }

    public double getCoefficientOfConcordance() {
        return coefficientOfConcordance;
    }

    public void setCoefficientOfConcordance(double coefficientOfConcordance) {
        this.coefficientOfConcordance = coefficientOfConcordance;
    }

    @Override
    public String toString() {
        return "Result{" +
                "userId='" + userId + '\'' +
                ", interviewResults=" + interviewResults +
                ", directScores=" + directScores +
                ", indirectScores=" + indirectScores +
                ", coefficientOfConcordance=" + coefficientOfConcordance +
                '}';
    }
}
