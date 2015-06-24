package com.java.wise.ticketsolver.triggers;


public class TriggerType {	

	public static final TriggerType AND = new TriggerType("AND");
	public static final TriggerType OR = new TriggerType("OR");

	private final String mType; 


	public TriggerType (String type) {
		this.mType = type;
	}

	public String getType() {
		return this.mType;
	}
}
