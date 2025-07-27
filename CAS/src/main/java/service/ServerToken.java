package service;

import java.util.HashMap;
import java.util.UUID;

import domain.UserBean;

public class ServerToken {

	private static HashMap<String, UserBean> MAP_TOKEN_USER = new HashMap<String, UserBean>();
	
	public ServerToken() {
		// TODO Auto-generated constructor stub
	}
	
	public static String addUser(UserBean user) {
		String ticket = user.getId() + "_" + UUID.randomUUID().toString();
		while (MAP_TOKEN_USER.containsKey(ticket)) {
			ticket = UUID.randomUUID().toString();
		}
		MAP_TOKEN_USER.put(ticket, user);
		return ticket;
	}
	
	public static UserBean getUser(String st) {
		return MAP_TOKEN_USER.get(st);
	}
}
