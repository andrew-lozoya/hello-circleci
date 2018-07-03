package hello;

import org.junit.*;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(Application.class)
public class ApplicationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebClient webClient;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        webClient = MockMvcWebClientBuilder.webAppContextSetup(context).build();
    }

    @After
    public void close() {
        webClient.close();
    }

    @Test
    public void homePageStatus() throws Exception {
        mockMvc.perform(get("/index.html")).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void homePageHtmlUnit() throws Exception {
        HtmlPage currentPage = webClient.getPage("http://localhost:8080/index.html");
        Assert.assertEquals("CircleCI Challenge - Spring: Serving Web Content", currentPage.getTitleText());
        HtmlButton button = (HtmlButton) currentPage.getElementById("onlickevent");
        button.click();
        Assert.assertEquals("Hello World", currentPage.getElementById("circleci").asText());
    }
}
