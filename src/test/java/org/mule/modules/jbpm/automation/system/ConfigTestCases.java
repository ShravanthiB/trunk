package org.mule.modules.jbpm.automation.system;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.mule.modules.jbpm.config.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mule.api.ConnectionException;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;
import org.mule.tools.devkit.ctf.exceptions.ConfigurationLoadingFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigTestCases {

    private static final Logger logger = LoggerFactory.getLogger(ConfigTestCases.class);

    @Rule
    public TestName name = new TestName();

    private ConnectionManagementStrategy config;
    private Properties credentials;
    private String username;
    private String password;

    @Before
    public void setUp() throws ConnectionException, ConfigurationLoadingFailedException {

        logger.debug("\n\n++++++++++++++++++++ TEST {} ++++++++++++++++++++\n", name.getMethodName());

        credentials = ConfigurationUtils.getAutomationCredentialsProperties();
        username = credentials.getProperty("config.connectionUser");
        password = credentials.getProperty("config.connectionPassword");

    }

    @Test
    public void validCredentials() throws ConnectionException, IOException {
        config = new ConnectionManagementStrategy();
        config.setUrl("http://localhost:8080/jbpm-console/rest");
        config.connect(username, password);
        assertNotNull(config);
        
    }

    @Test(expected = ConnectionException.class)
    public void invalidUsernameCredentials() throws ConnectionException, IOException {
        config = new ConnectionManagementStrategy();
        config.connect("INVALID_USER", password);
       
    }

    @Test(expected = ConnectionException.class)
    public void emptyUsernameCredentials() throws ConnectionException, IOException {
        config = new ConnectionManagementStrategy();
        config.testConnectivity("", password);
    }

    @Test(expected = ConnectionException.class)
    public void invalidPasswordCredentials() throws ConnectionException, IOException {
        config = new ConnectionManagementStrategy();
        config.testConnectivity(username, "INVALID_PASSWORD");
    }

    @Test(expected = ConnectionException.class)
    public void emptyPasswordCredentials() throws ConnectionException, IOException {
        config = new ConnectionManagementStrategy();
        config.testConnectivity(username, "");

    }
 

}
