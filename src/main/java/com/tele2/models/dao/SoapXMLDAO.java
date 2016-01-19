package com.tele2.models.dao;

import com.tele2.models.dto.SoapXML;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
@Scope("request")
public interface SoapXMLDAO extends CrudRepository<SoapXML, Long> {

    public default List<String> getPlaceholders(String xml) {
        Pattern pattern = Pattern.compile("\\{\\{(\\w+\\d*)\\}\\}");
        Matcher matcher = pattern.matcher(xml);
        List<String> placeholders = new ArrayList<>();
        while (matcher.find()) {
            placeholders.add(matcher.group(1));
        }
        return placeholders;
    }

}
