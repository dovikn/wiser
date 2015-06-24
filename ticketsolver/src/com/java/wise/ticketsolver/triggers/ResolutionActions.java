package com.java.wise.ticketsolver.triggers;

public class ResolutionActions {

	public static final ResolutionActions UPDATE = new ResolutionActions (1,"UPDATE");
	public static final ResolutionActions CLOSE = new ResolutionActions (2,"CLOSE");
	public static final ResolutionActions UPDATE_AND_CLOSE = new ResolutionActions (3,"UPDATE_AND_CLOSE");
	public static final ResolutionActions ASSIGN = new ResolutionActions (4,"ASSIGN");
	public static final ResolutionActions UPDATE_AND_ASSIGN = new ResolutionActions (5,"UPDATE_AND_ASSIGN");

	private final String mName; 
	private final Integer mId;

	public ResolutionActions(Integer id, String name) {
		this.mId = id;
		this.mName = name;
	}

	public String getResolutionAction() {
		return this.mName;
	}

	public Integer getId() {
		return this.mId;
	}
}
