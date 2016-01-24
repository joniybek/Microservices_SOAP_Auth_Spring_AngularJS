package com.tele2;

import com.tele2.models.dao.EnvironmentDAO;
import com.tele2.models.dao.SoapXMLDAO;
import com.tele2.models.dto.Environment;
import com.tele2.models.dto.SoapXML;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by jakhashr on 15.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SoapApplication.class)
@WebAppConfiguration
public class SoapXMLTest {

    private MockMvc mockMvc;

    @Autowired
    SoapXMLDAO soapXMLDAO;

    @Autowired
    EnvironmentDAO environmentDAO;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        Environment environment1 = new Environment("AGNIA", "Sweden", "SVE", "http://localhost:8080/soap");
        Environment environment2 = new Environment("SUE", "Latvia", "LVI", "http://localhost:8080/soap");
        environmentDAO.save(environment1);
        environmentDAO.save(environment2);


        String xml1 = "<?xml version=\"1.0\"?><soap:Envelope soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
                "<soap:Body xmlns:m=\"http://www.example.org/stock\">\n" +
                "  <m:GetStockPriceResponse>\n" +
                "    <m:Price>{{price}}</m:Price>\n" +
                "  </m:GetStockPriceResponse>\n" +
                "</soap:Body>\n" +
                "</soap:Envelope>";
        SoapXML soapXML1 = new SoapXML("test1", xml1, "this is test1 description", soapXMLDAO.getPlaceholders(xml1), "document/someaction");
        String xml2 = "<?xml version=\"1.0\"?><soap:Envelope soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
                "<soap:Body xmlns:m=\"http://www.example.org/stock\">\n" +
                "  <m:GetStockPriceResponse>\n" +
                "    <m:Price>{{price1}}</m:Price>\n" +
                "    <m:Price>{{price2}}</m:Price>\n" +
                "    <m:Price>{{price3}}</m:Price>\n" +
                "  </m:GetStockPriceResponse>\n" +
                "</soap:Body>\n" +
                "</soap:Envelope>";
        SoapXML soapXML2 = new SoapXML("test2", xml2, "this is test2 description", soapXMLDAO.getPlaceholders(xml2), "document/someaction2");

        this.soapXMLDAO.deleteAll();
        this.soapXMLDAO.save(Arrays.asList(new SoapXML[]{soapXML1, soapXML2}));
    }

    @Test
    public void test() {

    }

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


}
