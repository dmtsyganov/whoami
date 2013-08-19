package org.dnt.whoami.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * TODO: add class description here
 *
 * @author dima
 * @since 8/20/13 12:11 AM
 */
@XmlRootElement
public class InterviewResult {

    private String userId;
    private String interviewTemplateId;
    private String interviewId;
    private List<TraitScore> scores;
    private boolean complete;

    public InterviewResult() {
    }

    public InterviewResult(String userId, String interviewTemplateId, String interviewId, List<TraitScore> scores, boolean complete) {
        this.userId = userId;
        this.interviewTemplateId = interviewTemplateId;
        this.interviewId = interviewId;
        this.scores = scores;
        this.complete = complete;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInterviewTemplateId() {
        return interviewTemplateId;
    }

    public void setInterviewTemplateId(String interviewTemplateId) {
        this.interviewTemplateId = interviewTemplateId;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public List<TraitScore> getScores() {
        return scores;
    }

    public void setScores(List<TraitScore> scores) {
        this.scores = scores;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "InterviewResult{" +
                "userId='" + userId + '\'' +
                ", interviewTemplateId='" + interviewTemplateId + '\'' +
                ", interviewId='" + interviewId + '\'' +
                ", scores=" + scores +
                ", complete=" + complete +
                '}';
    }
}
