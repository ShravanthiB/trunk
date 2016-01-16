package org.mule.modules.jbpm.automation.functional;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.mule.modules.jbpm.JBPMConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class ConnectorTestCases
    extends AbstractTestCase<JBPMConnector>
{

	public ConnectorTestCases() {
        super(JBPMConnector.class);
    }
	
	   
	@Test
    public void testgetProcessInstanceDetails() throws IOException {
   
		Object historyInstances = getConnector().getHistoryInstances();
		assertNotNull(historyInstances);

    }

}
