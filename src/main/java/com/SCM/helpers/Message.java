package com.SCM.helpers;

public class Message {

	private String content;
	private MessageType type = MessageType.blue;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public Message(String content, MessageType string) {
		super();
		this.content = content;
		this.type = string;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String string) {
		// TODO Auto-generated constructor stub
	}
	
	
}
