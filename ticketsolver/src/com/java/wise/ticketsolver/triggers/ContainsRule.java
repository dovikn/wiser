package com.java.wise.ticketsolver.triggers;

import org.zendesk.client.v2.model.Ticket;

public class ContainsRule extends Rule {

	public ContainsRule(Integer id, String name, Component c, Action a, String ta) {
		super(id, name, c, a, ta, true);
	}

	public boolean test(Ticket t) {

		if (t == null) {
			return false;
		}
		
		if (null == mComponent) {
			System.out.println("[ERROR]: Component is empty.") ;
		}
		
		if (null == mAction) {
			System.out.println("[ERROR]: Action is empty.") ;
		}
		

		if ((mComponent.getComeponent().equals(Component.TITLE.getComeponent())) && 
				(mAction.getAction().equals(Action.CONTAINS.getAction()))) {
			String title = t.getSubject();
			boolean result = title.contains(mTarget);
			return result;
		}
		return false;
	}

}
