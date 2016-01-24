package com.tele2.controllers;

import com.tele2.models.dao.EnvironmentDAO;
import com.tele2.models.dao.SoapXMLDAO;
import com.tele2.models.dto.Environment;
import com.tele2.models.dto.SendSoapDataWrapper;
import com.tele2.models.dto.SoapXML;
import com.tele2.services.SoapClient;
import org.apache.http.StatusLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
public class SoapRestController {

    @Autowired
    SoapXMLDAO soapXMLDAO;

    @Autowired
    EnvironmentDAO environmentDAO;

    @Autowired
    SoapClient soapClient;

    @RequestMapping(value = "/api/soap", method = RequestMethod.GET)
    public SoapXML getSoapXML(@RequestParam(name = "id", required = false) Long id) {
        if (id == null) {
            return new SoapXML();
        }
        return soapXMLDAO.findOne(id);
    }

    @RequestMapping(value = "/api/soap", method = RequestMethod.PUT)
    public ResponseEntity<String> saveSoapXML(@RequestBody SoapXML soapXML) {
        soapXMLDAO.save(soapXML);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/soap", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSoapXML(@RequestBody SoapXML soapXML) {
        soapXMLDAO.delete(soapXML);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/soap/all", method = RequestMethod.GET)
    public Iterable<SoapXML> getAllSoapXML() {
        List<SoapXML> list = new LinkedList<>();
        for (SoapXML soapXML : soapXMLDAO.findAll()) {
            soapXML.setXml(null);
            list.add(soapXML);
        }
        return list;
    }

    @RequestMapping(value = "/api/soap/sendraw", method = RequestMethod.POST)
    public StatusLine getAllSoapRawXML(@RequestParam(name = "endpoint") String endpoint, @RequestParam(name = "soapAction") String soapAction, @RequestParam(name = "xml") String xml) {
        StatusLine statusCode = soapClient.sendSoapMessage(endpoint, soapAction, xml).getStatusLine();
        return statusCode;
    }

    @RequestMapping(value = "/api/soap/send", method = RequestMethod.POST)
    public int sendSoapXML(@RequestBody SendSoapDataWrapper wrapper) {
        String environment = wrapper.getEnvironment();
        SoapXML soapxml = wrapper.getSoapxml();
        soapxml.setRating(soapxml.getRating()+1);
        soapXMLDAO.save(soapxml);
        Environment environment1 = environmentDAO.findByName(environment);
        StatusLine statusCode = soapClient.sendSoapMessage(soapxml, environment1).getStatusLine();
        return statusCode.getStatusCode();
    }

    @RequestMapping(value = "/api/environment/all", method = RequestMethod.GET)
    public Iterable<Environment> getAllEnvironment() {
        return environmentDAO.findAll();
    }

}
