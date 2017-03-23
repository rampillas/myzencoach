package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RemaindersPojo{

	@SerializedName("date")
	@Expose
	private String date;

	@SerializedName("subtitle")
	@Expose
	private String subtitle;

	@SerializedName("description")
	@Expose
	private String description;

	@SerializedName("time")
	@Expose
	private String time;

	@SerializedName("type")
	@Expose
	private String type;

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("is_observations_enabled")
	@Expose
	private boolean isObservationsEnabled;

	@SerializedName("user")
	@Expose
	private String user;

	@SerializedName("is_personal")
	@Expose
	private boolean isPersonal;

	@SerializedName("frequency")
	@Expose
	private String frequency;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setSubtitle(String subtitle){
		this.subtitle = subtitle;
	}

	public String getSubtitle(){
		return subtitle;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setIsObservationsEnabled(boolean isObservationsEnabled){
		this.isObservationsEnabled = isObservationsEnabled;
	}

	public boolean isIsObservationsEnabled(){
		return isObservationsEnabled;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getUser(){
		return user;
	}

	public void setIsPersonal(boolean isPersonal){
		this.isPersonal = isPersonal;
	}

	public boolean isIsPersonal(){
		return isPersonal;
	}

	public void setFrequency(String frequency){
		this.frequency = frequency;
	}

	public String getFrequency(){
		return frequency;
	}

	@Override
 	public String toString(){
		return 
			"RemaindersPojo{" + 
			"date = '" + date + '\'' + 
			",subtitle = '" + subtitle + '\'' + 
			",description = '" + description + '\'' + 
			",time = '" + time + '\'' + 
			",type = '" + type + '\'' + 
			",title = '" + title + '\'' + 
			",is_observations_enabled = '" + isObservationsEnabled + '\'' + 
			",user = '" + user + '\'' + 
			",is_personal = '" + isPersonal + '\'' + 
			",frequency = '" + frequency + '\'' + 
			"}";
		}
}