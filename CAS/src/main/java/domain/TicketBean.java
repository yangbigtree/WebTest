package domain;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TicketBean implements Serializable, Delayed  {

	private static final long serialVersionUID = 1L;
	private String appUrl;
	private String ticket;
	private String token;
	private long expire;

	public TicketBean() {
		super();
		init("", "", "", 60);
	}

	public TicketBean(String appUrl, String token, String ticket, long ttl) {
		super();
		init(appUrl, token, ticket, ttl);
	}

	private void init(String appUrl, String token, String ticket, long ttl) {
		this.appUrl = appUrl;
		this.token = token;
		this.expire = System.currentTimeMillis() + ttl * 1000;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
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
