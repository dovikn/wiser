package com.java.wise.ticketsolver;

import java.util.Iterator;
import java.util.List;

import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Comment;
import org.zendesk.client.v2.model.Status;
import org.zendesk.client.v2.model.Ticket;

import com.java.wise.ticketsolver.client.Client;
import com.java.wise.ticketsolver.client.DBClient;
import com.java.wise.ticketsolver.processor.TicketProcessor;
import com.java.wise.ticketsolver.scan.TicketScanner;
import com.java.wise.ticketsolver.triggers.Trigger;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("[INFO] **********************"); 
		System.out.println("[INFO] **** Started Wise ****"); 
		System.out.println("[INFO] **********************"); 
		
		TicketScanner scanner = new TicketScanner(); 
		List<Ticket> tickets = scanner.scan();
		Iterator<Ticket> ticketsIt = tickets.iterator();
		System.out.println("[INFO] Found " + tickets.size() + " tickets in the queue...");

		List<Trigger> triggers = DBClient.loadAllTriggers();
		System.out.println("[INFO] Loaded " + triggers.size() + " triggers from the database..."); 
		
		while(ticketsIt != null && ticketsIt.hasNext()) {
			Ticket t = (Ticket) ticketsIt.next();
			
			// Skip the closed tickets. 
			if (t.getStatus().toString().equals(Status.CLOSED.toString())) {
				continue;
			}
			
			Iterator<Trigger> triggersIt = triggers.iterator();
			while(triggersIt != null && triggersIt.hasNext()) {
				Trigger trigger = triggersIt.next();
				boolean result = trigger.match(t);
				if (result) {
					System.out.println("[INFO] Ticket " + t.getId() + " matched trigger " + trigger.getName() + ".");
					TicketProcessor.process(trigger, t);
				}
			}
		}
	}
}
