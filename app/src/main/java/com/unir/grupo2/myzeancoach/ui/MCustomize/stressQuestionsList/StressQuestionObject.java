package com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList;

import com.unir.grupo2.myzeancoach.domain.model.StressAnswersItem;

import java.util.List;

/**
 * Created by andres on 28/03/2017.
 */

public class StressQuestionObject {
    String description;
    String user_answer;
    String popup;
    List<StressAnswersItem> elementos;

    public String getPopup() {
        return popup;
    }

    public void setPopup(String popup) {
        this.popup = popup;
    }

    public StressQuestionObject(String description, String user_answer, List<StressAnswersItem> elementos) {
        this.description = description;
        this.user_answer = user_answer;
        this.elementos = elementos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public List<StressAnswersItem> getElementos() {
        return elementos;
    }

    public void setElementos(List<StressAnswersItem> elementos) {
        this.elementos = elementos;
    }
}
