package firstTest.model;

public class Comment {

    private String programmingExperience;
    private String recommendedBy;
    private String additionalComment;

    public Comment(String programmingExperience, String recommendedBy, String additionalComment) {
        this.programmingExperience = programmingExperience;
        this.recommendedBy = recommendedBy;
        this.additionalComment = additionalComment;
    }

    public String getProgrammingExperience() {
        return programmingExperience;
    }

    public void setProgrammingExperience(String programmingExperience) {
        this.programmingExperience = programmingExperience;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }
}
