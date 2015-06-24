package com.java.wise.ticketsolver.triggers;

import java.util.Iterator;
import java.util.List;

import org.zendesk.client.v2.model.Ticket;

import com.java.wise.ticketsolver.client.DBClient;

public class Trigger {
	
	Integer 			mId;
	TriggerType 		mType; // OR, AND
	String 				mName = "";
	List<Rule> 			mRules = null;
	int 				mNumOfSuccesses = 0;
	int 				mNumOfFailures = 0;
	String				mTicketUpdate = "";
	ResolutionActions 	mResolutionAction = null;
	boolean 			mIsEnabled;
	
	
	protected Trigger () {};
	
	public Trigger (Integer id, String name, TriggerType type, List<Rule> rules, boolean isEnabled , String ticketUpdate, ResolutionActions ra) {
		mId = id;
		mType = type;
		mIsEnabled = isEnabled;
		this.mRules = rules;
		this.mName = name;
		this.mTicketUpdate = ticketUpdate;
		this.mResolutionAction = ra;
	}

	public boolean match (Ticket t) {
		if (mRules == null || mRules.size() == 0) {
			return false;
		}

		Iterator<Rule> it = mRules.iterator();
		int i = 0;
		while (it.hasNext()) {

			Rule r = (Rule)it.next();

			boolean result = r.test(t);
			System.out.println("[DEBUG] Trigger:" + mId.intValue() +" Rule: " + r.getName() + ", Ticket: " + t.getId() + ", Returned: " + result);
			
			if (mType.getType().equals(TriggerType.AND.getType()) && !result) {
				return false;
			} 
			if (mType.getType().equals(TriggerType.OR.getType()) && result) {
				return true;
			}
			if (result) {
				mNumOfSuccesses++;
			} else {
				mNumOfFailures++;
			}
			i++;
		}
		
		if (i == mRules.size()) {
			
			// If "AND" and all tests succeeded
			if (mType.getType().equals(TriggerType.AND.getType()) && mNumOfFailures == 0) {
				return true;
			}
			
			// If "OR" and all tests failed.
 			if (mType.getType().equals(TriggerType.OR.getType()) && mNumOfSuccesses == 0) {
				return false;
			}
		}
		return false;
	}
	
	public Integer getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getTicketUpdate() {
		return mTicketUpdate;
	}
	
	public ResolutionActions getResolutionActions() {
		return this.mResolutionAction;
	}
}
