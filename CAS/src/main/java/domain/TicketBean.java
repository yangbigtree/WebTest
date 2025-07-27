package domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TicketBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String app;
	private String token;
	private Timestamp created_at;

	public TicketBean() {
		super();
		init("", "");
	}

	public TicketBean(String app, String token) {
		super();
		init(app, token);
	}

	private void init(String app, String token) {
		this.app = app;
		this.token = token;
		this.created_at = Timestamp.valueOf(LocalDateTime.now());
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

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
	
}
