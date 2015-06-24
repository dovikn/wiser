package com.java.wise.ticketsolver.triggers;

import org.zendesk.client.v2.model.Ticket;

public class Rule {

	protected Component mComponent;
	protected Action mAction;
	protected String mTarget;
	protected boolean mIsEnabled = true;
	protected Integer mId;
	protected String mName;
	
	public Rule (Integer id, String name, Component c, Action a, String ta, boolean isEnabled ) {
		this.mComponent = c;
		this.mAction = a;
		this.mTarget = ta;
		this.mId = id;
		this.mIsEnabled = isEnabled;
		this.mName = name;
	}
	
	public boolean isEnabled() {
		return this.mIsEnabled;
	}
	
	public boolean test(Ticket t) {
		
		if (t == null) {
			return false;
		}
		return true;
	}
	
	public Integer getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
}
