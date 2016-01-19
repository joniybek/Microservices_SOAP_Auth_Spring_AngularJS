package com.tele2;

import com.tele2.models.dto.security.User;
import com.tele2.services.security.AuthenticationService;
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

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Dell on 16.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SoapApplication.class)
@WebAppConfiguration
public class AuthenticationTest {
    private MockMvc mockMvc;
    static boolean haveBeenSetup = false;

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


    @Autowired
    AuthenticationService authentificationService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        if(!haveBeenSetup) {
            haveBeenSetup = true;
            authentificationService.init();
        }
    }

    @Test
    public void usernameNotExists() {
        assertFalse(authentificationService.isUsernameExists("cannot be found"));
    }

    @Test
    public void usernameExists() {
        assertTrue(authentificationService.isUsernameExists("test1"));
    }

    @Test
    public void usernameAuth_Success() {
        assertEquals("email", authentificationService.authenticateUser("test1", "sometext").getEmail());
    }

    @Test
    public void usernameAuth_Failure() {
        assertNull(authentificationService.authenticateUser("test1", "wrong password"));
    }

    @Test
    public void createUser_Success() {
        User user = authentificationService.createUser("qwerty", "password", "example@tele2.com");
        assertNotNull(user);
    }

    @Test(expected = Exception.class)
    public void createUser_Failure() {
        User user = authentificationService.createUser("test2", "pass", "example@tele2.com");
        assertNotNull(user);
    }

    @Test
    public void deleteUser_Success() {
        authentificationService.deleteUser("qwerty");
        assertFalse(authentificationService.isUsernameExists("qwerty"));
    }

    @Test
    public void resourceExists_Success() {
        assertNotNull(authentificationService.isResourceExists("Data Pool"));
        assertEquals(2, authentificationService.getResource("Data Pool").getRoles().size());
    }

    @Test
    public void createResource_Success() {
        String name = "test tool";
        authentificationService.createResource(name, "some location");
        assertTrue(authentificationService.isResourceExists(name));
        assertEquals(2, authentificationService.getResource(name).getRoles().size());
        assertNotNull(authentificationService.getAdminFromResource(name));
        assertNotNull(authentificationService.getReaderFromResource(name));
    }

    @Test(expected = Exception.class)
    public void createResource_Failure() {
        authentificationService.createResource(null, "some location");
    }

    @Test
    public void deleteResource_Success() {
        String name = "test tool";
        authentificationService.deleteResource(name);
        assertFalse(authentificationService.isResourceExists(name));
        assertNull(authentificationService.getAdminFromResource(name));
        assertNull(authentificationService.getReaderFromResource(name));
    }

}
