package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class StressAnswersItem {

	@SerializedName("color")
	@Expose
	private String color;

	@SerializedName("popup_message")
	@Expose
	private String popupMessage;

	@SerializedName("description")
	@Expose
	private String description;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setPopupMessage(String popupMessage){
		this.popupMessage = popupMessage;
	}

	public String getPopupMessage(){
		return popupMessage;
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
			"StressAnswersItem{" +
			"color = '" + color + '\'' + 
			",popup_message = '" + popupMessage + '\'' + 
			",description = '" + description + '\'' + 
			"}";
		}
}