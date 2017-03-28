package com.unir.grupo2.myzeancoach.domain.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class StressQuestion{

	@SerializedName("is_personal_question")
	@Expose
	private boolean isPersonalQuestion;

	@SerializedName("possible_answers")
	@Expose
	private int possibleAnswers;

	@SerializedName("answers")
	@Expose
	private List<AnswersItem> answers;

	@SerializedName("description")
	@Expose
	private String description;

	public void setIsPersonalQuestion(boolean isPersonalQuestion){
		this.isPersonalQuestion = isPersonalQuestion;
	}

	public boolean isIsPersonalQuestion(){
		return isPersonalQuestion;
	}

	public void setPossibleAnswers(int possibleAnswers){
		this.possibleAnswers = possibleAnswers;
	}

	public int getPossibleAnswers(){
		return possibleAnswers;
	}

	public void setAnswers(List<AnswersItem> answers){
		this.answers = answers;
	}

	public List<AnswersItem> getAnswers(){
		return answers;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	@Override
 	public String toString(){
		return 
			"StressQuestion{" + 
			"is_personal_question = '" + isPersonalQuestion + '\'' + 
			",possible_answers = '" + possibleAnswers + '\'' + 
			",answers = '" + answers + '\'' + 
			",description = '" + description + '\'' + 
			"}";
		}
}