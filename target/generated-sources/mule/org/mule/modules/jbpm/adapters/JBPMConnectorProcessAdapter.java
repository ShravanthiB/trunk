
package org.mule.modules.jbpm.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.jbpm.JBPMConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>JBPMConnectorProcessAdapter</code> is a wrapper around {@link JBPMConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-12-10T05:22:27+05:30", comments = "Build UNNAMED.2613.77421cc")
public abstract class JBPMConnectorProcessAdapter
    extends JBPMConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<JBPMConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, JBPMConnectorCapabilitiesAdapter> getProcessTemplate() {
        final JBPMConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,JBPMConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, JBPMConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, JBPMConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
