package exception;

import javax.ws.rs.core.Response.Status;

public class CustomException extends Exception {
	private Status status;
	
	public CustomException(String message, Status status) {
		super(message);
		this.status = status;
	}

	public CustomException(Status status) {
		super();
		this.status = status;
	}
	
}
