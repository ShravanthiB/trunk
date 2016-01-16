
package org.mule.modules.jbpm.connectivity;

import javax.annotation.Generated;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionKey;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-12-10T05:22:27+05:30", comments = "Build UNNAMED.2613.77421cc")
public class ConnectionManagementJbpm_configJBPMConnectorConnectionKey implements ConnectionManagementConnectionKey
{

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;

    public ConnectionManagementJbpm_configJBPMConnectorConnectionKey(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    @Override
    public int hashCode() {
        int result = ((this.username!= null)?this.username.hashCode(): 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionManagementJbpm_configJBPMConnectorConnectionKey)) {
            return false;
        }
        ConnectionManagementJbpm_configJBPMConnectorConnectionKey that = ((ConnectionManagementJbpm_configJBPMConnectorConnectionKey) o);
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        return true;
    }

}
