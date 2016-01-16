
package org.mule.modules.jbpm.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/jbpm</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-12-10T05:22:27+05:30", comments = "Build UNNAMED.2613.77421cc")
public class JbpmNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(JbpmNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [jbpm] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [jbpm] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("JBPM_Config", new JBPMConnectorConnectionManagementStrategyConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("JBPM_Config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-process-instance-details", new GetProcessInstanceDetailsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-process-instance-details", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-process-instance-start-details", new PostProcessInstanceStartDetailsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-process-instance-start-details", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-complete", new PostTaskCompleteDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-complete", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-release", new PostTaskReleaseDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-release", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-claim", new PostTaskClaimDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-claim", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-claimnextavailable", new PostTaskClaimnextavailableDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-claimnextavailable", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-query", new GetTaskQueryDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-query", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-fail", new PostTaskFailDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-fail", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-start", new PostTaskStartDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-start", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-stop", new PostTaskStopDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-stop", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-suspend", new PostTaskSuspendDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-suspend", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-task-resume", new PostTaskResumeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-task-resume", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-content", new GetTaskContentDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-content", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-content-id", new GetTaskContentIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-content-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-process-inst-with-vars-details", new PostProcessInstWithVarsDetailsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-process-inst-with-vars-details", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("post-history-clear", new PostHistoryClearDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("post-history-clear", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-instances", new GetHistoryInstancesDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-instances", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-proc-inst-id", new GetHistoryProcInstIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-proc-inst-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-hist-proc-inst-id-node", new GetHistProcInstIdNodeDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-hist-proc-inst-id-node", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-proc-inst-id-child", new GetHistoryProcInstIdChildDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-proc-inst-id-child", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-proc-inst-id-var", new GetHistoryProcInstIdVarDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-proc-inst-id-var", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-proc-inst-id-node-id", new GetHistoryProcInstIdNodeIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-proc-inst-id-node-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-proc-inst-id-var-id", new GetHistoryProcInstIdVarIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-proc-inst-id-var-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-var-id-val", new GetHistoryVarIdValDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-var-id-val", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-hist-proc-def-id", new GetHistProcDefIdDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-hist-proc-def-id", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-var-id-inst", new GetHistoryVarIdInstDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-var-id-inst", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-history-var-id-val-inst", new GetHistoryVarIdValInstDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-history-var-id-val-inst", "@Processor", ex);
        }
    }

}
