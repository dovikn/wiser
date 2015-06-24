package com.java.wise.ticketsolver.processor;

import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Comment;
import org.zendesk.client.v2.model.Status;
import org.zendesk.client.v2.model.Ticket;

import com.java.wise.ticketsolver.client.Client;
import com.java.wise.ticketsolver.client.DBClient;
import com.java.wise.ticketsolver.triggers.ResolutionActions;
import com.java.wise.ticketsolver.triggers.Trigger;

public class TicketProcessor {

	public static boolean process (Trigger trigger, Ticket ticket) {
		if (null == ticket || null == trigger) {
			return false;
		}
		
		Zendesk zd = Client.getClient();
		
		String ticketUpdate = trigger.getTicketUpdate();
		ResolutionActions resolutionAction = trigger.getResolutionActions();
		
		// update
		if (resolutionAction.getResolutionAction().equals(ResolutionActions.UPDATE.getResolutionAction()) ||
				resolutionAction.getResolutionAction().equals(ResolutionActions.UPDATE_AND_CLOSE.getResolutionAction()) || 
				resolutionAction.getResolutionAction().equals(ResolutionActions.UPDATE_AND_ASSIGN.getResolutionAction())) {

			String prefix = "**** This update was automatically added by Wise ****\n";
			
			ticket.setComment(new Comment(prefix+ ticketUpdate));
			zd.updateTicket(ticket);
			System.out.println("[INFO] Added a comment to ticket " + ticket.getId() + ".");	 
		}
		
		// assign
		if (resolutionAction.getResolutionAction().equals(ResolutionActions.ASSIGN.getResolutionAction()) ||
				resolutionAction.getResolutionAction().equals(ResolutionActions.UPDATE_AND_ASSIGN.getResolutionAction())) {
			ticket.setAssigneeId(new Long (1));
			zd.updateTicket(ticket);
			System.out.println("[INFO] Assigned ticket " + ticket.getId() + ".");	 
		}
		
		
		
//		// close
//		if (resolutionAction.getResolutionAction().equals(ResolutionActions.CLOSE.getResolutionAction()) ||
//				resolutionAction.getResolutionAction().equals(ResolutionActions.UPDATE_AND_CLOSE.getResolutionAction())) {
//			ticket.setStatus(Status.CLOSED);
//			zd.updateTicket(ticket);
//			System.out.println("[INFO] Closed ticket " + ticket.getId() + ".");	 
//		}
		
		DBClient.incrementCountOfTicketsToSolveBy1(trigger.getId());
		
		return true;
	}
	
}
