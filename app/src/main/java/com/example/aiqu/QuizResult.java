package com.example.aiqu;

public class QuizResult {

    int number;
    String choice;
    String answer;

    public QuizResult() {
    }

    public QuizResult(int number, String choice, String answer) {
        this.number = number;
        this.choice = choice;
        this.answer = answer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "number=" + number +
                ", choice='" + choice + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
