package me.lamson.thumbsy.models;

public class SmsThread {

	public static final String ENTITY_NAME = "SmsThread";
	public static final String PROPERTY_ID = "threadId";
	public static final String PROPERTY_USER_ID = "userId";
	public static final String PROPERTY_ADDRESS = "address";
	public static final String PROPERTY_RECIPIENT_NAME = "recipientName";
	public static final String PROPERTY_DATE = "date";

	private String id;
	private String userId;
	private String address;
	private Long date;
	private String recipientName;
	private Integer numberOfMessages = 0;

	public SmsThread() {
	}

	public SmsThread(String id, String userId, String address) {
		this.setId(id);
		this.setUserId(userId);
		this.setAddress(address);
	}

	public SmsThread(String id, String userId, String address,
			String recipientName) {
		this.id = id;
		this.userId = userId;
		this.address = address;
		this.recipientName = recipientName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return recipientName;
	}

	public void setContent(String content) {
		this.recipientName = content;
	}

	public Integer getNumberOfMessages() {
		return numberOfMessages;
	}

	public void updateNumberOfMessages() {
		this.numberOfMessages++;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}