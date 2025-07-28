package service;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.DelayQueue;

import domain.TicketBean;



public class ServerTicket {

	private static DelayQueue<TicketBean> QUEUE_TICKET = new DelayQueue<TicketBean>();
	private static HashMap<String, TicketBean> MAP_TICKET_TOKEN = new HashMap<String, TicketBean>();
	private static Thread THREAD_CLEANER = null;
	
	public ServerTicket() {
		init();
	}
	
	public static String addTicket(String appUrl, String token) {
		init();
		
		String ticket = UUID.randomUUID().toString();
		while(MAP_TICKET_TOKEN.containsKey(ticket)) {
			ticket = UUID.randomUUID().toString();
		}
		
		TicketBean ticketBean = new TicketBean(appUrl, token, ticket, 60); 
		QUEUE_TICKET.offer(ticketBean);
		MAP_TICKET_TOKEN.put(ticket, ticketBean);
		return ticket;
	}
	
	public static boolean hasTicket(String ticket) {
		init();
		
		return MAP_TICKET_TOKEN.containsKey(ticket);
	}
	
	public static String getToken(String appUrl, String ticket) {
		init();
		
		if (!MAP_TICKET_TOKEN.containsKey(ticket))
			return null;

		TicketBean ticketBean = MAP_TICKET_TOKEN.get(ticket);
		if (ticketBean == null)
			return null;
		
		if (appUrl.compareTo(ticketBean.getAppUrl()) != 0)
			return null;
		
		return ticketBean.getToken();
	}
	
	private static void init() {
		if (THREAD_CLEANER == null) {
			THREAD_CLEANER = new Thread(() -> {
		        try {
		            while (true) {
		            	TicketBean e = QUEUE_TICKET.take();   // 阻塞到到期
		            	MAP_TICKET_TOKEN.remove(e.getTicket());               // 真正清理
		            }
		        } catch (InterruptedException ignored) {}
			});
			THREAD_CLEANER.setDaemon(true);
			THREAD_CLEANER.start();
		}
	}
}
