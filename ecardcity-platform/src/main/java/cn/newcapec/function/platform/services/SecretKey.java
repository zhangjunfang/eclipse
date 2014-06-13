
package cn.newcapec.function.platform.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SecretKey", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://192.168.60.224/SecretKey/SecretKey.asmx?wsdl")
public class SecretKey
    extends Service
{

    private final static URL SECRETKEY_WSDL_LOCATION;
    private final static WebServiceException SECRETKEY_EXCEPTION;
    private final static QName SECRETKEY_QNAME = new QName("http://tempuri.org/", "SecretKey");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.60.224/SecretKey/SecretKey.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SECRETKEY_WSDL_LOCATION = url;
        SECRETKEY_EXCEPTION = e;
    }

    public SecretKey() {
        super(__getWsdlLocation(), SECRETKEY_QNAME);
    }

    public SecretKey(WebServiceFeature... features) {
        super(__getWsdlLocation(), SECRETKEY_QNAME, features);
    }

    public SecretKey(URL wsdlLocation) {
        super(wsdlLocation, SECRETKEY_QNAME);
    }

    public SecretKey(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SECRETKEY_QNAME, features);
    }

    public SecretKey(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SecretKey(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SecretKeySoap
     */
    @WebEndpoint(name = "SecretKeySoap")
    public SecretKeySoap getSecretKeySoap() {
        return super.getPort(new QName("http://tempuri.org/", "SecretKeySoap"), SecretKeySoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SecretKeySoap
     */
    @WebEndpoint(name = "SecretKeySoap")
    public SecretKeySoap getSecretKeySoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "SecretKeySoap"), SecretKeySoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SECRETKEY_EXCEPTION!= null) {
            throw SECRETKEY_EXCEPTION;
        }
        return SECRETKEY_WSDL_LOCATION;
    }

}
