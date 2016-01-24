package com.tele2.services;

import com.tele2.models.dto.Environment;
import com.tele2.models.dto.SoapXML;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Random;

/**
 * Created by jakhashr on 15.01.2016.
 */
@Service
public class SoapClient {

    public void test() {
        String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\" +\n" +
                "                \"<soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:ord=\\\"http://siebel.com/OrderManagement/Order\\\" xmlns:data=\\\"http://siebel.com/OrderManagement/Order/Data\\\">\\n\" +\n" +
                "                \"   <soapenv:Header/>\" +\n" +
                "                \"   <soapenv:Body>\" +\n" +
                "                \"   </soapenv:Body>\" +\n" +
                "                \"</soapenv:Envelope>";

        String url = "http://jessie:8080/eai_enu/start.swe?SWEExtSource=WebService&SWEExtCmd=Execute&UserName=EAIADMIN&Password=eaiadmin";

        String soapAction = "\"document/http://siebel.com/OrderManagement/Order:GetOrderById\"";

        System.out.println(sendSoapMessage(url, soapAction, body).getStatusLine());

    }

    public HttpResponse sendSoapMessage(String endpoint, String soapAction, String xml) {
        HttpResponse response = null;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            String bodyLength = new Integer(xml.length()).toString();

            URI uri = new URI(endpoint);
            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader("Content-Type", "text/xml; charset=utf-8");
            httpPost.addHeader("SOAPAction", soapAction);
            httpPost.addHeader("SOAPAction", bodyLength);

            StringEntity entity = new StringEntity(xml, "text/xml", HTTP.UTF_8);
            httpPost.setEntity(entity);

            RequestWrapper requestWrapper = new RequestWrapper(httpPost);
            requestWrapper.setMethod("POST");
            response = httpclient.execute(requestWrapper);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse sendSoapMessage(SoapXML soapXML, Environment environment) {
        String endpoint = environment.getEndpoint();
        String soapAction = soapXML.getSoapAction();
        String xml = soapXML.getXml();
        try {
            Thread.sleep(1000 + new Random(System.currentTimeMillis()).nextInt(900));
        } catch (InterruptedException e) {
        }
        return sendSoapMessage(endpoint, soapAction, xml);

    }

    public int sendSoapMessageReturnResponseCode(String enpoint, String soapAction, String xml) {
        return sendSoapMessage(enpoint, soapAction, xml).getStatusLine().getStatusCode();

    }

    public int sendSoapMessageReturnResponseCode(SoapXML soapXML, Environment environment) {
        return sendSoapMessage(soapXML, environment).getStatusLine().getStatusCode();

    }


}
