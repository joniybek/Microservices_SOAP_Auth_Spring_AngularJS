package com.tele2.models.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by jakhashr on 14.01.2016.
 */

@Entity
@Table(name = "soapxml")
public class SoapXML {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long xml_id;

    @NotNull
    String name;

    int rating;

    @NotNull
    @Column(name="xml", columnDefinition="TEXT")
    String xml;

    public SoapXML(String name, String xml, String description, List<String> placeholders, String soapAction) {
        this.name = name;
        this.xml = xml;
        this.description = description;
        this.placeholders = placeholders;
        this.soapAction = soapAction;
    }

    String description;

    @ElementCollection
    @CollectionTable(name = "Placeholders", joinColumns = @JoinColumn(name = "xml_id"))
    @Column(name = "placeholders")
    List<String> placeholders;

    String soapAction;

    @Override
    public String toString() {
        return "SoapXML{" +
                "xml_id=" + xml_id +
                ", name='" + name + '\'' +
                ", xml='" + xml + '\'' +
                ", description='" + description + '\'' +
                ", placeholders=" + placeholders +
                ", soapAction='" + soapAction + '\'' +
                '}';
    }

    public SoapXML() {
    }

    public void setXml_id(long xml_id) {
        this.xml_id = xml_id;
    }

    public String getName() {
        return name;
    }

    public long getXml_id() {
        return xml_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<String> placeholders) {
        this.placeholders = placeholders;
    }

    public String getSoapAction() {
        return soapAction;
    }

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

