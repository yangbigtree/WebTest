package service;

import java.util.HashMap;
import java.util.UUID;

import domain.UserBean;

public class ServerTicket {

	private static HashMap<String, UserBean> s_mapServerTicket = new HashMap<String, UserBean>();
	
	public ServerTicket() {
	}
		
	public static String addUser(UserBean user) {
		String ticket = UUID.randomUUID().toString();
		while (s_mapServerTicket.containsKey(ticket)) {
			ticket = UUID.randomUUID().toString();
		}
		s_mapServerTicket.put(ticket, user);
		return ticket;
	}
	
	public static UserBean getUser(String st) {
		return s_mapServerTicket.get(st);
	}
}
