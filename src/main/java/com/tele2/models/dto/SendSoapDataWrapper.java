package com.tele2.models.dto;

/**
 * Created by Dell on 23.01.2016.
 */
public class SendSoapDataWrapper {
    SoapXML soapxml;
    String environment;

    public SendSoapDataWrapper(String environment, SoapXML soapxml) {

        this.soapxml = soapxml;
        this.environment = environment;
    }

    public SendSoapDataWrapper() {
    }

    public SoapXML getSoapxml() {
        return soapxml;
    }

    public void setSoapxml(SoapXML soapxml) {
        this.soapxml = soapxml;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
