package domain;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TicketBean implements Serializable, Delayed  {

	private static final long serialVersionUID = 1L;
	private String app;
	private String ticket;
	private String token;
	private long expire;

	public TicketBean() {
		super();
		init("", "", "", 60);
	}

	public TicketBean(String app, String token, String ticket, long ttl) {
		super();
		init(app, token, ticket, ttl);
	}

	private void init(String app, String token, String ticket, long ttl) {
		this.app = app;
		this.token = token;
		this.expire = System.currentTimeMillis() + ttl * 1000;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	@Override
	public int compareTo(Delayed o) {
		return Long.compare(this.expire, ((TicketBean) o).expire);
	}

	@Override
	public long getDelay(TimeUnit unit) {
        return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
	
}
