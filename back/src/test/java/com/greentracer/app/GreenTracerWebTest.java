package com.greentracer.app;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.greentracer.app.internal.DefaultGreenTracer;
import com.greentracer.app.responses.TestRecord;

@WebMvcTest(GreenTracer.class)
@ActiveProfiles("test")
class GreenTracerWebTest {

    private static Logger logger = LoggerFactory.getLogger(GreenTracerWebTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultGreenTracer defaultGreenTracer; // Mocked dependency

    @Test
    void testGreeting() throws Exception {
        // Arrange
        String name = "Alice";
        String expectedMessage = name;
        TestRecord expectedResult = new TestRecord("Hello " + name + " !");

        // Mock the behavior of DefaultGreenTracer.greeting() method
        when(defaultGreenTracer.greeting(expectedMessage)).thenReturn(expectedResult);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/").param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> logger.warn("test : {}", result.getResponse().getContentAsString()));
        // .andExpect(MockMvcResultMatchers.content().json("{content: 'Hello Alice
        // !'}")); // Adjust according to your expected JSON response
    }
}
