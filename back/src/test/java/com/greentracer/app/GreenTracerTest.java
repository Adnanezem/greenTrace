package com.greentracer.app;

import com.greentracer.app.internal.DefaultGreenTracer;
import com.greentracer.app.responses.TestRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(GreenTracer.class)
public class GreenTracerTest {

    @MockBean
    private DefaultGreenTracer defaultGreenTracer;

    @InjectMocks
    private GreenTracer greenTracer;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGreeting() throws Exception {
        String name = "test";
        TestRecord expectedResult = new TestRecord("Hello " + name + " !");

        when(defaultGreenTracer.greeting(name)).thenReturn(expectedResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"content\": \"Hello test !\"}"))
                .andDo(print());
    }
}