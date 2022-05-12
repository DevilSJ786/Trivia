package com.bawp.trivia.model;

public class Question {
    private String answer;
    private Boolean answerTrue;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    public Question(String answer, Boolean answerTrue) {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }
}
