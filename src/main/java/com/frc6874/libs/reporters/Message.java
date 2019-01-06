package com.frc6874.libs.reporters;

import java.util.Objects;

public class Message {
	public MessageLevel messageLevel;

	private String message;

	public Message(String message, MessageLevel messageLevel) {
		this.message = message;
		this.messageLevel = messageLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return messageLevel == message.messageLevel &&
				Objects.equals(this.message, message.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(messageLevel, message);
	}

	@Override
	public String toString() {
		String retVal;

		switch (messageLevel) {
			case DEFAULT:
				retVal = "DEFAULT: " + message;
				break;
			case ERROR:
				retVal = "ERROR: " + message;
				break;
			case WARNING:
				retVal = "WARNING: " + message;
				break;
			case INFO:
			default:
				retVal = message;
				break;
		}

		retVal += "\n\r";
		return retVal;
	}

	public String toString(boolean appendReportingLevel) {
		return message;
	}
}