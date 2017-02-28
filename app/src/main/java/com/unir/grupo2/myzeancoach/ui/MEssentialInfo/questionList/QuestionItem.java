package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

/**
 * Created by Cesar on 26/02/2017.
 */

public class QuestionItem {

    private int number;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;

    public QuestionItem(int number, String question, String answer1,String answer2, String answer3){
        this.number = number;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
