package uk.ac.london.co3326.harness;

public class TestResult {

    protected int score = 0;
    protected String error;
    protected String description;

    public TestResult(TestResult source) {
        if (source != null) {
            this.score = source.getScore();
            this.error = source.getError();
            this.description = source.getDescription();
        }
    }
    
    public TestResult(String description) {
        this.description = description;
    }
    
    public int getScore() {        
        return score;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TestResult [score=");
        builder.append(score);
        builder.append(", ");
        if (error != null) {
            builder.append("error=");
            builder.append(error);
            builder.append(", ");
        }
        if (description != null) {
            builder.append("description=");
            builder.append(description);
        }
        builder.append("]");
        return builder.toString();
    }
    
}
