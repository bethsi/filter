package web;

import javax.servlet.http.HttpSession;

public class SessionHandler {
	private final HttpSession session;
	private static final String EMPLOYEE_NAME_ATTRIBUTE = "kjfIjeucmLKJSFkfs";
	private static final String ERROR_MESSAGE_ATTRIBUTE = "lkZWKFhgfzerhjJVN";
	
	public SessionHandler(HttpSession session) {
		this.session = session;
	}

	public void setFormFilled(String employeeName) {
		session.setAttribute(EMPLOYEE_NAME_ATTRIBUTE, employeeName);
	}

	public String getEmployeeName() throws IllegalArgumentException {
		Object employeeName = session.getAttribute(EMPLOYEE_NAME_ATTRIBUTE);
		if (employeeName instanceof String) {
			return (String) employeeName;
		} else {
			throw new IllegalArgumentException("The form was not filled.");
		}
	}
	
	public boolean isFormFilled() {
		Object attribute = session.getAttribute(EMPLOYEE_NAME_ATTRIBUTE);
		return ( attribute != null);
	}
	
	public void setErrorMessage(String errorMessage) {
		session.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
	}
	
	public String getErrorMessage() {
		Object errorMessage = session.getAttribute(ERROR_MESSAGE_ATTRIBUTE);
		if (errorMessage instanceof String) {
			return (String) errorMessage;
		} else {
			return "";
		}
	}
	
	public void unsetErrorMessage() {
		session.removeAttribute(ERROR_MESSAGE_ATTRIBUTE);
	}
}
