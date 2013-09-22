package org.dnt.whoami.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The Interview Result class. Holds scores for the interview per answers per traits
 *
 * @author dima
 * @since 8/20/13 12:11 AM
 */
@XmlRootElement
public class InterviewResult {

    private String userId;
    private String interviewTemplateId;
    private String interviewId;
    private String interviewName;
    private List<TraitScore> indirectScores;
    private List<TraitScore> directScores;
    private boolean complete;

    public InterviewResult() {
    }

    public InterviewResult(String userId,
                           String interviewTemplateId,
                           String interviewId,
                           String interviewName,
                           List<TraitScore> indirectScores,
                           List<TraitScore> directScores,
                           boolean complete) {
        this.userId = userId;
        this.interviewTemplateId = interviewTemplateId;
        this.interviewId = interviewId;
        this.interviewName = interviewName;
        this.indirectScores = indirectScores;
        this.directScores = directScores;
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

    public String getInterviewName() {
        return interviewName;
    }

    public void setInterviewName(String interviewName) {
        this.interviewName = interviewName;
    }

    public List<TraitScore> getIndirectScores() {
        return indirectScores;
    }

    public void setIndirectScores(List<TraitScore> indirectScores) {
        this.indirectScores = indirectScores;
    }

    public List<TraitScore> getDirectScores() {
        return directScores;
    }

    public void setDirectScores(List<TraitScore> directScores) {
        this.directScores = directScores;
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
                ", interviewName='" + interviewName + '\'' +
                ", indirectScores=" + indirectScores +
                ", directScores=" + directScores +
                ", complete=" + complete +
                '}';
    }
}
