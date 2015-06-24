package com.java.wise.ticketsolver.triggers;

public class Component {
	
	public static final Component TITLE = new Component(1,"title");
	public static final Component DESCRIPTION = new Component(2, "description");
	
	private final String mName; 
	private final Integer mId;

	public Component(Integer id, String name) {
        this.mId = id;
		this.mName = name;
	}
	
	public String getComeponent() {
		return this.mName;
	}
	
	public Integer getId() {
		return this.mId;
	}
}
