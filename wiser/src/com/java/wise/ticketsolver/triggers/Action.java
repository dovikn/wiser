package com.java.wise.ticketsolver.triggers;

public class Action {

	public static final Action MATCH = new Action (1, "match");
	public static final Action LIKE = new Action (2, "like");
	public static final Action CONTAINS = new Action (3, "contains");
	public static final Action REGEX = new Action (4, "regex");

	private final String mAction;

	private final Integer mId;

	public Action(Integer id, String name) {
		this.mId = id;
		this.mAction = name;
	}

	public String getAction() {
		return mAction;
	}
	
	public Integer getId() {
		return this.mId;
	}
}