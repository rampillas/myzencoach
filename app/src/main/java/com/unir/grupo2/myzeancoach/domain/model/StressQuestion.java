package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class StressQuestion{

	@SerializedName("user")
	@Expose
	private String user;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("possible_answers")
	@Expose
	private Integer possibleAnswers;
	@SerializedName("is_personal_question")
	@Expose
	private Boolean isPersonalQuestion;
	@SerializedName("active")
	@Expose
	private Integer active;
	@SerializedName("user_answer")
	@Expose
	private String userAnswer;
	@SerializedName("questions")
	@Expose
	private List<StressAnswersItem> questions = null;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPossibleAnswers() {
		return possibleAnswers;
	}

	public void setPossibleAnswers(Integer possibleAnswers) {
		this.possibleAnswers = possibleAnswers;
	}

	public Boolean getIsPersonalQuestion() {
		return isPersonalQuestion;
	}

	public void setIsPersonalQuestion(Boolean isPersonalQuestion) {
		this.isPersonalQuestion = isPersonalQuestion;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public List<StressAnswersItem> getQuestions() {
		return questions;
	}

	public void setQuestions(List<StressAnswersItem> questions) {
		this.questions = questions;
	}
}