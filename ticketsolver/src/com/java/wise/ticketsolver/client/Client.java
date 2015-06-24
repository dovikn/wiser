package com.java.wise.ticketsolver.client;

import org.zendesk.client.v2.Zendesk;
public class Client {

	private static Zendesk mZendeskClient = null;
	private static String ZENDESK_USER = "dovik.nissim@gmail.com";
	private static String ZENDESK_PASS= "dndndndn";		
	
	public static Zendesk getClient() {
	
		if (mZendeskClient == null) {
			mZendeskClient = new Zendesk.Builder("https://wisesupport.zendesk.com")
			.setUsername(ZENDESK_USER)
			.setPassword(ZENDESK_PASS)
			.build();
		}
		return mZendeskClient;
	}
}
