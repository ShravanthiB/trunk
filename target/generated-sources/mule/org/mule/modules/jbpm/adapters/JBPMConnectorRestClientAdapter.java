
package org.mule.modules.jbpm.adapters;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.registry.RegistrationException;
import org.mule.api.registry.ResolverException;
import org.mule.api.registry.TransformerResolver;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.modules.jbpm.JBPMConnector;
import org.mule.registry.TypeBasedTransformerResolver;
import org.mule.transformer.simple.ObjectToString;
import org.mule.transformer.types.DataTypeFactory;
import org.mule.transport.http.HttpMuleMessageFactory;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2015-12-10T05:22:27+05:30", comments = "Build UNNAMED.2613.77421cc")
public class JBPMConnectorRestClientAdapter
    extends JBPMConnectorConnectionManagementAdapter
    implements MuleContextAware, Disposable, Initialisable
{

    private int responseTimeout;
    private MuleContext muleContext;
    private volatile HttpClient httpClient;
    private HttpMuleMessageFactory httpMuleMessageFactory;
    private volatile MultiThreadedHttpConnectionManager connectionManager;

    private MuleMessage getMuleMessage(HttpMethod method, String encoding) {
        try {
            MuleMessage muleMessage = httpMuleMessageFactory.create(method, encoding);
            muleMessage.getPayloadAsString();
            return muleMessage;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't transform http response to MuleMessage", e);
        }
    }

    public void setMuleContext(MuleContext context) {
        muleContext = context;
        httpMuleMessageFactory = new HttpMuleMessageFactory(muleContext);
    }

    private Transformer getPayloadTransformer(DataType inputDataType, DataType outputDataType) {
        try {
            TransformerResolver typeBasedResolver = muleContext.getRegistry().lookupObject(TypeBasedTransformerResolver.class);
            Transformer typeResolverTransformer = typeBasedResolver.resolve(inputDataType, outputDataType);
            if ((typeResolverTransformer == null)||(typeResolverTransformer instanceof ObjectToString)) {
                Transformer transformer = muleContext.getRegistry().lookupTransformer(inputDataType, outputDataType);
                if (transformer!= null) {
                    return transformer;
                }
            }
            return typeResolverTransformer;
        } catch (ResolverException rese) {
            throw new RuntimeException(rese.getMessage(), rese);
        } catch (RegistrationException re) {
            throw new RuntimeException(re.getMessage(), re);
        } catch (TransformerException te) {
            throw new RuntimeException(te.getMessage(), te);
        }
    }

    @Override
    public void initialise()
        throws InitialisationException
    {
        connectionManager = new MultiThreadedHttpConnectionManager();
        httpClient = new HttpClient(connectionManager);
        httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpClient.getParams().setParameter("http.socket.timeout", responseTimeout);
        httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
    }

    /**
     * Sets responseTimeout
     * 
     * @param value Value to set
     */
    public void setResponseTimeout(int value) {
        this.responseTimeout = value;
    }

    public Object getProcessInstanceDetails(String deploymentId, String procInstId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/runtime/{DeploymentId}/process/instance/{ProcessInstanceId}";
        uri = uri.replace("{DeploymentId}", String.valueOf(deploymentId));
        uri = uri.replace("{ProcessInstanceId}", String.valueOf(procInstId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getProcessInstanceDetails", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getProcessInstanceDetails", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postProcessInstanceStartDetails(String deploymentId, String procDefId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/runtime/{DeploymentId}/process/{processDefId}/start";
        uri = uri.replace("{DeploymentId}", String.valueOf(deploymentId));
        uri = uri.replace("{processDefId}", String.valueOf(procDefId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postProcessInstanceStartDetails", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postProcessInstanceStartDetails", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskComplete(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/complete";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskComplete", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskComplete", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskRelease(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/release";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskRelease", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskRelease", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskClaim(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/claim";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskClaim", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskClaim", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskClaimnextavailable(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/claimnextavailable";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskClaimnextavailable", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskClaimnextavailable", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getTaskQuery()
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/task/query";
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getTaskQuery");
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getTaskQuery", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskFail(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/fail";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskFail", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskFail", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskStart(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/start";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskStart", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskStart", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskStop(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/stop";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskStop", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskStop", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskSuspend(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/suspend";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskSuspend", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskSuspend", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postTaskResume(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/task/{taskId}/resume";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postTaskResume", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postTaskResume", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getTaskContent(String taskId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/task/{taskId}/content";
        uri = uri.replace("{taskId}", String.valueOf(taskId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getTaskContent", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getTaskContent", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getTaskContentId(String contentId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/task/content/{contentId}";
        uri = uri.replace("{contentId}", String.valueOf(contentId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getTaskContentId", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getTaskContentId", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postProcessInstWithVarsDetails(String deploymentId, String procDefId)
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/runtime/{DeploymentId}/withvars/process/{processDefId}/start";
        uri = uri.replace("{DeploymentId}", String.valueOf(deploymentId));
        uri = uri.replace("{processDefId}", String.valueOf(procDefId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postProcessInstWithVarsDetails", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postProcessInstWithVarsDetails", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object postHistoryClear()
        throws IOException
    {
        HttpMethod method = null;
        method = new PostMethod();
        String uri = "{url}/history/clean";
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        ((PostMethod) method).addParameters(postParameters.toArray(new NameValuePair[] {}));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("postHistoryClear");
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named postHistoryClear", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryInstances()
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instances";
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryInstances");
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryInstances", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryProcInstId(String ProcInstId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instance/{ProcInstId}";
        uri = uri.replace("{ProcInstId}", String.valueOf(ProcInstId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryProcInstId", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryProcInstId", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistProcInstIdNode(String ProcInstId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instance/{ProcInstId}/node";
        uri = uri.replace("{ProcInstId}", String.valueOf(ProcInstId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistProcInstIdNode", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistProcInstIdNode", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryProcInstIdChild(String ProcInstId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instance/{ProcInstId}/child";
        uri = uri.replace("{ProcInstId}", String.valueOf(ProcInstId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryProcInstIdChild", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryProcInstIdChild", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryProcInstIdVar(String ProcInstId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instance/{ProcInstId}/variable";
        uri = uri.replace("{ProcInstId}", String.valueOf(ProcInstId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryProcInstIdVar", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryProcInstIdVar", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryProcInstIdNodeId(String ProcInstId, String nodeId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instance/{ProcInstId}/node/{nodeId}";
        uri = uri.replace("{ProcInstId}", String.valueOf(ProcInstId));
        uri = uri.replace("{nodeId}", String.valueOf(nodeId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryProcInstIdNodeId", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryProcInstIdNodeId", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryProcInstIdVarId(String ProcInstId, String varId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/instance/{ProcInstId}/variable/{varId}";
        uri = uri.replace("{ProcInstId}", String.valueOf(ProcInstId));
        uri = uri.replace("{varId}", String.valueOf(varId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryProcInstIdVarId", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryProcInstIdVarId", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryVarIdVal(String varId, String value)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/variable/{varId}/value/{value}";
        uri = uri.replace("{varId}", String.valueOf(varId));
        uri = uri.replace("{value}", String.valueOf(value));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryVarIdVal", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryVarIdVal", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistProcDefId(String processDefId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/process/{processDefId}";
        uri = uri.replace("{processDefId}", String.valueOf(processDefId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistProcDefId", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistProcDefId", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryVarIdInst(String varId)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/variable/{varId}/instances";
        uri = uri.replace("{varId}", String.valueOf(varId));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryVarIdInst", String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryVarIdInst", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

    public Object getHistoryVarIdValInst(String varId, String value)
        throws IOException
    {
        HttpMethod method = null;
        method = new GetMethod();
        String uri = "{url}/history/variable/{varId}/value/{value}/instances";
        uri = uri.replace("{varId}", String.valueOf(varId));
        uri = uri.replace("{value}", String.valueOf(value));
        uri = uri.replace("{url}", String.valueOf(getUrl()));
        method.setURI(new URI(uri, false));
        StringBuilder queryString = new StringBuilder(((method.getQueryString()!= null)?method.getQueryString():""));
        if ((queryString.length()> 0)&&(queryString.charAt(0) == '&')) {
            queryString.deleteCharAt(0);
        }
        method.setQueryString(URIUtil.encodeQuery(queryString.toString()));
        method.addRequestHeader("Authorization", String.valueOf(getAuthorization()));
        httpClient.executeMethod(method);
        MuleMessage muleMessage = getMuleMessage(method, "UTF-8");
        String output = ((String) muleMessage.getPayload());
        if ((output!= null)&&(!Object.class.isAssignableFrom(String.class))) {
            DataType payloadOutputDataType = null;
            try {
                Method reflectedMethod = JBPMConnector.class.getMethod("getHistoryVarIdValInst", String.class, String.class);
                payloadOutputDataType = DataTypeFactory.createFromReturnType(reflectedMethod);
                DataType payloadInputDataType = DataTypeFactory.create(String.class, ((String) muleMessage.getOutboundProperty("Content-Type")));
                Transformer transformer = getPayloadTransformer(payloadInputDataType, payloadOutputDataType);
                return ((Object) transformer.transform(output));
            } catch (TransformerException te) {
                throw new RuntimeException(("Unable to transform output from String to "+ payloadOutputDataType.toString()), te);
            } catch (NoSuchMethodException nsme) {
                throw new RuntimeException("Unable to find method named getHistoryVarIdValInst", nsme);
            }
        } else {
            return ((Object)((Object) output));
        }
    }

}
