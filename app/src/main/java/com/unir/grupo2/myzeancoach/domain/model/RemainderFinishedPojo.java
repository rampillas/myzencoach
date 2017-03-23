package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RemainderFinishedPojo{

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("is_finished")
	@Expose
	private boolean isFinished;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setIsFinished(boolean isFinished){
		this.isFinished = isFinished;
	}

	public boolean isIsFinished(){
		return isFinished;
	}

	@Override
 	public String toString(){
		return 
			"RemainderFinishedPojo{" + 
			"title = '" + title + '\'' + 
			",is_finished = '" + isFinished + '\'' + 
			"}";
		}
}