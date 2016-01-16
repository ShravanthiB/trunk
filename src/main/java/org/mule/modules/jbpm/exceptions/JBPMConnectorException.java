package org.mule.modules.jbpm.exceptions;

/***********************************************************
 * File Name : JBPMConnectorException.java Author :
 * sra@whishworks.cvanthi.bhaskaraom Date: 12-December-2015 Description : This
 * class has the following responsibilities: 1) This class is a marker exception
 * which is thrown when there is an error when establishing a connection 2) This
 * is used to raise an alert based on the type of exception (which is
 * JBPMConnectorException)
 *************************************************************/

public class JBPMConnectorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode = "";
	private String errorMessage;

	public JBPMConnectorException() {
		super();
	}

	public JBPMConnectorException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public JBPMConnectorException(String errorMessage, String errorCode) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public JBPMConnectorException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public JBPMConnectorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
