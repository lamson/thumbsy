package me.lamson.thumbsy.models;

public class Sms {

	public static final String ENTITY_NAME = "Message";
	public static final String PROPERTY_ID = "messageId";
	public static final String PROPERTY_THREAD_ID = "threadId";
	public static final String PROPERTY_THREAD_KEY = "threadKey";
	public static final String PROPERTY_ADDRESS = "address";
	public static final String PROPERTY_READ = "read";
	public static final String PROPERTY_INCOMING = "incoming";
	public static final String PROPERTY_BODY = "body";
	public static final String PROPERTY_DATE = "date";

	private Long id;
	private Long threadId;
	private String address;
	private Boolean read;
	private Boolean incoming;
	private String body;
	private Long date;
	private String userId;

	public Sms() {
	}

	public Sms(Long id) {
		this.setId(id);
	}

	public Sms(Long msgId, String msgBody, String msgAddress, Boolean incoming,
			Long date) {
		this.setId(msgId);
		this.setBody(msgBody);
		this.setAddress(msgAddress);
		this.setIncoming(incoming);
		this.setDate(date);
	}

	public Sms(Long id, Long threadId, String address, Boolean read,
			Boolean incoming, String body) {
		this.setId(id);
		this.setThreadId(threadId);
		this.setAddress(address);
		this.setRead(read);
		this.setIncoming(incoming);
		this.setBody(body);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getThreadId() {
		return threadId;
	}

	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean isRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean isIncoming() {
		return incoming;
	}

	public void setIncoming(Boolean incoming) {
		this.incoming = incoming;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}