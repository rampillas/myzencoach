package com.unir.grupo2.myzeancoach.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class StressCoachResponsePojo {

	@SerializedName("next")
	@Expose
	private Object next;

	@SerializedName("previous")
	@Expose
	private Object previous;

	@SerializedName("count")
	@Expose
	private int count;

	@SerializedName("results")
	@Expose
	private List<StressCoachResponse> results;

	public void setNext(Object next){
		this.next = next;
	}

	public Object getNext(){
		return next;
	}

	public void setPrevious(Object previous){
		this.previous = previous;
	}

	public Object getPrevious(){
		return previous;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setResults(List<StressCoachResponse> results){
		this.results = results;
	}

	public List<StressCoachResponse> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"StressCoachResponsePojo{" +
			"next = '" + next + '\'' + 
			",previous = '" + previous + '\'' + 
			",count = '" + count + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}