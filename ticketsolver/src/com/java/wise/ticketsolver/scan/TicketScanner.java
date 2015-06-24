package com.java.wise.ticketsolver.scan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Status;
import org.zendesk.client.v2.model.Ticket;

import com.java.wise.ticketsolver.client.Client;

/**
 * Scanner generates a list of candidate tickets to solve.
 * 
 * @author dovik
 *
 */

public class TicketScanner {

	private List<Ticket> tickets = new ArrayList<Ticket>(); 

	public List<Ticket> scan () {
		Zendesk zd = Client.getClient();
		Iterable<Ticket> iterable = zd.getTickets(); 
		Iterator<Ticket> it = iterable.iterator();
		int i = 0; 
		while(it != null && it.hasNext()) {
			i++;
			Ticket t =(Ticket) it.next();
			tickets.add(t);
		}
		return tickets;
	}
}
