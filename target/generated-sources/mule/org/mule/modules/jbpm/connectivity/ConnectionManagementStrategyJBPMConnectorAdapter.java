
package org.mule.modules.jbpm.connectivity;

import javax.annotation.Generated;
import org.mule.api.ConnectionException;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.TestableConnection;
import org.mule.modules.jbpm.config.ConnectionManagementStrategy;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-12-10T05:22:27+05:30", comments = "Build UNNAMED.2613.77421cc")
public class ConnectionManagementStrategyJBPMConnectorAdapter
    extends ConnectionManagementStrategy
    implements ConnectionManagementConnectionAdapter<ConnectionManagementStrategy, ConnectionManagementJbpm_configJBPMConnectorConnectionKey> , TestableConnection<ConnectionManagementJbpm_configJBPMConnectorConnectionKey>
{


    @Override
    public void connect(ConnectionManagementJbpm_configJBPMConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.connect(connectionKey.getUsername(), connectionKey.getPassword());
    }

    @Override
    public void test(ConnectionManagementJbpm_configJBPMConnectorConnectionKey connectionKey)
        throws ConnectionException
    {
        super.testConnectivity(connectionKey.getUsername(), connectionKey.getPassword());
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    public String connectionId() {
        return super.connectionId();
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public ConnectionManagementStrategy getStrategy() {
        return this;
    }

}
