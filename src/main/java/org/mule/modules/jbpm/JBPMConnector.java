package org.mule.modules.jbpm;

import java.io.IOException;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ReconnectOn;
import org.mule.api.annotations.rest.HttpMethod;
import org.mule.api.annotations.rest.RestCall;
import org.mule.api.annotations.rest.RestHeaderParam;
import org.mule.api.annotations.rest.RestUriParam;
import org.mule.modules.jbpm.config.ConnectionManagementStrategy;
import org.mule.modules.jbpm.exceptions.JBPMConnectorException;
import org.mule.modules.jbpm.util.JBPMConstants;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/***********************************************************
 * File Name : JBPMConnector.java Author : sravanthi.bhaskara@whishworks.com Date:
 * 12-December-2015 Description : This class has the following responsibilities:
 * 1) This class will export its functionalities as a Mule Connector using @Connector
 * annotation 2) Perform basic authentication to the call 3) Generate Custom
 * processor to make JBPM Restful calls
 *************************************************************/

@Connector(name = "jbpm", friendlyName = "JBPM")
public abstract class JBPMConnector {

	/*
	 * 
	 */
	@Config
	private ConnectionManagementStrategy config;
	
	public ConnectionManagementStrategy getConfig() {
		return config;
	}

	public void setConfig(ConnectionManagementStrategy config) {
		this.config = config;
	}

	
	@RestHeaderParam("Authorization")
	private String authorization = generateBasicAuthorization(JBPMConstants.USER_NAME,JBPMConstants.PASSWORD);

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	private static String generateBasicAuthorization(String userName,
			String password) {
		byte[] encodedPassword = (userName + ":" + password).getBytes();
		Base64Encoder encoder = new Base64Encoder();
		return "Basic " + encoder.encode(encodedPassword);
	}
	

	@RestUriParam("url")
	private String url;

	public String getUrl() {
		return config.getUrl();
	}

	public void setUrl(String url) {
		this.url = config.getUrl();
	}

	/**
	 * Custom processor to perform JBPM Restful calls
	 * 
	 * @param objectName
	 *            object name to serialize
	 * @return serialized object
	 * @throws IOException
	 *             on error
	 */

	/**
	 * Custom processor to retrieval process instance. This operation will fail
	 * (code 400) if the process instance has been completed.
	 */

	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/runtime/{DeploymentId}/process/instance/{ProcessInstanceId}", method = HttpMethod.GET)
	public abstract Object getProcessInstanceDetails(
			@RestUriParam("DeploymentId") String deploymentId,
			@RestUriParam("ProcessInstanceId") String procInstId)
			throws IOException;

	/**
	 * Custom processor to Start a process
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/runtime/{DeploymentId}/process/{processDefId}/start", method = HttpMethod.POST)
	public abstract Object postProcessInstanceStartDetails(
			@RestUriParam("DeploymentId") String deploymentId,
			@RestUriParam("processDefId") String procDefId) throws IOException;

	/**
	 * Custom processor to complete a task.
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/complete", method = HttpMethod.POST)
	public abstract Object postTaskComplete(
			@RestUriParam("taskId") String taskId) throws IOException;

	/**
	 * Custom processor to release a task.
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/release", method = HttpMethod.POST)
	public abstract Object postTaskRelease(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to claim a task.
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/claim", method = HttpMethod.POST)
	public abstract Object postTaskClaim(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to claim next available a task.
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/claimnextavailable", method = HttpMethod.POST)
	public abstract Object postTaskClaimnextavailable(
			@RestUriParam("taskId") String taskId) throws IOException;

	/**
	 * Custom processor to retrieve list of available tasks
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/query", method = HttpMethod.GET)
	public abstract Object getTaskQuery() throws IOException;

	/**
	 * Custom processor to fail a task
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/fail", method = HttpMethod.POST)
	public abstract Object postTaskFail(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to start a task
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/start", method = HttpMethod.POST)
	public abstract Object postTaskStart(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to stop a task
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/stop", method = HttpMethod.POST)
	public abstract Object postTaskStop(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to suspend a task
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/suspend", method = HttpMethod.POST)
	public abstract Object postTaskSuspend(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to resume a task
	 */

	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/resume", method = HttpMethod.POST)
	public abstract Object postTaskResume(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to retrieve the content for the task Id provided
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/{taskId}/content", method = HttpMethod.GET)
	public abstract Object getTaskContent(@RestUriParam("taskId") String taskId)
			throws IOException;

	/**
	 * Custom processor to retrieve the content for the content Id provided
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/task/content/{contentId}", method = HttpMethod.GET)
	public abstract Object getTaskContentId(
			@RestUriParam("contentId") String contentId) throws IOException;

	/**
	 * Custom processor to start a process and retrieves the list of variables
	 * associated with the process instance
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/runtime/{DeploymentId}/withvars/process/{processDefId}/start", method = HttpMethod.POST)
	public abstract Object postProcessInstWithVarsDetails(
			@RestUriParam("DeploymentId") String deploymentId,
			@RestUriParam("processDefId") String procDefId) throws IOException;

	/**
	 * Custom processor to delete all the history logs
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/clean", method = HttpMethod.POST)
	public abstract Object postHistoryClear() throws IOException;

	/**
	 * Custom processor to retrieve list of ProcessInstanceLog instances
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instances", method = HttpMethod.GET)
	public abstract Object getHistoryInstances() throws IOException;

	/**
	 * Custom processor to retrieve the ProcessInstanceLog instance associated
	 * with the specified process instance
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instance/{ProcInstId}", method = HttpMethod.GET)
	public abstract Object getHistoryProcInstId(
			@RestUriParam("ProcInstId") String ProcInstId) throws IOException;

	/**
	 * Custom processor to retrieve the list of NodeInstanceLog instances
	 * associated with the specified process instance
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instance/{ProcInstId}/node", method = HttpMethod.GET)
	public abstract Object getHistProcInstIdNode(
			@RestUriParam("ProcInstId") String ProcInstId) throws IOException;

	/**
	 * Custom processor to retrieve the list of ProcessInstanceLog instances
	 * associated with any child/sub-processes associated with the specified
	 * process instance
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instance/{ProcInstId}/child", method = HttpMethod.GET)
	public abstract Object getHistoryProcInstIdChild(
			@RestUriParam("ProcInstId") String ProcInstId) throws IOException;

	/**
	 * Custom processor to retrieve the list of VariableInstanceLog instances
	 * associated with the specified process instance
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instance/{ProcInstId}/variable", method = HttpMethod.GET)
	public abstract Object getHistoryProcInstIdVar(
			@RestUriParam("ProcInstId") String ProcInstId) throws IOException;

	/**
	 * Custom processor to retrieve list of NodeInstanceLog instances associated
	 * with the specified process instance that have the given (node) id
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instance/{ProcInstId}/node/{nodeId}", method = HttpMethod.GET)
	public abstract Object getHistoryProcInstIdNodeId(
			@RestUriParam("ProcInstId") String ProcInstId,
			@RestUriParam("nodeId") String nodeId) throws IOException;

	/**
	 * Custom processor to retrieve a list of VariableInstanceLog instances
	 * associated with the specified process instance that have the given
	 * (variable) id
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/instance/{ProcInstId}/variable/{varId}", method = HttpMethod.GET)
	public abstract Object getHistoryProcInstIdVarId(
			@RestUriParam("ProcInstId") String ProcInstId,
			@RestUriParam("varId") String varId) throws IOException;

	/**
	 * Custom processor to retrieve a list of VariableInstanceLog instances
	 * associated with the specified variable id that contain the value
	 * specified
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/variable/{varId}/value/{value}", method = HttpMethod.GET)
	public abstract Object getHistoryVarIdVal(
			@RestUriParam("varId") String varId,
			@RestUriParam("value") String value) throws IOException;

	/**
	 * Custom processor to retrieve a list of ProcessInstanceLog instances
	 * associated with the specified process definition
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/process/{processDefId}", method = HttpMethod.GET)
	public abstract Object getHistProcDefId(
			@RestUriParam("processDefId") String processDefId)
			throws IOException;

	/**
	 * Custom processor to retrieve a list of ProcessInstance instances that
	 * contain the variable specified by the given variable id.
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/variable/{varId}/instances", method = HttpMethod.GET)
	public abstract Object getHistoryVarIdInst(
			@RestUriParam("varId") String varId) throws IOException;

	/**
	 * Custom processor to retrieve a list of ProcessInstance instances that
	 * contain the variable specified by the given variable id which contains
	 * the (variable) value specified
	 */
	@Processor
	@ReconnectOn(exceptions = { JBPMConnectorException.class })
	@RestCall(uri = "{url}/history/variable/{varId}/value/{value}/instances", method = HttpMethod.GET)
	public abstract Object getHistoryVarIdValInst(
			@RestUriParam("varId") String varId,
			@RestUriParam("value") String value) throws IOException;
}