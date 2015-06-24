package com.java.wise.ticketsolver.triggers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zendesk.client.v2.model.Ticket;

public class RegexRule extends Rule {

	public RegexRule(Integer id, String name, Component c, Action a, String regex) {
		super(id, name, c, a, regex, true);
	}

	public boolean test(Ticket t) {

		if (t == null) {
			return false;
		}

		Pattern p = Pattern.compile(mTarget);

		if (mComponent.getComeponent().equals(Component.TITLE.getComeponent())) {
			Matcher m = p.matcher(t.getSubject());
			return m.find();
		}

		if (mComponent.getComeponent().equals(Component.DESCRIPTION.getComeponent())) {
			Matcher m = p.matcher(t.getDescription());
			return m.find();
		}

		else return ( (t.getSubject().matches(mTarget)) ||
				(t.getDescription().matches(mTarget)));
	}
}
