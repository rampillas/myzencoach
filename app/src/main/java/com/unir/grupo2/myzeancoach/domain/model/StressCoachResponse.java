package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class StressCoachResponse{

	@SerializedName("description")
	@Expose
	private String description;

	@SerializedName("active")
	@Expose
	private int active;

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setActive(int active){
		this.active = active;
	}

	public int getActive(){
		return active;
	}

	@Override
 	public String toString(){
		return 
			"StressCoachResponse{" + 
			"description = '" + description + '\'' + 
			",active = '" + active + '\'' + 
			"}";
		}
}