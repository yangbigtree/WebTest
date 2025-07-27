package service;

import java.util.HashMap;
import java.util.UUID;

import domain.TicketBean;



public class ServerTicket {

	private static HashMap<String, TicketBean> MAP_TICKET_TOKEN = new HashMap<String, TicketBean>();
	
	public ServerTicket() {
		
	}
		
	public String addTicket(String token) {
		String ticket = null;
		return ticket;
	}
	
	public String getTicket(String ticket) {
		String token = null;
		return token;
	}
}
