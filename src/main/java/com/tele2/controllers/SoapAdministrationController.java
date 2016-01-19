package com.tele2.controllers;

import com.tele2.models.dao.SoapXMLDAO;
import com.tele2.models.dto.SoapXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

/**
 * Created by jakhashr on 15.01.2016.
 */
@Controller
@RequestMapping("/mvc")
public class SoapAdministrationController {
    @Autowired
    SoapXMLDAO soapXMLDAO;

    @RequestMapping(value = "/soap/new", method = RequestMethod.GET)
    public String newSoapMessage(Model model) {
        model.addAttribute("title", "SOAP Tool: New soap message");
        model.addAttribute("msg", new SoapXML("NewMsg", "<?xml><head></head><body>{{myVar}}</body>", "My new message", new ArrayList(), "http://localhost/soapaction"));
        return "editmessage";
    }

    @RequestMapping(value = "/soap/edit", method = RequestMethod.GET)
    public String editSoapMessage(@RequestParam(name = "id") long id, Model model) {
        model.addAttribute("title", "SOAP Tool: Edit soap message");
        model.addAttribute("msg", soapXMLDAO.findOne(id));
        return "editmessage";
    }

    @RequestMapping(value = "/soap/delete", method = RequestMethod.GET)
    public String deketeSoapMessage(@RequestParam(name = "id") long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("title", "SOAP Tool");
        soapXMLDAO.delete(id);
        redirectAttributes.addFlashAttribute("info", "Deleted!");
        return "redirect:/mvc/soap/all";
    }

    @RequestMapping(value = "/soap/submit", method = RequestMethod.POST)
    public String newSoapMessageRegister(@ModelAttribute SoapXML soapXML, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("title", "SOAP Tool: All soap messages");
        soapXML.setPlaceholders(soapXMLDAO.getPlaceholders(soapXML.getXml()));
        soapXMLDAO.save(soapXML);
        redirectAttributes.addFlashAttribute("info", "Created!");
        return "redirect:/mvc/soap/all";
    }

    @RequestMapping(value = "/soap/all", method = RequestMethod.GET)
    public String getAllSoapXMLs(Model model) {
        model.addAttribute("title", "All Soap XMLs");
        model.addAttribute("soapxmls", soapXMLDAO.findAll());
        return "listsoap";
    }


}
