package org.mule.modules.jbpm.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.modules.jbpm.util.JBPMConstants;
import org.mule.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***********************************************************
 * File Name : ConnectionManagementStrategy.java Author :
 * sravanthi.bhaskara@whishworks.com Date: 12-December-2015 Description : This
 * class has the following responsibilities: 1) Defines a connection strategy
 * for basic username and password authentication. 2) Test connection
 * connectivity.
 *************************************************************/
@ConnectionManagement(friendlyName = "HTTP Basic Auth", configElementName = "JBPM_Config")
public class ConnectionManagementStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(ConnectionManagementStrategy.class);

	/**
	 * Constructs a new {@link ConnectionManagementStrategy} Instance with
	 * default configuration values set to false.
	 */
	public ConnectionManagementStrategy() {

	}


	@Configurable
	@Default("http://localhost:8080/jbpm-console/rest")
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	/**
	 * @TestConnectivity makes a connector simpler and helps build better
	 *                   connection strategies
	 * 
	 * @param username
	 * @param password
	 * @throws ConnectionException
	 */
	@TestConnectivity
	public void testConnectivity(@ConnectionKey String username,
			@Password String password) throws ConnectionException {

		logger.debug("Testing connectivity...");

		try {
			this.connect(username, password);
		} finally {
			disconnect();
			logger.debug("Session disconnected");
		}

	}

	/**
	 * @Connect method is called automatically by Mule when the connector starts
	 *          up, or if the connection to the API has been lost and must be
	 *          reestablished
	 * 
	 * @param username
	 *            A username
	 * @param password
	 *            A password
	 * @throws ConnectionException
	 */
	@Connect
	public void connect(@ConnectionKey String username,
			@Password String password) throws ConnectionException {

		// Validate user name
		if (StringUtils.isBlank(username)) {
			logger.error("Invalid username");
			throw new ConnectionException(
					ConnectionExceptionCode.INCORRECT_CREDENTIALS,
					"",
					"Attribute 'user' is required. Provide a valid user to access your Jbpm account.");
		}

		// Validate password
		if (StringUtils.isBlank(password)) {
			logger.error("Invalid password");
			throw new ConnectionException(
					ConnectionExceptionCode.INCORRECT_CREDENTIALS,
					"",
					"Attribute 'password' is required. Provide a valid password to access your Jbpm account.");
		}

		if (!StringUtils.equals(username, JBPMConstants.USER_NAME)) {
			logger.error("Incorrect username");
			throw new ConnectionException(
					ConnectionExceptionCode.INCORRECT_CREDENTIALS,
					"",
					"Attribute 'user' is not Authenticated. Provide a valid user to access your Jbpm account.");
		}

		if (!StringUtils.equals(password, JBPMConstants.PASSWORD)) {
			logger.error("Incorrect password");
			throw new ConnectionException(
					ConnectionExceptionCode.INCORRECT_CREDENTIALS,
					"",
					"Attribute 'password' is not Authenticated. Provide a valid user to access your Jbpm account.");
		}
		
		try {
			URL url = new URL(getUrl());
			URL basicUrl = new URL(JBPMConstants.URL);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			if (!url.equals(basicUrl) || url.equals(null)) {
				logger.error("Incorrect URL");
				throw new ConnectionException(
						ConnectionExceptionCode.CANNOT_REACH,
						"",
						"Could not establish connection to url provided. Provide a valid URL to access your Jbpm account.");
			}
			urlConn.connect();
		} catch (IOException e) {
			throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH,
					"", "Could not establish connection to JBPM url provided. "
							+ e.getMessage(), e);
		}

		
		logger.info("Connection to JBPM established");
		

	}

	/**
	 * Disconnect
	 */

	@Disconnect
	public void disconnect() {

	}

	/**
	 * To Validate whether we connected or not
	 */
	@ValidateConnection
	public boolean isConnected() {
		return true;

	}

	/**
	 * Are we connected
	 */
	@ConnectionIdentifier
	public String connectionId() {
		return "";
	}
	
	
}