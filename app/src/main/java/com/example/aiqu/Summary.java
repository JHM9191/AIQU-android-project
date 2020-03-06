package com.example.aiqu;

import java.util.ArrayList;

public class Summary {
    ArrayList<Integer> answered;
    ArrayList<Integer> unanswered;
    ArrayList<Integer> correct;
    ArrayList<Integer> incorrect;

    public Summary() {
    }

    public Summary(ArrayList<Integer> answered, ArrayList<Integer> unanswered, ArrayList<Integer> correct, ArrayList<Integer> incorrect) {
        this.answered = answered;
        this.unanswered = unanswered;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public ArrayList<Integer> getAnswered() {
        return answered;
    }

    public void setAnswered(ArrayList<Integer> answered) {
        this.answered = answered;
    }

    public ArrayList<Integer> getUnanswered() {
        return unanswered;
    }

    public void setUnanswered(ArrayList<Integer> unanswered) {
        this.unanswered = unanswered;
    }

    public ArrayList<Integer> getCorrect() {
        return correct;
    }

    public void setCorrect(ArrayList<Integer> correct) {
        this.correct = correct;
    }

    public ArrayList<Integer> getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(ArrayList<Integer> incorrect) {
        this.incorrect = incorrect;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "answered=" + answered +
                ", unanswered=" + unanswered +
                ", correct=" + correct +
                ", incorrect=" + incorrect +
                '}';
    }
}
